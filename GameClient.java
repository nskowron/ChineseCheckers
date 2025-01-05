import java.io.*;
import java.net.*;

public class GameClient implements Runnable 
{
    private final String serverAddress;
    private final int serverPort;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private GameUiController uiController; // FIX IT

    private int playerId;
    private boolean myTurn = false;

    public GameClient(String serverAddress, int serverPort) 
    {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    @Override
    public void run() 
    {
        try 
        {
            socket = new Socket(serverAddress, serverPort);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            sendRequest(Request.GREET);

            while (true) 
            {
                Request request = (Request) in.readObject();
                handleServerResponse(request);
            }
        } 
        catch (IOException | ClassNotFoundException e) 
        {
            e.printStackTrace();
        }
    }

    public void sendRequest(Request request) throws IOException 
    {
        out.writeObject(request);
        out.flush();
    }

    private void handleServerResponse(Request request) throws IOException 
    {
        //TODO REMEMBER TO USE unlock() FOR GAME UI AFTER EACH MOVE, UI IS LOCKED EVERY TIME GET MOVES OR MOVE IS SENT
        switch (request) 
        {
            case GREET:
                Player player = (Player) request.getData();
                playerId = player.getId();
                break;
            case GET_MOVES:
                // Handle GET_MOVES response and update the UI
                break;
            case MOVE:
                myTurn = false;
                break;
            case UPDATE:
                // Handle game state update and notify GameUIController
                break;
            default:
                // Handle other requests
                break;
        }
    }

    public int getPlayerId() 
    {
        return playerId;
    }

    public void setTurn(boolean isTurn)
    {
        myTurn = isTurn;
    }

    public boolean isMyTurn() 
    {
        return myTurn;
    }
}
