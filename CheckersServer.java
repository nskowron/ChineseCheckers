import java.io.*;
import java.net.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CheckersServer 
{
    private static final int PORT = 12345;
    private static AtomicInteger clientIdCounter = new AtomicInteger(1);

    public static void main(String[] args) 
    {
        Game game = new Game(new ValidityChecker(), new Board());

        try (ServerSocket serverSocket = new ServerSocket(PORT)) 
        {
            System.out.println("Server is running on port " + PORT + "...");

            while (true) 
            {
                Socket clientSocket = serverSocket.accept();
                int clientId = clientIdCounter.getAndIncrement();
                System.out.println("Client connected with ID: " + clientId);

                // Separate thread for each client
                new Thread(() -> handleClient(clientSocket, game, clientId)).start();
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket, Game game, int clientId) 
    {
        try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) 
        {

            // Send greeting with client ID
            out.writeObject(clientId);
            out.flush();
            
            while (true) 
            {
                // Receive request from client
                String clientRequest = (String) in.readObject();
                System.out.println("Client " + clientId + " request: " + clientRequest);

                switch (clientRequest) 
                {
                    case "GET_BOARD":
                        // Send the current board state
                        out.writeObject(game.getBoard());
                        System.out.println("Board sent to client " + clientId + ".");
                        break;

                    case "GET_READY":
                        //  (dummy response for now)
                        out.writeObject("Client " + clientId + " is now ready!");
                        System.out.println("Client " + clientId + " marked as ready.");
                        break;

                    case "GET_NOT_READY":
                        // (dummy response for now)
                        out.writeObject("Client " + clientId + " is now not ready.");
                        System.out.println("Client " + clientId + " marked as not ready.");
                        break;

                    case "SEND_MOVE":
                        // Receive move details
                        Move move = (Move) in.readObject(); // Cast to Move
                        int moveBegin = move.startId;       // Access beginId
                        int moveEnd = move.endId;           // Access endId
                        int playerId = move.playerId;       // Access playerId if needed

                        System.out.println("Client " + clientId + " sent move: " + moveBegin + " -> " + moveEnd);

                        try
                        {
                            game.move(move);
                        }
                        catch (IllegalArgumentException e)
                        {
                            e.printStackTrace();
                        }
                        break;

                    default:
                        // Unknown command
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
}
