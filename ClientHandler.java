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
        try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) 
        {
            requestHandler = getDefaultRequestHandler(out, in);

            Thread readiness = new Thread(() -> {
                // continue work here
            });
            // Send greeting with client ID
            out.writeObject(clientId);
            out.flush();

            while (true) 
            {
                String clientRequest = (String) in.readObject();
                System.out.println("Client " + clientId + " request: " + clientRequest);

                switch (clientRequest) 
                {
                    case "GET_BOARD":
                        out.writeObject(game.getBoard());
                        out.flush();
                        System.out.println("Board sent to client " + clientId + ".");
                        break;

                    case "GET_READY":
                        out.writeObject("Client " + clientId + " is now ready!");
                        out.flush();
                        System.out.println("Client " + clientId + " marked as ready.");
                        break;

                    case "GET_NOT_READY":
                        out.writeObject("Client " + clientId + " is now not ready.");
                        out.flush();
                        System.out.println("Client " + clientId + " marked as not ready.");
                        break;

                    case "SEND_MOVE":
                        Move move = (Move) in.readObject();
                        synchronized (game) 
                        {
                            System.out.println("Client " + clientId + " sent move: " + move.startId + " -> " + move.endId);

                            try 
                            {
                                game.move(move);

                                out.writeObject("Moved succesfully: " +  move.startId + " -> " + move.endId);
                                out.flush();

                                // Notify all clients about the updated board
                                CheckersServer.broadcastBoardUpdate(game.getBoard());
                            } 
                            catch (IllegalArgumentException e) 
                            {
                                e.printStackTrace();
                                out.writeObject("Error while moving: " + e.getMessage());
                                out.flush();
                            }
                        }
                        break;

                    default:
                        out.writeObject("Error: Unknown request.");
                        System.out.println("Unknown request from client " + clientId + ": " + clientRequest);
                }
            }
        } 
        catch (EOFException e) 
        {
            System.out.println("Client " + clientId + " disconnected.");
        } 
        catch (IOException | ClassNotFoundException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
                clientSocket.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        }
    }

    private Map<Request, Runnable> getDefaultRequestHandler(ObjectOutputStream out, ObjectInputStream in)
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

                out.writeObject(Request.ACKNOWLEDGE);
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
    }
}
