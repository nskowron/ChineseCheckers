package client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


public class CheckersClientApp extends Application 
{
    private Stage welcomeStage;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) 
    {
        this.primaryStage = primaryStage;

        // Create the welcomeStage
        welcomeStage = new Stage();
        StackPane welcomeLayout = new StackPane();
        
        // Add logo to welcomeStage
        ImageView logo = new ImageView(new Image("file:./data/LOGO.png"));
        logo.setFitWidth(400);
        logo.setFitHeight(400);
        logo.setPreserveRatio(true);
        welcomeLayout.getChildren().add(logo);

        // Add the "READY" button
        Button readyButton = new Button("READY");
        readyButton.setOnAction(e -> 
        {
            // Hide the welcomeStage
            welcomeStage.hide();
            
            // Show the primaryStage
            primaryStage.show();

            // You can update the game-related information, e.g., game status
            System.out.println("Game is now ready!");
        });
        

        welcomeLayout.getChildren().add(readyButton);
        
        // Add a label that updates live
        Text statusLabel = new Text("Status: Waiting...");
        welcomeLayout.getChildren().add(statusLabel);

        Scene welcomeScene = new Scene(welcomeLayout, 400, 400);
        welcomeStage.setTitle("Welcome");
        welcomeStage.setScene(welcomeScene);
        welcomeStage.show();

        // Set up the game UI
        GameUI gameUI = new GameUI();
        // Start the client 
        GameRequestMediator gameClient = new GameRequestMediator("localhost", 12345);
        // Create the controller
        GameUiController gameController = new GameUiController(gameUI, gameClient);
        // They are Co-dependent
        gameClient.addGameController(gameController);
        // Set them up
        new Thread(gameClient).start();
        gameController.setup();

        // Show the stage
        Scene scene = new Scene(gameUI.getRoot(), 1250, 950);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Chinese Checkers Client");
        primaryStage.setScene(scene);
        //TODO IMPLEMENT 2 STAGES
        primaryStage.show();
    }

    public void moveToGame() 
    {
        welcomeStage.hide();
        primaryStage.show();
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}
