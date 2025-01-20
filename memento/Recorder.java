package memento;

import shared.Request;
import shared.GameState;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileWriter;
import java.io.IOException;

public class Recorder 
{
    private Recorder() { throw new UnsupportedOperationException("This is a static class"); }

    private static volatile int moveID = 0;
    private static FileWriter fileWriter;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static boolean initialized = false;

    public static void initialize(String fileName) 
    {
        if (initialized) 
        {
            throw new IllegalStateException("Recorder is already initialized");
        }
        try 
        {
            fileWriter = new FileWriter(fileName, true); // Open file in append mode
            initialized = true;
        } 
        catch (IOException e) 
        {
            throw new RuntimeException("Failed to initialize Recorder: " + e.getMessage());
        }
    }


    public static void record(Request request) 
    {
        if (!initialized) 
        {
            throw new IllegalStateException("Recorder is not initialized");
        }
        if ("UPDATE".equals(request.getType())) 
        {
            try 
            {
                GameState gameState = (GameState) request.getData();

                SavedMove saveableMove = new SavedMove(moveID++, gameState);

                String json = objectMapper.writeValueAsString(saveableMove);

                synchronized (fileWriter) 
                {
                    fileWriter.write(json + System.lineSeparator());
                    fileWriter.flush();
                }
            } 
            catch (ClassCastException e) 
            {
                System.err.println("Failed to cast Request data to GameState: " + e.getMessage());
            } 
            catch (IOException e) 
            {
                System.err.println("Failed to write to JSON file: " + e.getMessage());
            }
        }
    }

    public static void shutdown() 
    {
        if (!initialized) return;
        try 
        {
            if (fileWriter != null) 
            {
                synchronized (fileWriter) 
                {
                    fileWriter.close();
                }
            }
        } 
        catch (IOException e) 
        {
            System.err.println("Failed to close FileWriter: " + e.getMessage());
        } 
        finally 
        {
            initialized = false;
        }
    }
}
