import java.io.*;

public class GameUiController 
{
    private final GameUI gameUI;
    private final GameClient gameClient;
    private GraphicNode firstSelectedNode = null;
    private GraphicNode secondSelectedNode = null;

    private boolean locked = false;

    public GameUiController(GameUI gameUI, GameClient gameClient) 
    {
        this.gameUI = gameUI;
        this.gameClient = gameClient;
    }

    public void lock()
    {
        locked = true;
    }

    public void unlock()
    {
        locked = false;
    }

    public void setup()
    {
        // Listen for node clicks
        for (GraphicNode node : this.gameUI.getNodeSet()) 
        {
            node.setOnMouseClicked(event -> 
            {
                if (!gameClient.isMyTurn() || locked) return;
        
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
                        gameClient.sendRequest(request);
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
                            gameClient.sendRequest(request);
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
            if (!gameClient.isMyTurn() || locked)
            {
                gameUI.appendToSystemOutput("It's not your turn, you can't end it");
            }
            else
            {
                Request request = Request.END_TURN;
                try
                {
                    gameClient.sendRequest(request);
                }
                catch (IOException e)
                {
                    gameUI.appendToSystemOutput(e.getMessage());
                    unlock();
                }
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
                    gameClient.sendRequest(request);
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
