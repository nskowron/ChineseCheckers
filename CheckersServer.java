import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CheckersServer 
{
    private static final int PORT = 12345;
    private static AtomicInteger clientIdCounter = new AtomicInteger(1);
    private static List<ClientHandler> connectedClients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) 
    {
        Request req = Request.MOVE;
        Game game = new Game(new ValidityChecker(), new Board());

        try (ServerSocket serverSocket = new ServerSocket(PORT)) 
        {
            System.out.println("Server is running on port " + PORT + "...");

            while (true) 
            {
                Socket clientSocket = serverSocket.accept();
                int clientId = clientIdCounter.getAndIncrement();
                System.out.println("Client connected with ID: " + clientId);

                // Create a new ClientHandler thread for each client
                new Thread(new ClientHandler(clientSocket, game, clientId)).start();
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static void broadcastBoardUpdate(IBoard board) 
    {
        for (ClientHandler clientHandler : connectedClients) 
        {
            clientHandler.sendBoardUpdate(board);
        }
    }

    // Remove clients from the list when they disconnect
    public static void removeClient(ClientHandler client) 
    {
        connectedClients.remove(client);
        System.out.println("Client " + client.clientId + " removed.");
    }
}
