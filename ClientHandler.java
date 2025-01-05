import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.logging.Logger;

public class ClientHandler implements Runnable 
{
    private final int id;
    private final Socket clientSocket;
    private final Player player;
    private Object gameStarted;

    private Map<Request, RequestRunnable> requestHandler;

    private final Logger LOGGER;

    private boolean running;

    public ClientHandler(int id, Socket clientSocket, Player player, Object gameStarted) 
    {
        this.id = id;
        this.clientSocket = clientSocket;
        this.player = player;
        this.gameStarted = gameStarted;

        this.requestHandler = null;

        this.running = false;

        LOGGER = Logger.getLogger("ServerPlayer " + player.getId());

        LOGGER.info("Client handler created");
    }
    
    @Override
    public void run() 
    {
        running = true;
        try(ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) 
        {
            requestHandler = getDefaultRequestHandler(out, in);

            Thread readiness = new Thread(() -> {
                while(running)
                {
                    try
                    {
                        Request request = (Request)in.readObject();
                        if(request == Request.READY)
                        {
                            requestHandler.get(Request.READY).run(request);
                        }
                        else
                        {
                            Request error = Request.ERROR;
                            error.setData(new Error("Game has not started yet"));
                            out.writeObject(error);
                        }
                    }
                    catch(EOFException e)
                    {
                        disconnect(true);
                    }
                    catch(IOException | ClassNotFoundException e)
                    {
                        disconnect(false);
                    }
                }
            });

            readiness.start();
            synchronized(gameStarted){}
            readiness.stop(); // I know the risk
            
            while(running)
            {
                Request request = (Request)in.readObject();
                RequestRunnable action = requestHandler.get(request);
                if(action != null)
                {
                    synchronized(this)
                    {
                        action.run(request);
                    }
                }
                else
                {
                    Request error = Request.ERROR;
                    error.setData("Non-handled request");
                    out.writeObject(error);
                }
            }
        }
        catch(EOFException e)
        {
            disconnect(true);
        }
        catch(IOException | ClassNotFoundException e) 
        {
            disconnect(false);
        }

        LOGGER.info("ClientHandler died");
    }

    public void disconnect(boolean ok)
    {
        running = false;

        if(ok)
        {
            LOGGER.info("Client disconnected");
        }
        else
        {
            LOGGER.severe("Connection error!");
        }
        
        CheckersServer.removeClient(id);
        try{ clientSocket.close(); }catch( IOException e ){}
    }

    public void sendUpdate() throws IOException
    {
        if(requestHandler != null)
        {
            try{ requestHandler.get(Request.UPDATE).run(Request.UPDATE); }catch( ClassNotFoundException e){}
        }
    }

    private Map<Request, RequestRunnable> getDefaultRequestHandler(ObjectOutputStream out, ObjectInputStream in)
    {
        Map<Request, RequestRunnable> requestHandler = new HashMap<>();

        requestHandler.put(Request.GREET, (Request greet) -> {
            greet.setData(player);
            out.writeObject(greet);
        });

        requestHandler.put(Request.END_TURN, (Request end_turn) -> {
            try
            {
                synchronized(CheckersServer.class)
                {
                    Game game = CheckersServer.getGame();
                    game.endTurn(player);
                    end_turn.setData(game.getCurrentTurn());
                }
                out.writeObject(end_turn);

                CheckersServer.broadcastUpdate();
            }
            catch(IllegalAccessError e)
            {
                Request error = Request.ERROR;
                error.setData(e);
                out.writeObject(error);
            }
        });

        requestHandler.put(Request.UPDATE, (Request update) -> {
            synchronized(CheckersServer.class)
            {
                Game game = CheckersServer.getGame();
                update.setData(new GameState(game.getBoard().getNodes(), game.getCurrentTurn(), player.didWin()));
            }
            out.writeObject(update);
        });

        requestHandler.put(Request.ACKNOWLEDGE, (Request ack) -> {
        });

        requestHandler.put(Request.ERROR, (Request error) -> {
            LOGGER.severe(((Error)error.getData()).getMessage());
        });

        requestHandler.put(Request.READY, (Request ready) -> {
            if(ready.getData() != null)
            {
                synchronized(CheckersServer.class)
                {
                    CheckersServer.setReady((Boolean)ready.getData(), id);
                }
                out.writeObject(ready);
            }
            else
            {
                Request error = Request.ERROR;
                error.setData(new Error("Cannot set readiness to null"));
                out.writeObject(error);
            }
        });

        requestHandler.put(Request.GET_MOVES, (Request moves) -> {
            synchronized(CheckersServer.class)
            {
                Game game = CheckersServer.getGame();
                moves.setData(game.getValidMoves(player, (int[])moves.getData()));
            }
            out.writeObject(moves);
        });

        requestHandler.put(Request.MOVE, (Request move) -> {
            try
            {
                synchronized(CheckersServer.class)
                {
                    Game game = CheckersServer.getGame();
                    game.move(player, (Move)move.getData());
                }
                out.writeObject(move);

                CheckersServer.broadcastUpdate();
            }
            catch(IllegalAccessError e)
            {
                Request error = Request.ERROR;
                error.setData(e);
                out.writeObject(error);
            }
        });

        return requestHandler;
    }
}
