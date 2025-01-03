import javafx.application.Application;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CheckersClient extends Application
{

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 12345;

    @Override
    public void start(Stage primaryStage) 
    {
        // Create an instance of your custom BoardGridPane
        BoardGridPane boardGridPane = new BoardGridPane();

        // Generate the star pattern
        boardGridPane.createBoard(25);

        // Set up the scene and stage
        Scene scene = new Scene(boardGridPane, 400, 400);
        primaryStage.setTitle("Chinese Checkers Board");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) 
    {
        launch(args);

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) 
        {
            System.out.println("Connected to server!");

            final int playerId;

            try 
            {
                playerId = (Integer) in.readObject();
                System.out.println("Received Id from server: "+playerId);
                out.writeObject("GET_BOARD");
                out.flush();
                System.out.println("Request sent to server: GET_BOARD");

                // TODO Implement Board display (much later)
                IBoard board = (IBoard) in.readObject();
                System.out.println("Received boardbase  from server");
            } 
            catch (Exception e) 
            {
                System.out.println("Id assigment failed, Error: " + e.getMessage());
                return;
            }
        } 
        catch (IOException e) 
        {
            System.out.println("Error connecting to server: " + e.getMessage());
        }
    }
}
