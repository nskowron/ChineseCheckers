package client;

import shared.GameState;
import shared.Player;
import shared.Request;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javafx.scene.paint.Color;

public class GameRequestMediator implements Runnable 
{
    private final String serverAddress;
    private final int serverPort;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private GameUiController gameEndPoint;

    public GameRequestMediator(String serverAddress, int serverPort) 
    {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void addGameController(GameUiController controler)
    {
        this.gameEndPoint = controler;
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
        if(gameEndPoint == null)
        {
            throw new IllegalStateException("can't really handle response with no endpoint...", null);
        }
        try
        {   //TODO REMEMBER TO USE unlock() FOR GAME UI AFTER EACH MOVE, UI IS LOCKED EVERY TIME GET MOVES OR MOVE IS SENT
            switch (request) 
            {
                case GREET:

                    Player player = (Player) request.getData();
                    gameEndPoint.setPlayer(player);
                    //TODO FILL BOARD WITH CORRECT COLORS (PIONKI)

                    break;
                case GET_MOVES:

                    List<int[]> nodes = (List<int[]>) request.getData();
                    for(int[] nodeId : nodes)
                    {
                        gameEndPoint.getGameUI().highlightNode(nodeId);
                    }

                    gameEndPoint.unlock();

                    break;
                case UPDATE:

                    GameState state = (GameState) request.getData();

                    for(Map.Entry<int[], Color> entry : state.board.entrySet())
                    {
                        int[] key = entry.getKey();   
                        Color color = entry.getValue();
                        GraphicNode node = gameEndPoint.getGameUI().findNodeById(key);
                        if(node != null)
                        {
                            node.setFill(color);
                        }
                    }

                    if(gameEndPoint.getPlayer().getId() == state.currentTurn.getId())
                    {
                        gameEndPoint.setMyTurn(true);
                        
                        if(state.won)
                        {
                            gameEndPoint.won();
                        }
                    }
                    else
                    {
                        gameEndPoint.setMyTurn(false);
                    }

                    gameEndPoint.unlock();

                    break;
                case READY:

                    // UPDATE THE WELCOME SCREEN

                    break;
                default:
                    throw new IllegalArgumentException("TF Happened");
            }
        }
        catch(ClassCastException e)
        {
            e.printStackTrace();
        }
    }
}
