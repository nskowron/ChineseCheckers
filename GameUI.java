import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Insets;

import java.util.HashSet;
import java.util.Set;

public class GameUI 
{
    private final VBox root;
    private final BoardGridPane boardGridPane;
    private Set<GraphicNode> nodeSet;

    private Label currentPlayerLabel;
    private Button endTurnButton;
    private Button moveButton;
    private TextArea systemOutputArea;

    public GameUI() 
    {
        this.root = new VBox();
        root.setStyle("-fx-background-color: rgb(50, 50, 50);");

        this.boardGridPane = new BoardGridPane();
        this.nodeSet = new HashSet<>();

        setupUI();
    }

    public VBox getRoot() 
    {
        return root;
    }

    public Set<GraphicNode> getNodeSet() 
    {
        return this.nodeSet;
    }

    public void addNode(GraphicNode node) 
    {
        this.nodeSet.add(node);
    }

    public void highlightNode(String nodeId)
    {
        GraphicNode node = findNodeById(nodeId);
        if (node != null) 
        {
            node.highlight();
        }
    }

    public void removeHighlightFromNode(String nodeId) 
    {
        GraphicNode node = findNodeById(nodeId);
        if (node != null) 
        {
            node.removeHighlight();
        }
    }

    public void clearAllHighlights() 
    {
        for (GraphicNode node : this.nodeSet) 
        {
            node.removeHighlight();
        }
    }

    public void setCurrentPlayerTurn(int playerTurn) 
    {
        currentPlayerLabel.setText("Current Player: " + playerTurn);
    }

    public void appendToSystemOutput(String text) 
    {
        systemOutputArea.appendText(text + "\n");
    }

    private GraphicNode findNodeById(String nodeId) 
    {
        for (GraphicNode node : this.nodeSet) 
        {
            if (node.getGameId().equals(nodeId)) 
            {
                return node;
            }
        }
        return null;
    }

    private void setupUI() 
    {
        // Set up the Board
        boardGridPane.createBoard(14, this);

        VBox rightVbox = new VBox();
        rightVbox.setStyle("-fx-background-color: rgb(50, 50, 50);");
        rightVbox.setPrefWidth(300);

        // Create top-left label for current player
        currentPlayerLabel = new Label("Current Player: 0");
        currentPlayerLabel.setFont(Font.font("Arial", 18));
        currentPlayerLabel.setTextFill(Color.WHITE);

        // Create HBox to center the label and set background color
        HBox topBox = new HBox(currentPlayerLabel);
        topBox.setAlignment(Pos.CENTER);
        topBox.setStyle("-fx-background-color: rgb(50, 50, 50); -fx-padding: 10;");

        rightVbox.getChildren().add(topBox);

        // Create Logger Area
        systemOutputArea = new TextArea();
        systemOutputArea.setEditable(false);
        systemOutputArea.setStyle("-fx-background-color: rgb(50, 50, 50); -fx-font-family: Consolas; -fx-font-size: 14px;");
        systemOutputArea.setPrefHeight(730);

        rightVbox.getChildren().add(systemOutputArea);

        // Create buttons
        endTurnButton = new Button("END TURN");
        moveButton = new Button("MOVE");
        endTurnButton.setPrefSize(150, 80);
        moveButton.setPrefSize(150, 80);

        GridPane grid = new GridPane();
        grid.add(moveButton, 1, 0);
        grid.add(endTurnButton, 0, 0);

        rightVbox.getChildren().add(grid);

        BorderPane borderPane = new BorderPane();

        // Create an HBox for left and right sections
        HBox hbox = new HBox();
        hbox.setSpacing(20);  // Set spacing between the left and right sections
        hbox.setPadding(new Insets(0, 0, 0, 20)); // Set padding (left gap of 20px)
        hbox.setStyle("-fx-background-color: rgb(50, 50, 50);");

        // Set boardGridPane on the left and rightVbox on the right of the HBox
        hbox.getChildren().addAll(boardGridPane, rightVbox);
        
        // Set the HBox as the center of the BorderPane
        borderPane.setCenter(hbox);
        borderPane.setStyle("-fx-background-color: rgb(50, 50, 50);");

        root.getChildren().add(borderPane);
    }
}

