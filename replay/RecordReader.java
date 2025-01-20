package replay;

import com.fasterxml.jackson.databind.ObjectMapper;
import shared.ColorTranslator;
import client.*;
import memento.*;

import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.List;

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
        try
        {
            ObjectMapper objectMapper = new ObjectMapper();

            while (running) 
            {
                SavedMove savedMove = objectMapper.readValue(filePath, SavedMove.class);

                Platform.runLater(() -> 
                {
                    handleUpdate(savedMove);
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

    private void handleUpdate(SavedMove savedMove) 
    {
        if (gameEndPoint == null) 
        {
            throw new IllegalStateException("Game controller is not attached");
        }

        for (Map.Entry<List<Integer>, String> entry : savedMove.board.entrySet()) 
        {
            int[] key = {entry.getKey().get(0), entry.getKey().get(1)};
            Color color = ColorTranslator.get(entry.getValue());
            GraphicNode node = gameEndPoint.findNodeById(key);
            if (node != null) 
            {
                node.setFill(color);
            }
        }

        gameEndPoint.setCurrentLabelText(savedMove.playerColor);
    }

    public void stop() 
    {
        running = false;
    }

    public void start()
    {
        running = true;
    }
}
