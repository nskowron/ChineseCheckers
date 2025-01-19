package replay;

import client.GameUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ReplayApp extends Application 
{
    @Override
    public void start(Stage primaryStage) 
    {
        String filePath = null;
        var parameters = getParameters();
        if (!parameters.getRaw().isEmpty()) 
        {
            filePath = parameters.getRaw().get(0); // Get the first argument
        } 
        else 
        {
            System.err.println("Usage: java ReplayApp <file_path>");
            System.exit(1);
        }

        // Initialize the UI and RecordReader
        GameUI gameUI = new GameUI();
        RecordReader reader = new RecordReader(filePath);

        // Attach the GameUI controller to the RecordReader
        reader.addUI(gameUI);

        // Start the RecordReader in a new thread
        Thread readerThread = new Thread(reader);
        readerThread.start();

        Scene scene = new Scene(gameUI.getRoot(), 1257, 975); //Oddly specific...
        primaryStage.setTitle("Chinese Checkers Replay");
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) 
    {
        launch(args); // Pass the command-line arguments to JavaFX
    }
}
