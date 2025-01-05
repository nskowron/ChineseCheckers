import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class CheckersServer 
{
    private static final int PORT = 12345;

    private static int clientIdCounter = 0;
    private static ArrayList<ServerPlayer> connectedClients = new ArrayList<>();
    private static Game game = null;

    private static Object gameStarted = new Object();

    private static Logger LOGGER = Logger.getLogger("Server");

    public static void main(String[] args) 
    {
        try(ServerSocket serverSocket = new ServerSocket(PORT)) 
        {
            LOGGER.info("Server is running on port " + PORT + "...");

            synchronized(gameStarted)
            {
                while(true)
                {
                    // TODO: wait for max 6 players and everyone to be ready
                }
            }

            // while (true) 
            // {
            //     Socket clientSocket = serverSocket.accept();
            //     System.out.println("Client connected with ID: " + clientId);

            //     // Create a new ClientHandler thread for each client
            //     new Thread(new ClientHandler(clientSocket, game, clientId)).start();
            // }
        } 
        catch(IOException e) 
        {
            LOGGER.severe(e.getMessage());
        }
    }

    public static Game getGame()
    {
        return game;
    }

    public static void setReady(Boolean ready, int id)
    {
        for(ServerPlayer client : connectedClients)
        {
            if(client.id == id)
            {
                client.ready = ready;
                return;
            }
        }
    }

    public static synchronized void removeClient(int id) 
    {
        for(int i = 0; i < connectedClients.size(); ++i)
        {
            if(connectedClients.get(i).id == id)
            {
                connectedClients.remove(i);
                // probably do sth else

                return;
            }
        }
    }
}
