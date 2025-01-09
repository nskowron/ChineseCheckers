package client;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Insets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GameUI 
{
    private ScrollPane root;
    private final BoardGridPane boardGridPane;
    private Set<GraphicNode> nodeSet;

    private Label currentPlayerLabel;
    private Label playerLabel;
    private Button endTurnButton;
    private Button moveButton;
    private TextArea systemOutputArea;

    public GameUI() 
    {
        root = new ScrollPane();
        this.boardGridPane = new BoardGridPane();
        this.nodeSet = new HashSet<>();
    }

    public ScrollPane getRoot() 
    {
        return root;
    }

    public Set<GraphicNode> getNodeSet() 
    {
        return this.nodeSet;
    }

    public Button getEndTurnButton()
    {
        return endTurnButton;
    }
    public Button getMoveButton()
    {
        return moveButton;
    }

    public void addNode(GraphicNode node) 
    {
        this.nodeSet.add(node);
    }

    public void highlightNode(int[] nodeId)
    {
        GraphicNode node = findNodeById(nodeId);
        if (node != null) 
        {
            node.highlight();
        }
    }

    public void removeHighlightFromNode(int[] nodeId) 
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

    public void setPlayerLabelText(String text)
    {
        playerLabel.setText(text);
    }

    public void setCurrentLabelText(String playerTurn) 
    {
        currentPlayerLabel.setText("Current Player: " + playerTurn);
    }

    public void appendToSystemOutput(String text) 
    {
        systemOutputArea.appendText(text + "\n");
    }

    public GraphicNode findNodeById(int[] nodeId) 
    {
        for (GraphicNode node : this.nodeSet) 
        {
            if (Arrays.equals(node.getGameId(), nodeId)) 
            {
                return node;
            }
        }
        return null;
    }

    public void setupUI() 
    {
        // Set up the Board
        boardGridPane.createBoard(16, this);

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
        systemOutputArea.setStyle("-fx-font-family: Consolas; " +
                                "-fx-font-size: 14px; " +
                                "-fx-text-fill: black;");
        systemOutputArea.setPrefHeight(830);

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

        playerLabel = new Label("You are: ");
        playerLabel.setFont(Font.font("Arial", 18));
        playerLabel.setTextFill(Color.WHITE);

        VBox boardAndPlayer = new VBox();
        boardAndPlayer.setStyle("-fx-background-color: rgb(50, 50, 50); -fx-padding: 10;");
        boardAndPlayer.getChildren().addAll(playerLabel, boardGridPane);

        HBox hbox = new HBox();
        hbox.setSpacing(20);
        hbox.setPadding(new Insets(0, 0, 0, 20));
        hbox.setStyle("-fx-background-color: rgb(50, 50, 50);");

        hbox.getChildren().addAll(boardAndPlayer, rightVbox);

        root = new ScrollPane(hbox);
        root.setStyle("-fx-background-color: rgb(50, 50, 50);");
    }
}

