import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

public class BoardGridPane extends GridPane {

    private Map<String, Color> starPositions = new HashMap<>(); // Set to store star positions

    public BoardGridPane() 
    {   
        super();
        setHgap(3);  // Horizontal gap
        setVgap(6);  // Vertical gap
        setBackground(new Background(new BackgroundFill(Color.rgb(50, 50, 50), null, null)));
        addStarPositions();
    }

    // Method to add star positions (the positions where you want to place nodes)
    private void addStarPositions() 
    {
        starPositions.put("1,13", Color.WHITE);

        starPositions.put("2,12", Color.WHITE);
        starPositions.put("2,14", Color.WHITE);

        starPositions.put("3,11", Color.WHITE);
        starPositions.put("3,13", Color.WHITE);
        starPositions.put("3,15", Color.WHITE);

        starPositions.put("4,10", Color.WHITE);
        starPositions.put("4,12", Color.WHITE);
        starPositions.put("4,14", Color.WHITE);
        starPositions.put("4,16", Color.WHITE);

        starPositions.put("5,1", Color.BLUE);
        starPositions.put("5,3", Color.BLUE);
        starPositions.put("5,5", Color.BLUE);
        starPositions.put("5,7", Color.BLUE);
        starPositions.put("5,9", Color.GRAY);
        starPositions.put("5,11", Color.GRAY);
        starPositions.put("5,13", Color.GRAY);
        starPositions.put("5,15", Color.GRAY);
        starPositions.put("5,17", Color.GRAY);
        starPositions.put("5,19", Color.GREEN);
        starPositions.put("5,21", Color.GREEN);
        starPositions.put("5,23", Color.GREEN);
        starPositions.put("5,25", Color.GREEN);

        starPositions.put("6,2", Color.BLUE);
        starPositions.put("6,4", Color.BLUE);
        starPositions.put("6,6", Color.BLUE);
        starPositions.put("6,8", Color.GRAY);
        starPositions.put("6,10", Color.GRAY);
        starPositions.put("6,12", Color.GRAY);
        starPositions.put("6,14", Color.GRAY);
        starPositions.put("6,16", Color.GRAY);
        starPositions.put("6,18", Color.GRAY);
        starPositions.put("6,20", Color.GREEN);
        starPositions.put("6,22", Color.GREEN);
        starPositions.put("6,24", Color.GREEN);

        starPositions.put("7,3", Color.BLUE);
        starPositions.put("7,5", Color.BLUE);
        starPositions.put("7,7", Color.GRAY);
        starPositions.put("7,9", Color.GRAY);
        starPositions.put("7,11", Color.GRAY);
        starPositions.put("7,13", Color.GRAY);
        starPositions.put("7,15", Color.GRAY);
        starPositions.put("7,17", Color.GRAY);
        starPositions.put("7,19", Color.GRAY);
        starPositions.put("7,21", Color.GREEN);
        starPositions.put("7,23", Color.GREEN);

        starPositions.put("8,4", Color.BLUE);
        starPositions.put("8,6", Color.GRAY);
        starPositions.put("8,8", Color.GRAY);
        starPositions.put("8,10", Color.GRAY);
        starPositions.put("8,12", Color.GRAY);
        starPositions.put("8,14", Color.GRAY);
        starPositions.put("8,16", Color.GRAY);
        starPositions.put("8,18", Color.GRAY);
        starPositions.put("8,20", Color.GRAY);
        starPositions.put("8,22", Color.GREEN);

        starPositions.put("9,5", Color.GRAY);
        starPositions.put("9,7", Color.GRAY);
        starPositions.put("9,9", Color.GRAY);
        starPositions.put("9,11", Color.GRAY);
        starPositions.put("9,13", Color.GRAY);
        starPositions.put("9,15", Color.GRAY);
        starPositions.put("9,17", Color.GRAY);
        starPositions.put("9,19", Color.GRAY);
        starPositions.put("9,21", Color.GRAY);

        starPositions.put("10,4", Color.YELLOW);
        starPositions.put("10,6", Color.GRAY);
        starPositions.put("10,8", Color.GRAY);
        starPositions.put("10,10", Color.GRAY);
        starPositions.put("10,12", Color.GRAY);
        starPositions.put("10,14", Color.GRAY);
        starPositions.put("10,16", Color.GRAY);
        starPositions.put("10,18", Color.GRAY);
        starPositions.put("10,20", Color.GRAY);
        starPositions.put("10,22", Color.RED);
        
        starPositions.put("11,3", Color.YELLOW);
        starPositions.put("11,5", Color.YELLOW);
        starPositions.put("11,7", Color.GRAY);
        starPositions.put("11,9", Color.GRAY);
        starPositions.put("11,11", Color.GRAY);
        starPositions.put("11,13", Color.GRAY);
        starPositions.put("11,15", Color.GRAY);
        starPositions.put("11,17", Color.GRAY);
        starPositions.put("11,19", Color.GRAY);
        starPositions.put("11,21", Color.RED);
        starPositions.put("11,23", Color.RED);

        starPositions.put("12,2", Color.YELLOW);
        starPositions.put("12,4", Color.YELLOW);
        starPositions.put("12,6", Color.YELLOW);
        starPositions.put("12,8", Color.GRAY);
        starPositions.put("12,10", Color.GRAY);
        starPositions.put("12,12", Color.GRAY);
        starPositions.put("12,14", Color.GRAY);
        starPositions.put("12,16", Color.GRAY);
        starPositions.put("12,18", Color.GRAY);
        starPositions.put("12,20", Color.RED);
        starPositions.put("12,22", Color.RED);
        starPositions.put("12,24", Color.RED);

        starPositions.put("13,1", Color.YELLOW);
        starPositions.put("13,3", Color.YELLOW);
        starPositions.put("13,5", Color.YELLOW);
        starPositions.put("13,7", Color.YELLOW);
        starPositions.put("13,9", Color.GRAY);
        starPositions.put("13,11", Color.GRAY);
        starPositions.put("13,13", Color.GRAY);
        starPositions.put("13,15", Color.GRAY);
        starPositions.put("13,17", Color.GRAY);
        starPositions.put("13,19", Color.RED);
        starPositions.put("13,21", Color.RED);
        starPositions.put("13,23", Color.RED);
        starPositions.put("13,25", Color.RED);

        starPositions.put("14,10", Color.BLACK);
        starPositions.put("14,12", Color.BLACK);
        starPositions.put("14,14", Color.BLACK);
        starPositions.put("14,16", Color.BLACK);

        starPositions.put("15,11", Color.BLACK);
        starPositions.put("15,13", Color.BLACK);
        starPositions.put("15,15", Color.BLACK);

        starPositions.put("16,12", Color.BLACK);
        starPositions.put("16,14", Color.BLACK);

        starPositions.put("17,13", Color.BLACK);
    }

    public void putGraphicNode(int row, int col, int id, double radius, Color color) 
    {
        String positionKey = row + "," + col;

        // Only add the node if it's a star position
        if (!starPositions.containsKey(positionKey)) 
        {
            return;
        }

        Color colorOut = starPositions.get(positionKey);  // Get the color for the position
        GraphicNode node = new GraphicNode(id,0, 0, radius, Color.GRAY, colorOut);
        add(node, col, row); // Place the node at (col, row)
    }

    public void createGrid(int rows, int cols, double radius) 
    {
        int id = 0;
        double spacing = 2.2 * radius; // Adjust spacing as needed
        
        for (int row = 0; row < rows; row++) 
        {
            for (int col = 0; col < cols; col++) 
            {
                putGraphicNode(row, col, id++, radius, Color.LIGHTBLUE);
            }
        }
    }
}
