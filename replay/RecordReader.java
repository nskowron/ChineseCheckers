package replay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import shared.ColorTranslator;
import client.*;
import memento.*;

import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

import java.util.List;

public class RecordReader implements Runnable 
{
    private final String filePath;
    private boolean running = true;
    private GameUI gameEndPoint;
    private Runnable alert;

    public RecordReader(String filePath) 
    {
        this.filePath = filePath;
    }

    public void addUI(GameUI controller) 
    {
        this.gameEndPoint = controller;
    }

    public void addAlert(Runnable alert)
    {
        this.alert = alert;
    }

    @Override
    public void run() 
    {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addKeyDeserializer(List.class, new ListKeyDeserializer());
        objectMapper.registerModule(module);

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) 
        {
            String line;
            while (running && (line = reader.readLine()) != null) 
            {
                SavedMove savedMove;
                try 
                {
                    savedMove = objectMapper.readValue(line, SavedMove.class);
                } 
                catch (Exception e) 
                {
                    System.err.println("Error deserializing JSON from line: " + line);
                    e.printStackTrace();
                    Platform.runLater(() -> 
                    {
                        if (gameEndPoint != null) 
                        {
                            gameEndPoint.appendToSystemOutput("Error deserializing line: " + e.getMessage());
                        }
                    });
                    continue;
                }

                // Update the UI
                Platform.runLater(() -> handleUpdate(savedMove));

                try 
                {
                    Thread.sleep(2000);
                } 
                catch (InterruptedException e) 
                {
                    System.err.println("Thread interrupted: " + e.getMessage());
                    Thread.currentThread().interrupt();
                    stop();
                    break;
                }
            }
            System.out.println("END OF RECORDING");
            alert.run();
        } 
        catch (IOException e) 
        {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
            Platform.runLater(() -> 
            {
                if (gameEndPoint != null) 
                {
                    gameEndPoint.appendToSystemOutput("Error reading file: " + e.getMessage());
                }
            });
            stop();
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
