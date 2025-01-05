import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

public class BoardGridPane extends GridPane 
{
    private Map<int[], Pair<int[], Color>> starPositions = new HashMap<>();

    public BoardGridPane() 
    {   
        super();
        setHgap(0);  // Horizontal gap
        setBackground(new Background(new BackgroundFill(Color.rgb(50, 50, 50), null, null)));
        addStarPositions();
    }

    private void addStarPositions() 
    {
        starPositions.put(new int[]{1, 13}, new Pair<>(new int[]{1, 1}, Color.CYAN));

        starPositions.put(new int[]{2, 12}, new Pair<>(new int[]{2, 1}, Color.CYAN));
        starPositions.put(new int[]{2, 14}, new Pair<>(new int[]{2, 2}, Color.CYAN));
        
        starPositions.put(new int[]{3, 11}, new Pair<>(new int[]{3, 1}, Color.CYAN));
        starPositions.put(new int[]{3, 13}, new Pair<>(new int[]{3, 2}, Color.CYAN));
        starPositions.put(new int[]{3, 15}, new Pair<>(new int[]{3, 3}, Color.CYAN));
        
        starPositions.put(new int[]{4, 10}, new Pair<>(new int[]{4, 1}, Color.CYAN));
        starPositions.put(new int[]{4, 12}, new Pair<>(new int[]{4, 2}, Color.CYAN));
        starPositions.put(new int[]{4, 14}, new Pair<>(new int[]{4, 3}, Color.CYAN));
        starPositions.put(new int[]{4, 16}, new Pair<>(new int[]{4, 4}, Color.CYAN));

        starPositions.put(new int[]{5, 1}, new Pair<>(new int[]{5, 1}, Color.BLUE));
        starPositions.put(new int[]{5, 3}, new Pair<>(new int[]{5, 2}, Color.BLUE));
        starPositions.put(new int[]{5, 5}, new Pair<>(new int[]{5, 3}, Color.BLUE));
        starPositions.put(new int[]{5, 7}, new Pair<>(new int[]{5, 4}, Color.BLUE));
        starPositions.put(new int[]{5, 9}, new Pair<>(new int[]{5, 5}, Color.GREY));
        starPositions.put(new int[]{5, 11}, new Pair<>(new int[]{5, 6}, Color.GREY));
        starPositions.put(new int[]{5, 13}, new Pair<>(new int[]{5, 7}, Color.GREY));
        starPositions.put(new int[]{5, 15}, new Pair<>(new int[]{5, 8}, Color.GREY));
        starPositions.put(new int[]{5, 17}, new Pair<>(new int[]{5, 9}, Color.GREY));
        starPositions.put(new int[]{5, 19}, new Pair<>(new int[]{5, 10}, Color.GREEN));
        starPositions.put(new int[]{5, 21}, new Pair<>(new int[]{5, 11}, Color.GREEN));
        starPositions.put(new int[]{5, 23}, new Pair<>(new int[]{5, 12}, Color.GREEN));
        starPositions.put(new int[]{5, 25}, new Pair<>(new int[]{5, 13}, Color.GREEN));
        
        starPositions.put(new int[]{6, 2}, new Pair<>(new int[]{6, 1}, Color.BLUE));
        starPositions.put(new int[]{6, 4}, new Pair<>(new int[]{6, 2}, Color.BLUE));
        starPositions.put(new int[]{6, 6}, new Pair<>(new int[]{6, 3}, Color.BLUE));
        starPositions.put(new int[]{6, 8}, new Pair<>(new int[]{6, 4}, Color.GREY));
        starPositions.put(new int[]{6, 10}, new Pair<>(new int[]{6, 5}, Color.GREY));
        starPositions.put(new int[]{6, 12}, new Pair<>(new int[]{6, 6}, Color.GREY));
        starPositions.put(new int[]{6, 14}, new Pair<>(new int[]{6, 7}, Color.GREY));
        starPositions.put(new int[]{6, 16}, new Pair<>(new int[]{6, 8}, Color.GREY));
        starPositions.put(new int[]{6, 18}, new Pair<>(new int[]{6, 9}, Color.GREY));
        starPositions.put(new int[]{6, 20}, new Pair<>(new int[]{6, 10}, Color.GREEN));
        starPositions.put(new int[]{6, 22}, new Pair<>(new int[]{6, 11}, Color.GREEN));
        starPositions.put(new int[]{6, 24}, new Pair<>(new int[]{6, 12}, Color.GREEN));
        
        starPositions.put(new int[]{7, 3}, new Pair<>(new int[]{7, 1}, Color.BLUE));
        starPositions.put(new int[]{7, 5}, new Pair<>(new int[]{7, 2}, Color.BLUE));
        starPositions.put(new int[]{7, 7}, new Pair<>(new int[]{7, 3}, Color.GREY));
        starPositions.put(new int[]{7, 9}, new Pair<>(new int[]{7, 4}, Color.GREY));
        starPositions.put(new int[]{7, 11}, new Pair<>(new int[]{7, 5}, Color.GREY));
        starPositions.put(new int[]{7, 13}, new Pair<>(new int[]{7, 6}, Color.GREY));
        starPositions.put(new int[]{7, 15}, new Pair<>(new int[]{7, 7}, Color.GREY));
        starPositions.put(new int[]{7, 17}, new Pair<>(new int[]{7, 8}, Color.GREY));
        starPositions.put(new int[]{7, 19}, new Pair<>(new int[]{7, 9}, Color.GREY));
        starPositions.put(new int[]{7, 21}, new Pair<>(new int[]{7, 10}, Color.GREEN));
        starPositions.put(new int[]{7, 23}, new Pair<>(new int[]{7, 11}, Color.GREEN));
        
        starPositions.put(new int[]{8, 4}, new Pair<>(new int[]{8, 1}, Color.BLUE));
        starPositions.put(new int[]{8, 6}, new Pair<>(new int[]{8, 2}, Color.GREY));
        starPositions.put(new int[]{8, 8}, new Pair<>(new int[]{8, 3}, Color.GREY));
        starPositions.put(new int[]{8, 10}, new Pair<>(new int[]{8, 4}, Color.GREY));
        starPositions.put(new int[]{8, 12}, new Pair<>(new int[]{8, 5}, Color.GREY));
        starPositions.put(new int[]{8, 14}, new Pair<>(new int[]{8, 6}, Color.GREY));
        starPositions.put(new int[]{8, 16}, new Pair<>(new int[]{8, 7}, Color.GREY));
        starPositions.put(new int[]{8, 18}, new Pair<>(new int[]{8, 8}, Color.GREY));
        starPositions.put(new int[]{8, 20}, new Pair<>(new int[]{8, 9}, Color.GREY));
        starPositions.put(new int[]{8, 22}, new Pair<>(new int[]{8, 10}, Color.GREEN));

        starPositions.put(new int[]{9, 5}, new Pair<>(new int[]{9, 1}, Color.GREY));
        starPositions.put(new int[]{9, 7}, new Pair<>(new int[]{9, 2}, Color.GREY));
        starPositions.put(new int[]{9, 9}, new Pair<>(new int[]{9, 3}, Color.GREY));
        starPositions.put(new int[]{9, 11}, new Pair<>(new int[]{9, 4}, Color.GREY));
        starPositions.put(new int[]{9, 13}, new Pair<>(new int[]{9, 5}, Color.GREY));
        starPositions.put(new int[]{9, 15}, new Pair<>(new int[]{9, 6}, Color.GREY));
        starPositions.put(new int[]{9, 17}, new Pair<>(new int[]{9, 7}, Color.GREY));
        starPositions.put(new int[]{9, 19}, new Pair<>(new int[]{9, 8}, Color.GREY));
        starPositions.put(new int[]{9, 21}, new Pair<>(new int[]{9, 9}, Color.GREY));

        starPositions.put(new int[]{10, 4}, new Pair<>(new int[]{10, 1}, Color.YELLOW));
        starPositions.put(new int[]{10, 6}, new Pair<>(new int[]{10, 2}, Color.GREY));
        starPositions.put(new int[]{10, 8}, new Pair<>(new int[]{10, 3}, Color.GREY));
        starPositions.put(new int[]{10, 10}, new Pair<>(new int[]{10, 4}, Color.GREY));
        starPositions.put(new int[]{10, 12}, new Pair<>(new int[]{10, 5}, Color.GREY));
        starPositions.put(new int[]{10, 14}, new Pair<>(new int[]{10, 6}, Color.GREY));
        starPositions.put(new int[]{10, 16}, new Pair<>(new int[]{10, 7}, Color.GREY));
        starPositions.put(new int[]{10, 18}, new Pair<>(new int[]{10, 8}, Color.GREY));
        starPositions.put(new int[]{10, 20}, new Pair<>(new int[]{10, 9}, Color.GREY));
        starPositions.put(new int[]{10, 22}, new Pair<>(new int[]{10, 10}, Color.RED));

        starPositions.put(new int[]{11, 3}, new Pair<>(new int[]{11, 1}, Color.YELLOW));
        starPositions.put(new int[]{11, 5}, new Pair<>(new int[]{11, 2}, Color.YELLOW));
        starPositions.put(new int[]{11, 7}, new Pair<>(new int[]{11, 3}, Color.GREY));
        starPositions.put(new int[]{11, 9}, new Pair<>(new int[]{11, 4}, Color.GREY));
        starPositions.put(new int[]{11, 11}, new Pair<>(new int[]{11, 5}, Color.GREY));
        starPositions.put(new int[]{11, 13}, new Pair<>(new int[]{11, 6}, Color.GREY));
        starPositions.put(new int[]{11, 15}, new Pair<>(new int[]{11, 7}, Color.GREY));
        starPositions.put(new int[]{11, 17}, new Pair<>(new int[]{11, 8}, Color.GREY));
        starPositions.put(new int[]{11, 19}, new Pair<>(new int[]{11, 9}, Color.GREY));
        starPositions.put(new int[]{11, 21}, new Pair<>(new int[]{11, 10}, Color.RED));
        starPositions.put(new int[]{11, 23}, new Pair<>(new int[]{11, 11}, Color.RED));

        starPositions.put(new int[]{12, 2}, new Pair<>(new int[]{12, 1}, Color.YELLOW));
        starPositions.put(new int[]{12, 4}, new Pair<>(new int[]{12, 2}, Color.YELLOW));
        starPositions.put(new int[]{12, 6}, new Pair<>(new int[]{12, 3}, Color.YELLOW));
        starPositions.put(new int[]{12, 8}, new Pair<>(new int[]{12, 4}, Color.GREY));
        starPositions.put(new int[]{12, 10}, new Pair<>(new int[]{12, 5}, Color.GREY));
        starPositions.put(new int[]{12, 12}, new Pair<>(new int[]{12, 6}, Color.GREY));
        starPositions.put(new int[]{12, 14}, new Pair<>(new int[]{12, 7}, Color.GREY));
        starPositions.put(new int[]{12, 16}, new Pair<>(new int[]{12, 8}, Color.GREY));
        starPositions.put(new int[]{12, 18}, new Pair<>(new int[]{12, 9}, Color.GREY));
        starPositions.put(new int[]{12, 20}, new Pair<>(new int[]{12, 10}, Color.RED));
        starPositions.put(new int[]{12, 22}, new Pair<>(new int[]{12, 11}, Color.RED));
        starPositions.put(new int[]{12, 24}, new Pair<>(new int[]{12, 12}, Color.RED));

        starPositions.put(new int[]{13, 1}, new Pair<>(new int[]{13, 1}, Color.YELLOW));
        starPositions.put(new int[]{13, 3}, new Pair<>(new int[]{13, 2}, Color.YELLOW));
        starPositions.put(new int[]{13, 5}, new Pair<>(new int[]{13, 3}, Color.YELLOW));
        starPositions.put(new int[]{13, 7}, new Pair<>(new int[]{13, 4}, Color.YELLOW));
        starPositions.put(new int[]{13, 9}, new Pair<>(new int[]{13, 5}, Color.GREY));
        starPositions.put(new int[]{13, 11}, new Pair<>(new int[]{13, 6}, Color.GREY));
        starPositions.put(new int[]{13, 13}, new Pair<>(new int[]{13, 7}, Color.GREY));
        starPositions.put(new int[]{13, 15}, new Pair<>(new int[]{13, 8}, Color.GREY));
        starPositions.put(new int[]{13, 17}, new Pair<>(new int[]{13, 9}, Color.GREY));
        starPositions.put(new int[]{13, 19}, new Pair<>(new int[]{13, 10}, Color.RED));
        starPositions.put(new int[]{13, 21}, new Pair<>(new int[]{13, 11}, Color.RED));
        starPositions.put(new int[]{13, 23}, new Pair<>(new int[]{13, 12}, Color.RED));
        starPositions.put(new int[]{13, 25}, new Pair<>(new int[]{13, 13}, Color.RED));

        starPositions.put(new int[]{14, 10}, new Pair<>(new int[]{14, 1}, Color.MAGENTA));
        starPositions.put(new int[]{14, 12}, new Pair<>(new int[]{14, 2}, Color.MAGENTA));
        starPositions.put(new int[]{14, 14}, new Pair<>(new int[]{14, 3}, Color.MAGENTA));
        starPositions.put(new int[]{14, 16}, new Pair<>(new int[]{14, 4}, Color.MAGENTA));

        starPositions.put(new int[]{15, 11}, new Pair<>(new int[]{15, 1}, Color.MAGENTA));
        starPositions.put(new int[]{15, 13}, new Pair<>(new int[]{15, 2}, Color.MAGENTA));
        starPositions.put(new int[]{15, 15}, new Pair<>(new int[]{15, 3}, Color.MAGENTA));

        starPositions.put(new int[]{16, 12}, new Pair<>(new int[]{16, 1}, Color.MAGENTA));
        starPositions.put(new int[]{16, 14}, new Pair<>(new int[]{16, 2}, Color.MAGENTA));

        starPositions.put(new int[]{17, 13}, new Pair<>(new int[]{17, 1}, Color.MAGENTA));
    }

    public void putGraphicNode(int row, int col, double radius, GameUI UI) 
    {
        int[] positionKey = {row, col};

        // Use Arrays.equals() to compare arrays based on their content
        if (!starPositions.keySet().stream().anyMatch(key -> Arrays.equals(key, positionKey))) 
        {
            return;
        }

        // Find the matching key
        Pair<int[], Color> data = starPositions.entrySet().stream()
                .filter(entry -> Arrays.equals(entry.getKey(), positionKey))
                .map(entry -> entry.getValue())
                .findFirst()
                .orElse(null);

        GraphicNode node = new GraphicNode(data.getFirst(), 0, 0, radius, Color.rgb(50, 50, 50), data.getSecond());

        add(node, col, row);

        UI.addNode(node);
    }

    public void createBoard(double radius, GameUI UI) 
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
