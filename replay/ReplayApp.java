package replay;

import org.springframework.context.ConfigurableApplicationContext;

import client.GameUI;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class ReplayApp extends Application 
{
    private static ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
        context = SpringApplication.run(ReplayApp.class);
    }

    @Override
    public void stop() throws Exception {
        context.close();
    }

    @Override
    public void start(Stage primaryStage) 
    {
        String filePath = null;
        var parameters = getParameters(); //kinda like c++ auto

        if (!parameters.getRaw().isEmpty()) 
        {
            filePath = parameters.getRaw().get(0);
        } 
        else 
        {
            System.err.println("Usage: java ReplayApp <file_path>");
            System.exit(1);
        }

        GameUI gameUI = new GameUI();
        RecordReader reader = new RecordReader(filePath);

        reader.addUI(gameUI);
        reader.addAlert(()->
        {
            Platform.runLater(()->
            {
                Alert infoAlert = new Alert(AlertType.INFORMATION);
                infoAlert.setTitle("Recording Finished");
                infoAlert.setHeaderText("No more moves to play");
                infoAlert.setContentText("End of recording.");
                infoAlert.showAndWait();
            });
        });
        gameUI.setupUI();

        gameUI.getEndTurnButton().setText("INACTIVE");
        gameUI.getMoveButton().setText("INACTIVE");
        gameUI.setPlayerLabelText("YOU ARE WATCHING A REPLAY");

        Scene scene = new Scene(gameUI.getRoot(), 1257, 975);
        primaryStage.setTitle("Chinese Checkers Replay");
        primaryStage.setScene(scene);
        primaryStage.show();

        Thread readerThread = new Thread(reader);

        primaryStage.setOnCloseRequest(event -> 
        {
           readerThread.interrupt(); 
        });

        readerThread.start();
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}
