import java.io.*;
import java.net.*;
import java.util.*;

public class CheckersServer 
{
    private static final int PORT = 12345;

    public static void main(String[] args) 
    {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) 
        {
            System.out.println("Server is running on port " + PORT + "...");

            // Initialize a sample board
            // TODO fix this test
            Board board = new Board();
            board.addNode(new Node(10));
            board.addNode(new Node(12));
            board.addNode(new Node(13));

            while (true) 
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected!");

                // Separate Thread for each client
                new Thread(() -> handleClient(clientSocket, board)).start();
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket, Board board) 
    {
        // try-with-resources (closes streams after fail automatically)
        try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())) 
        {

            // Receive request from client (if needed)
            String clientRequest = (String) in.readObject();
            System.out.println("Received request: " + clientRequest);

            // Send the current board state back to the client
            out.writeObject(board);
            System.out.println("Board sent to client.");

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
