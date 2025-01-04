import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CheckersClient extends Application 
{
    @Override
    public void start(Stage primaryStage) 
    {
        // Set up the game UI
        GameUI gameUI = new GameUI();

        // Start the client connection
        GameClient gameClient = new GameClient("localhost", 12345);
        new Thread(gameClient).start();

        // Create and set up the controller
        GameUiController gameController = new GameUiController(gameUI, gameClient);

        // Show the stage
        Scene scene = new Scene(gameUI.getRoot(), 1190, 880);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Chinese Checkers Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}
