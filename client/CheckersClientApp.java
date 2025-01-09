package client;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.transform.Scale;


public class CheckersClientApp extends Application 
{
    private Stage welcomeStage;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) 
    {
        // Create the UI's and Controllers
        GameUI gameUI = new GameUI();
        WelcomeUI welcomeUI = new WelcomeUI();
        GameRequestMediator requestMediator = new GameRequestMediator("localhost", 12345);
        GameUiController gameController = new GameUiController(gameUI, welcomeUI, requestMediator);

        // Welcome Stage
        welcomeStage = new Stage();
        Scene welcomeScene = new Scene(welcomeUI.getRoot(), 400, 600);
        welcomeStage.setTitle("Welcome");
        welcomeStage.setScene(welcomeScene);
        welcomeStage.setResizable(false);

        // Main Stage
        this.primaryStage = primaryStage;
        VBox vbox = new VBox(gameUI.getRoot());
        vbox.setStyle("-fx-background-color: rgb(50, 50, 50);");
        Scene scene = new Scene(vbox, 1250, 980);
        this.primaryStage.setTitle("Chinese Checkers Client");
        this.primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        // Continue with setting up Controllers
        gameController.setRooms(welcomeStage, primaryStage);
        gameController.setup();
        requestMediator.addGameController(gameController);
        Thread requestThread = new Thread(requestMediator);
        gameController.setRequesterThread(requestThread);
        requestThread.start();

        // Start the app
        welcomeStage.show();
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}
