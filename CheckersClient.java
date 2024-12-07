import java.io.*;
import java.net.*;

public class CheckersClient 
{
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) 
    {
        // try-with-resources (closes streams after fail automatically)
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) 
        {

            // Send a request to the server
            String request = "GET_BOARD";
            out.writeObject(request);
            System.out.println("Request sent to server: " + request);

            // Receive the Board object from the server
            Board board = (Board) in.readObject();
            System.out.println("Received board from server:");
            System.out.println(board);

        } 
        catch (IOException | ClassNotFoundException e) 
        {
            e.printStackTrace();
        }
    }
}
