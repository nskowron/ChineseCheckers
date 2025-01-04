import java.io.*;
import java.net.Socket;

public class GameClient implements Runnable {

    private final String serverHost;
    private final int serverPort;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public GameClient(String serverHost, int serverPort) 
    {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    @Override
    public void run() 
    {
        try 
        {
            socket = new Socket(serverHost, serverPort);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            System.out.println("Connected to server!");

            int playerId = (int) in.readObject();
            System.out.println("Assigned Player ID: " + playerId);

            listenForServerMessages();

        } 
        catch (Exception e) 
        {
            System.err.println("Error: " + e.getMessage());
        } 
        finally 
        {
            closeConnection();
        }
    }

    public void sendMessage(Object message) 
    {
        try 
        {
            if (out != null) 
            {
                out.writeObject(message);
                out.flush();
            }
        } 
        catch (IOException e) 
        {
            System.err.println("Failed to send message: " + e.getMessage());
        }
    }

    private void listenForServerMessages() 
    {
        try 
        {
            while (true) 
            {
                Object message = in.readObject();
                
                if (message instanceof Request) 
                {
                    Request request = (Request) message;
                    handleRequest(request);
                }
            }
        } 
        catch (Exception e) 
        {
            System.err.println("Connection closed or error: " + e.getMessage());
        }
    }

    private void handleRequest(Request request) 
    {
        //TODO Add more cases (or fix it being Switch (ifs are ugly af))
        switch (request) 
        {
            case UPDATE:
                System.out.println("Update received: " + request.getData());
                break;
            case GREET:
                System.out.println("Greet received: " + request.getData());
                break;
            default:
                System.out.println("Unhandled request type: " + request);
                break;
        }
    }

    private void closeConnection() 
    {
        try 
        {
            if (socket != null) socket.close();
            if (out != null) out.close();
            if (in != null) in.close();
        } 
        catch (IOException e) 
        {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}
