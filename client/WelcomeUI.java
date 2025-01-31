package client;

import shared.Request;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

/**
 * The waiting room UI
 */
public class WelcomeUI
{
    private final VBox root;
    private ImageView logo;
    private Label statusLabel;
    private Label infoLabel;
    private ToggleButton readyButton;

    WelcomeUI()
    {
        root = new VBox();
    }

    public void setupUI(GameRequestMediator requestMediator)
    {
        root.setStyle("-fx-background-color: rgb(50, 50, 50);");
        root.setPrefWidth(400);
        root.setSpacing(10);
        
        // Add logo to welcomeStage
        logo = new ImageView(new Image("file:./data/LOGO.png"));
        logo.setFitWidth(400);
        logo.setFitHeight(400);
        logo.setPreserveRatio(true);
        root.getChildren().add(logo);

        // Add a label that blinks
        infoLabel = new Label("Waiting.....");
        infoLabel.setFont(Font.font("Arial", 24));
        infoLabel.setTextFill(Color.WHITE);
        // // making it blink
        Timeline blinkTimeline = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> infoLabel.setVisible(true)),
                new KeyFrame(Duration.seconds(0.5), e -> infoLabel.setVisible(false))
        );
        blinkTimeline.setCycleCount(Timeline.INDEFINITE);
        blinkTimeline.setAutoReverse(true);
        blinkTimeline.play();

        HBox infolabelBox = new HBox(infoLabel);
        infolabelBox.setAlignment(Pos.CENTER);
        root.getChildren().add(infolabelBox);

        // Add a label that updates live
        statusLabel = new Label("? / ?");
        statusLabel.setFont(Font.font("Arial", 24));
        statusLabel.setTextFill(Color.WHITE);
        HBox labelBox = new HBox(statusLabel);
        labelBox.setAlignment(Pos.CENTER);
        root.getChildren().add(labelBox);

        // Add the "READY" button
        readyButton = new ToggleButton("NOT READY");
        readyButton.setStyle("-fx-border-width: 5px; -fx-border-radius: 10px;-fx-background-radius: 15px;  -fx-border-color: rgb(30, 30, 30); -fx-background-color: rgb(70, 40, 40); -fx-text-fill: white;");
        readyButton.setPrefWidth(150);
        readyButton.setPrefHeight(80); 
        readyButton.setOnAction(event -> 
        {
            if (readyButton.isSelected()) 
            {
                try
                {
                    requestMediator.sendRequest(new Request("READY", Boolean.TRUE));
                }
                catch (IOException e)
                {
                    statusLabel.setText("Unable to send READY");
                }

                readyButton.setText("READY");
                readyButton.setStyle("-fx-border-width: 5px; -fx-border-radius: 10px; -fx-background-radius: 15px;  -fx-border-color: rgb(30, 30, 30); -fx-background-color: rgb(40, 70, 40); -fx-text-fill: white;");
            } 
            else 
            {
                try
                {
                    requestMediator.sendRequest(new Request("READY", Boolean.FALSE));
                }
                catch (IOException e)
                {
                    statusLabel.setText("Unable to send !READY");
                }

                readyButton.setText("NOT READY");
                readyButton.setStyle("-fx-border-width: 5px; -fx-border-radius: 10px; -fx-background-radius: 15px;  -fx-border-color: rgb(30, 30, 30); -fx-background-color: rgb(70, 40, 40); -fx-text-fill: white;");
            }
        });
        HBox buttonBox = new HBox(readyButton);
        buttonBox.setAlignment(Pos.CENTER);
        root.getChildren().add(buttonBox);
    }

    public void updatePlayerCount(int ready, int playerCount)
    {
        if(ready == playerCount)
        {
            infoLabel.setText("EVERYONE IS READY!");
            statusLabel.setText(ready + " / " + playerCount);
        }
        else
        {
            infoLabel.setText("Waiting.....");
            statusLabel.setText(ready + " / " + playerCount);
        }
    }

    public VBox getRoot()
    {
        return root;
    }

    public ToggleButton getToggleButton()
    {
        return readyButton;
    }

    public Label getStatusLabel()
    {
        return statusLabel;
    }
}
