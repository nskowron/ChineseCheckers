package client;

import shared.Move;
import shared.Request;
import shared.Player;

import java.io.*;

public class GameUiController 
{
    private final GameUI gameUI;
    private final GameRequestMediator requestReceiver;

    // Move Helpers
    private GraphicNode firstSelectedNode = null;
    private GraphicNode secondSelectedNode = null;

    // Current Data
    private boolean myTurn = false;
    private boolean locked = false;
    private Player player;

    public GameUiController(GameUI gameUI, GameRequestMediator requestReceiver) 
    {
        this.gameUI = gameUI;
        this.requestReceiver = requestReceiver;
    }

    public void won()
    {
        //TODO IDK SOMETHING TO SHOW THAT YOU WON?
    }

    public GameUI getGameUI()
    {
        return this.gameUI;
    }

    public void lock()
    {
        locked = true;
    }

    public void unlock()
    {
        locked = false;
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
}
