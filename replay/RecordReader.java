package replay;

import com.fasterxml.jackson.databind.ObjectMapper;
import shared.ColorTranslator;
import shared.GameState;
import client.*;
import memento.*;

import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class RecordReader implements Runnable 
{
    private final String filePath;
    private boolean running = true;
    private GameUI gameEndPoint;

    public RecordReader(String filePath) 
    {
        this.filePath = filePath;
    }

    public void addUI(GameUI controller) 
    {
        this.gameEndPoint = controller;
    }

    @Override
    public void run() 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
            ObjectMapper objectMapper = new ObjectMapper();
            String line;

            while (running && (line = reader.readLine()) != null) 
            {
                SavedMove savedMove = objectMapper.readValue(line, SavedMove.class);

                Platform.runLater(() -> 
                {
                    handleUpdate(savedMove.state);
                });

                Thread.sleep(2000);
            }
        } 
        catch (IOException | InterruptedException e) 
        {
            e.printStackTrace();
            stop();
            if (gameEndPoint != null) 
            {
                Platform.runLater(() -> 
                {
                    gameEndPoint.appendToSystemOutput("Error reading record: " + e.getMessage());
                });
            }
        }
    }

    private void handleUpdate(GameState state) 
    {
        if (gameEndPoint == null) 
        {
            throw new IllegalStateException("Game controller is not attached");
        }

        for (Map.Entry<int[], String> entry : state.board.entrySet()) 
        {
            int[] key = entry.getKey();
            Color color = ColorTranslator.get(entry.getValue());
            GraphicNode node = gameEndPoint.findNodeById(key);
            if (node != null) 
            {
                node.setFill(color);
            }
        }

        if (state.won != null) 
        {
            gameEndPoint.appendToSystemOutput(state.won.getColor() + " just won!");
        }

        gameEndPoint.setCurrentLabelText(state.currentTurn.getColor());
    }

    public void stop() 
    {
        running = false;
    }
}
