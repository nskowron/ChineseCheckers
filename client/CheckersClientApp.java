package client;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


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
        Scene scene = new Scene(gameUI.getRoot(), 1257, 975); //Oddly specific...
        this.primaryStage.setTitle("Chinese Checkers Client");
        this.primaryStage.setScene(scene);

        if (getParameters().getRaw().contains("resize")) 
        {
            // Make the stage resizable if the argument "resize" is passed
            primaryStage.setResizable(true);
        } 
        else 
        {
            // Set the stage to non-resizable by default
            primaryStage.setResizable(false);
        }

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
