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

    public ClientHandler(int id, Socket clientSocket, Player player, Object gameStarted) 
    {
        this.id = id;
        this.clientSocket = clientSocket;
        this.player = player;
        this.gameStarted = gameStarted;

        LOGGER = Logger.getLogger("ServerPlayer " + player.id);

        LOGGER.info("Client handler created");
    }
    

    @Override
    public void run() 
    {
        try(ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) 
        {
            requestHandler = getDefaultRequestHandler(out, in);

            Thread readiness = new Thread(() -> {
                while(true)
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
            });

            readiness.start();
            synchronized(gameStarted){}
            readiness.stop(); // I know the risk
            
            while(true)
            {
                Request request = (Request)in.readObject();
                RequestRunnable action = requestHandler.get(request);
                if(action != null)
                {
                    action.run(request);
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
            LOGGER.info("Client disconnected");
            CheckersServer.removeClient(id);
        } 
        catch(IOException | ClassNotFoundException e) 
        {
            LOGGER.severe("Communication error:\n" + e.getMessage());
        } 
        finally 
        {
            try 
            {
                clientSocket.close();
            } 
            catch (IOException e) 
            {
                LOGGER.severe("Couldn't close client socket:\n" + e.getMessage());
            }
        }
    }

    private Map<Request, RequestRunnable> getDefaultRequestHandler(ObjectOutputStream out, ObjectInputStream in)
    {
        Map<Request, RequestRunnable> requestHandler = new HashMap<>();

        requestHandler.put(Request.GREET, (Request greet) -> {
            Request ack = Request.ACKNOWLEDGE;
            ack.setData(player);
            out.writeObject(ack);
        });

        requestHandler.put(Request.END_TURN, (Request end_turn) -> {
            try
            {
                Request ack = Request.ACKNOWLEDGE;
                synchronized(CheckersServer.class)
                {
                    Game game = CheckersServer.getGame();
                    game.endTurn(player);
                    ack.setData(game.getCurrentTurn());
                }
                out.writeObject(ack);
            }
            catch(IllegalAccessError e)
            {
                Request error = Request.ERROR;
                error.setData(e);
                out.writeObject(error);
            }
        });

        requestHandler.put(Request.UPDATE, (Request update) -> {
            Request ack = Request.ACKNOWLEDGE;
            synchronized(CheckersServer.class)
            {
                Game game = CheckersServer.getGame();
                ack.setData(new GameState(game.getBoard().getNodes(), game.getCurrentTurn(), player.didWin()));
            }
            out.writeObject(ack);
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
                out.writeObject(Request.ACKNOWLEDGE);
            }
            else
            {
                Request error = Request.ERROR;
                error.setData(new Error("Cannot set readiness to null"));
                out.writeObject(error);
            }
        });

        requestHandler.put(Request.GET_MOVES, (Request get) -> {
            Request moves = Request.ACKNOWLEDGE;
            synchronized(CheckersServer.class)
            {
                Game game = CheckersServer.getGame();
                moves.setData(game.getValidMoves(player, (String)get.getData()));
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
                out.writeObject(Request.ACKNOWLEDGE);
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
