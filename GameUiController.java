import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class GameUiController 
{
    private final GameUI gameUI;
    private final GameClient gameClient;
    private GraphicNode selectedNode = null;

    public GameUiController(GameUI gameUI, GameClient gameClient) 
    {
        this.gameUI = gameUI;
        this.gameClient = gameClient;

        //Setup interaction handler
        for(GraphicNode node : this.gameUI.getNodeSet())
        {
            node.setOnMouseClicked(event -> 
            {
                if(node.isHighlighted())
                {
                    node.removeHighlight();
                }
                else
                {
                    node.highlight();
                }
                gameUI.appendToSystemOutput("Clicked Node ID: " + node.getGameId()[0] + "," + node.getGameId()[1]);
            });
        }
    }
}
