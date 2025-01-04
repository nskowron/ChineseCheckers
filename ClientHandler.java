import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.logging.Logger;

public class ClientHandler implements Runnable 
{
    private final Socket clientSocket;
    private final Game game;
    private final Player player;

    private Map<Request, RequestRunnable> requestHandler;
    private Condition gameStarted;
    private Boolean ready;

    private final Logger LOGGER;

    public ClientHandler(Socket clientSocket, Game game, Player player, Condition gameStarted, Boolean ready) 
    {
        this.clientSocket = clientSocket;
        this.game = game;
        this.player = player;
        this.gameStarted = gameStarted;
        this.ready = ready;

        LOGGER = Logger.getLogger("Player " + player.id);

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
            gameStarted.await();
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
                game.endTurn(player);
                Request ack = Request.ACKNOWLEDGE;
                ack.setData(game.getCurrentTurn());
                out.writeObject(ack);
            }
            catch(IllegalAccessError e)
            {
                Request error = Request.ERROR;
                error.setData(e);
                out.writeObject(error);
            }
        });

        request.put(Request.UPDATE, (Request update) -> {
            GameState state = new GameState(game.getBoard().getNodes(), game.getCurrentTurn(), player.didWin());
            Request ack = Request.ACKNOWLEDGE;
            ack.setData(state);
            out.writeObject(ack);
        });

        request.put(Request.ACKNOWLEDGE, (Request ack) -> {
            in.flush();
        });

        request.put(Request.ERROR, (Request error) -> {
            LOGGER.severe(error.getData().getMessage());
        });

        request.put(Request.READY, (Request ready) -> {
            if(ready.getData() != null)
            {
                this.ready = (Boolean)ready.getData();
                out.writeObject(Request.ACKNOWLEDGE);
            }
            else
            {
                Request error = Request.ERROR;
                error.setData(new Error("Cannot set readiness to null"));
                out.writeObject(error);
            }
        });

        request.put(Request.GET_MOVES, (Request get) -> {
            Request moves = Request.ACKNOWLEDGE;
            moves.setData(game.getValidMoves(player, (String)get.getData()));
            out.writeObject(moves);
        });

        request.put(Request.MOVE, (Request move) -> {
            try
            {
                game.move((Move)move.getData());
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
