package client;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

public class BoardGridPane extends GridPane 
{
    private Map<int[], Color> starPositions = new HashMap<>();

    public BoardGridPane() 
    {   
        super();
        setHgap(0);  // Horizontal gap
        setBackground(new Background(new BackgroundFill(Color.rgb(50, 50, 50), null, null)));
        addStarPositions();
    }

    private void addStarPositions() 
    {
        starPositions.put(new int[]{1, 13}, Color.CYAN);
        starPositions.put(new int[]{2, 12}, Color.CYAN);
        starPositions.put(new int[]{2, 14}, Color.CYAN);

        starPositions.put(new int[]{3, 11}, Color.CYAN);
        starPositions.put(new int[]{3, 13}, Color.CYAN);
        starPositions.put(new int[]{3, 15}, Color.CYAN);

        starPositions.put(new int[]{4, 10}, Color.CYAN);
        starPositions.put(new int[]{4, 12}, Color.CYAN);
        starPositions.put(new int[]{4, 14}, Color.CYAN);
        starPositions.put(new int[]{4, 16}, Color.CYAN);

        starPositions.put(new int[]{5, 1}, Color.BLUE);
        starPositions.put(new int[]{5, 3}, Color.BLUE);
        starPositions.put(new int[]{5, 5}, Color.BLUE);
        starPositions.put(new int[]{5, 7}, Color.BLUE);
        starPositions.put(new int[]{5, 9}, Color.GREY);
        starPositions.put(new int[]{5, 11}, Color.GREY);
        starPositions.put(new int[]{5, 13}, Color.GREY);
        starPositions.put(new int[]{5, 15}, Color.GREY);
        starPositions.put(new int[]{5, 17}, Color.GREY);
        starPositions.put(new int[]{5, 19}, Color.GREEN);
        starPositions.put(new int[]{5, 21}, Color.GREEN);
        starPositions.put(new int[]{5, 23}, Color.GREEN);
        starPositions.put(new int[]{5, 25}, Color.GREEN);

        starPositions.put(new int[]{6, 2}, Color.BLUE);
        starPositions.put(new int[]{6, 4}, Color.BLUE);
        starPositions.put(new int[]{6, 6}, Color.BLUE);
        starPositions.put(new int[]{6, 8}, Color.GREY);
        starPositions.put(new int[]{6, 10}, Color.GREY);
        starPositions.put(new int[]{6, 12}, Color.GREY);
        starPositions.put(new int[]{6, 14}, Color.GREY);
        starPositions.put(new int[]{6, 16}, Color.GREY);
        starPositions.put(new int[]{6, 18}, Color.GREY);
        starPositions.put(new int[]{6, 20}, Color.GREEN);
        starPositions.put(new int[]{6, 22}, Color.GREEN);
        starPositions.put(new int[]{6, 24}, Color.GREEN);

        starPositions.put(new int[]{7, 3}, Color.BLUE);
        starPositions.put(new int[]{7, 5}, Color.BLUE);
        starPositions.put(new int[]{7, 7}, Color.GREY);
        starPositions.put(new int[]{7, 9}, Color.GREY);
        starPositions.put(new int[]{7, 11}, Color.GREY);
        starPositions.put(new int[]{7, 13}, Color.GREY);
        starPositions.put(new int[]{7, 15}, Color.GREY);
        starPositions.put(new int[]{7, 17}, Color.GREY);
        starPositions.put(new int[]{7, 19}, Color.GREY);
        starPositions.put(new int[]{7, 21}, Color.GREEN);
        starPositions.put(new int[]{7, 23}, Color.GREEN);

        starPositions.put(new int[]{8, 4}, Color.BLUE);
        starPositions.put(new int[]{8, 6}, Color.GREY);
        starPositions.put(new int[]{8, 8}, Color.GREY);
        starPositions.put(new int[]{8, 10}, Color.GREY);
        starPositions.put(new int[]{8, 12}, Color.GREY);
        starPositions.put(new int[]{8, 14}, Color.GREY);
        starPositions.put(new int[]{8, 16}, Color.GREY);
        starPositions.put(new int[]{8, 18}, Color.GREY);
        starPositions.put(new int[]{8, 20}, Color.GREY);
        starPositions.put(new int[]{8, 22}, Color.GREEN);

        starPositions.put(new int[]{9, 5}, Color.GREY);
        starPositions.put(new int[]{9, 7}, Color.GREY);
        starPositions.put(new int[]{9, 9}, Color.GREY);
        starPositions.put(new int[]{9, 11}, Color.GREY);
        starPositions.put(new int[]{9, 13}, Color.GREY);
        starPositions.put(new int[]{9, 15}, Color.GREY);
        starPositions.put(new int[]{9, 17}, Color.GREY);
        starPositions.put(new int[]{9, 19}, Color.GREY);
        starPositions.put(new int[]{9, 21}, Color.GREY);

        starPositions.put(new int[]{10, 4}, Color.YELLOW);
        starPositions.put(new int[]{10, 6}, Color.GREY);
        starPositions.put(new int[]{10, 8}, Color.GREY);
        starPositions.put(new int[]{10, 10}, Color.GREY);
        starPositions.put(new int[]{10, 12}, Color.GREY);
        starPositions.put(new int[]{10, 14}, Color.GREY);
        starPositions.put(new int[]{10, 16}, Color.GREY);
        starPositions.put(new int[]{10, 18}, Color.GREY);
        starPositions.put(new int[]{10, 20}, Color.GREY);
        starPositions.put(new int[]{10, 22}, Color.RED);

        starPositions.put(new int[]{11, 3}, Color.YELLOW);
        starPositions.put(new int[]{11, 5}, Color.YELLOW);
        starPositions.put(new int[]{11, 7}, Color.GREY);
        starPositions.put(new int[]{11, 9}, Color.GREY);
        starPositions.put(new int[]{11, 11}, Color.GREY);
        starPositions.put(new int[]{11, 13}, Color.GREY);
        starPositions.put(new int[]{11, 15}, Color.GREY);
        starPositions.put(new int[]{11, 17}, Color.GREY);
        starPositions.put(new int[]{11, 19}, Color.GREY);
        starPositions.put(new int[]{11, 21}, Color.RED);
        starPositions.put(new int[]{11, 23}, Color.RED);

        starPositions.put(new int[]{12, 2}, Color.YELLOW);
        starPositions.put(new int[]{12, 4}, Color.YELLOW);
        starPositions.put(new int[]{12, 6}, Color.YELLOW);
        starPositions.put(new int[]{12, 8}, Color.GREY);
        starPositions.put(new int[]{12, 10}, Color.GREY);
        starPositions.put(new int[]{12, 12}, Color.GREY);
        starPositions.put(new int[]{12, 14}, Color.GREY);
        starPositions.put(new int[]{12, 16}, Color.GREY);
        starPositions.put(new int[]{12, 18}, Color.GREY);
        starPositions.put(new int[]{12, 20}, Color.RED);
        starPositions.put(new int[]{12, 22}, Color.RED);
        starPositions.put(new int[]{12, 24}, Color.RED);

        starPositions.put(new int[]{13, 1}, Color.YELLOW);
        starPositions.put(new int[]{13, 3}, Color.YELLOW);
        starPositions.put(new int[]{13, 5}, Color.YELLOW);
        starPositions.put(new int[]{13, 7}, Color.YELLOW);
        starPositions.put(new int[]{13, 9}, Color.GREY);
        starPositions.put(new int[]{13, 11}, Color.GREY);
        starPositions.put(new int[]{13, 13}, Color.GREY);
        starPositions.put(new int[]{13, 15}, Color.GREY);
        starPositions.put(new int[]{13, 17}, Color.GREY);
        starPositions.put(new int[]{13, 19}, Color.RED);
        starPositions.put(new int[]{13, 21}, Color.RED);
        starPositions.put(new int[]{13, 23}, Color.RED);
        starPositions.put(new int[]{13, 25}, Color.RED);

        starPositions.put(new int[]{14, 10}, Color.MAGENTA);
        starPositions.put(new int[]{14, 12}, Color.MAGENTA);
        starPositions.put(new int[]{14, 14}, Color.MAGENTA);
        starPositions.put(new int[]{14, 16}, Color.MAGENTA);

        starPositions.put(new int[]{15, 11}, Color.MAGENTA);
        starPositions.put(new int[]{15, 13}, Color.MAGENTA);
        starPositions.put(new int[]{15, 15}, Color.MAGENTA);

        starPositions.put(new int[]{16, 12}, Color.MAGENTA);
        starPositions.put(new int[]{16, 14}, Color.MAGENTA);

        starPositions.put(new int[]{17, 13}, Color.MAGENTA);
    }

    public void putGraphicNode(int row, int col, double radius, BoardUI UI) 
    {
        int[] positionKey = {row, col};

        // Use Arrays.equals() to compare arrays based on their content
        if (!starPositions.keySet().stream().anyMatch(key -> Arrays.equals(key, positionKey))) 
        {
            return;
        }

        // Find the matching key
        Color data = starPositions.entrySet().stream()
            .filter(entry -> Arrays.equals(entry.getKey(), positionKey))
            .map(entry -> entry.getValue())
            .findFirst()
            .orElse(null);

        GraphicNode node = new GraphicNode(positionKey, 0, 0, radius, Color.rgb(50, 50, 50), data);

        add(node, col, row);

        UI.addNode(node);
    }

    public void createBoard(double radius, BoardUI UI) 
    {
        int rows = 20;
        int cols = 28;

        setVgap(radius*1.2);
        
        for (int row = 0; row < rows; row++) 
        {
            for (int col = 0; col < cols; col++) 
            {
                putGraphicNode(row, col, radius, UI);
            }
        }
    }
}
