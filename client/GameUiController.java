package client;

import shared.Move;
import shared.Request;
import shared.Player;

import java.io.*;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class GameUiController 
{
    private final GameUI gameUI;
    private final WelcomeUI welcomeUI;
    private final GameRequestMediator requestReceiver;

    // Move Helpers
    private GraphicNode firstSelectedNode = null;
    private GraphicNode secondSelectedNode = null;

    // UI Stages
    private Stage waitingRoom;
    private Stage gameRoom;

    // Current Data
    private boolean myTurn = false;
    private boolean locked = false;
    private Player player;

    public GameUiController(GameUI gameUI, WelcomeUI welcomeUI, GameRequestMediator requestReceiver) 
    {
        this.gameUI = gameUI;
        this.welcomeUI = welcomeUI;
        this.requestReceiver = requestReceiver;
        gameUI.setupUI();
        welcomeUI.setupUI(requestReceiver);
    }

    public void lock()
    {
        locked = true;
    }

    public void unlock()
    {
        locked = false;
    }

    public void startGame() throws IllegalStateException
    {
        if(waitingRoom != null && gameRoom != null)
        {
            waitingRoom.close();
            gameRoom.show();
        }
        else
        {
            throw new IllegalStateException("Missing components", null);
        }
    }

    public void endGame()
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.getDialogPane().setStyle("-fx-text-fill: white; -fx-background-color: rgb(50,5050,50); -fx-font-size: 20px; -fx-border-width: 5px; -fx-border-radius: 10px; -fx-background-radius: 15px;  -fx-border-color: rgb(30, 30, 30); -fx-font-family: 'Arial';");
        alert.setHeaderText("Game Has Ended");
        alert.setContentText("SOMEONE WON, BUT NOT YOU :C");
        alert.showAndWait().ifPresent(response -> 
        {
            if (response == ButtonType.FINISH) 
            {
                Platform.exit(); 
            }
        });
    }

    public void won()
    {
        Alert alert = new Alert(AlertType.ERROR);
        alert.getDialogPane().setStyle("-fx-text-fill: white; -fx-background-color: rgb(50,5050,50); -fx-font-size: 20px; -fx-border-width: 5px; -fx-border-radius: 10px; -fx-background-radius: 15px;  -fx-border-color: rgb(30, 30, 30); -fx-font-family: 'Arial';");
        alert.setHeaderText("Game Has Ended");
        alert.setContentText("You Have WON!");
        alert.showAndWait().ifPresent(response -> 
        {
            if (response == ButtonType.FINISH) 
            {
                Platform.exit(); 
            }
        });
    }

    public void setup()
    {
        // Listen for node clicks
        for (GraphicNode node : this.gameUI.getNodeSet()) 
        {
            node.setOnMouseClicked(event -> 
            {
                if (!myTurn || locked) return;
        
                if(node == firstSelectedNode)
                {
                    gameUI.clearAllHighlights();
                    firstSelectedNode = null;
                    return;
                }
                else if(firstSelectedNode == null)
                {
                    firstSelectedNode = node;
                    node.highlight();
                    lock();
        
                    Request request = Request.GET_MOVES;
                    request.setData(firstSelectedNode.getGameId());
                    try
                    {
                        requestReceiver.sendRequest(request);
                    }
                    catch (IOException e)
                    {
                        gameUI.appendToSystemOutput(e.getMessage());
                        unlock();
                    }
                }
        
                if(node.isHighlighted())
                {
                    if(node == secondSelectedNode)
                    {
                        Request request = Request.GET_MOVES;
                        request.setData(firstSelectedNode.getGameId());
                        try
                        {
                            requestReceiver.sendRequest(request);
                        }
                        catch (IOException e)
                        {
                            gameUI.appendToSystemOutput(e.getMessage());
                            unlock();
                        }
                    }
                    else
                    {
                        secondSelectedNode = node;
        
                        gameUI.clearAllHighlights();
                        secondSelectedNode.highlight();
                        firstSelectedNode.highlight();
                    }
                }
            });
        }

        gameUI.getEndTurnButton().setOnAction(event -> 
        {
            if(!locked)
            {
                if (!myTurn)
                {
                    gameUI.appendToSystemOutput("It's not your turn, you can't end it");
                }
                else
                {
                    Request request = Request.END_TURN;
                    try
                    {
                        requestReceiver.sendRequest(request);
                    }
                    catch (IOException e)
                    {
                        gameUI.appendToSystemOutput(e.getMessage());
                        unlock();
                    }
                }
            }
            else
            {
                gameUI.appendToSystemOutput("UI temporary locked, awaiting server response....");
            }
        });

        gameUI.getMoveButton().setOnAction(event -> 
        {
            if (firstSelectedNode != null && secondSelectedNode != null) 
            {
                lock();
                Request request = Request.MOVE;
                request.setData(new Move(firstSelectedNode.getGameId(), secondSelectedNode.getGameId()));
                try
                {
                    requestReceiver.sendRequest(request);
                    gameUI.clearAllHighlights();
                    firstSelectedNode = null;
                    secondSelectedNode = null;
                }
                catch (IOException e)
                {
                    gameUI.appendToSystemOutput(e.getMessage());
                    gameUI.clearAllHighlights();
                    unlock();
                }
            }
            else
            {
                gameUI.appendToSystemOutput("Please select 2 Nodes to confirm move");
            }
        });
    }

    public void setRooms(Stage waitingRoom, Stage gameRoom)
    {
        this.waitingRoom = waitingRoom;
        this.gameRoom = gameRoom;
    }

    public GameUI getGameUI()
    {
        return this.gameUI;
    }

    public WelcomeUI getWelcomeUI()
    {
        return this.welcomeUI;
    }

    public void setMyTurn(Boolean input)
    {
        this.myTurn = input;
    }

    public Boolean isTurn()
    {
        return myTurn;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public Player getPlayer()
    {
        return this.player;
    }
}
