package client;

import shared.ColorTranslator;
import shared.GameState;
import shared.Player;
import shared.Request;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.lang.Error;

import javafx.application.Platform;
import javafx.scene.paint.Color;

public class GameRequestMediator implements Runnable 
{
    private final String SERVER_ADDRES;
    private final int SERVER_PORT;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private GameUiController gameEndPoint;

    public GameRequestMediator(String SERVER_ADDRES, int SERVER_PORT) 
    {
        this.SERVER_ADDRES = SERVER_ADDRES;
        this.SERVER_PORT = SERVER_PORT;
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
            socket = new Socket(SERVER_ADDRES, SERVER_PORT);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            while (true) 
            {
                Request request = (Request) in.readObject();
                handleServerResponse(request);
            }
        } 
        catch (IOException | ClassNotFoundException e) 
        {
            e.printStackTrace();
            Platform.runLater(() -> 
            {
                gameEndPoint.getGameUI().appendToSystemOutput(e.getMessage());
            });
        }
    }

    public void sendRequest(Request request) throws IOException 
    {
        out.writeObject(request);
        out.flush();
    }

    private void handleServerResponse(Request request) throws IOException, IllegalStateException
    {
        if(gameEndPoint == null)
        {
            throw new IllegalStateException("Missing components", null);
        }
        try
        {   // REMEMBER TO USE unlock() FOR GAME UI AFTER EACH MOVE, UI IS LOCKED EVERY TIME GET MOVES OR MOVE IS SENT
            // TODO Other requests?
            switch (request.getType()) 
            {
                case "GREET":

                    Player player = (Player) request.getData();
                    gameEndPoint.setPlayer(player);
                    Platform.runLater(() -> 
                    {
                        gameEndPoint.getGameUI().setPlayerLabelText("You are " + " (" + player.getId() + ")");
                    });
                    break;

                case "GAME_START":

                    gameEndPoint.lock(); //LOCK FIRST, WAIT FOR UPDATE
                    String playerColor = (String) request.getData();
                    Platform.runLater(() -> 
                    {
                        gameEndPoint.getGameUI().setPlayerLabelText("You are " + playerColor);
                        gameEndPoint.startGame();
                    });
                    break;

                case "GET_MOVES":

                    List<int[]> nodes = (List<int[]>) request.getData(); // THIS MAY NOT WORK...
                    Platform.runLater(() -> 
                    {
                        for(int[] nodeId : nodes)
                        {
                            gameEndPoint.getGameUI().highlightNode(nodeId);
                        }

                        gameEndPoint.unlock();
                    });
                    break;

                case "WAITING":
                    int[] data = (int[]) request.getData();
                    Platform.runLater(() -> 
                    {
                        gameEndPoint.getWelcomeUI().updatePlayerCount(data[0], data[1]);
                    });
                    break;

                case "UPDATE":

                    GameState state = (GameState) request.getData();

                    Platform.runLater(() -> 
                    {
                        for(Map.Entry<int[], String> entry : state.board.entrySet())
                        {
                            int[] key = entry.getKey();   
                            Color color = ColorTranslator.get(entry.getValue());
                            GraphicNode node = gameEndPoint.getGameUI().findNodeById(key);
                            if(node != null)
                            {
                                node.setFill(color);
                            }
                        }

                        if(state.won != null)
                        {
                            gameEndPoint.getGameUI().appendToSystemOutput(state.won.getColor() + " just won!");
                        }

                        if(gameEndPoint.getPlayer().getId() == state.currentTurn.getId())
                        {
                            gameEndPoint.getGameUI().setCurrentLabelText(" YOU! ");
                            gameEndPoint.setMyTurn(true);
                        }
                        else
                        {
                            gameEndPoint.getGameUI().setCurrentLabelText(state.currentTurn.getColor());
                            gameEndPoint.setMyTurn(false);
                        }

                        gameEndPoint.unlock();
                    });
                    break;
                case "WON":
                    Platform.runLater(() -> 
                    {
                         gameEndPoint.won();
                    });
                    break;
                    
                case "ERROR":
                    Platform.runLater(() -> 
                    {
                        gameEndPoint.getGameUI().appendToSystemOutput(((Error) request.getData()).getMessage());
                    });
                    break;

                default:
                    System.out.println("TF Happened");
                    break;
            }
        }
        catch(ClassCastException e)
        {
            e.printStackTrace();
        }
    }
}
