import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;

public class BoardGridPane extends GridPane {

    private Map<String, String> starPositions = new HashMap<>(); // Set to store star positions

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
        starPositions.put("1,13", "1.1");

        starPositions.put("2,12", "2.1");
        starPositions.put("2,14", "2.2");

        starPositions.put("3,11", "3.1");
        starPositions.put("3,13", "3.2");
        starPositions.put("3,15", "3.3");

        starPositions.put("4,10", "4.1");
        starPositions.put("4,12", "4.2");
        starPositions.put("4,14", "4.3");
        starPositions.put("4,16", "4.4");

        starPositions.put("5,1", "5.1");
        starPositions.put("5,3", "5.2");
        starPositions.put("5,5", "5.3");
        starPositions.put("5,7", "5.4");
        starPositions.put("5,9", "5.5");
        starPositions.put("5,11", "5.6");
        starPositions.put("5,13", "5.7");
        starPositions.put("5,15", "5.8");
        starPositions.put("5,17", "5.9");
        starPositions.put("5,19", "5.10");
        starPositions.put("5,21", "5.11");
        starPositions.put("5,23", "5.12");
        starPositions.put("5,25", "5.13");

        starPositions.put("6,2", "6.1");
        starPositions.put("6,4", "6.2");
        starPositions.put("6,6", "6.3");
        starPositions.put("6,8", "6.4");
        starPositions.put("6,10", "6.5");
        starPositions.put("6,12", "6.6");
        starPositions.put("6,14", "6.7");
        starPositions.put("6,16", "6.8");
        starPositions.put("6,18", "6.9");
        starPositions.put("6,20", "6.10");
        starPositions.put("6,22", "6.11");
        starPositions.put("6,24", "6.12");

        starPositions.put("7,3", "7.1");
        starPositions.put("7,5", "7.2");
        starPositions.put("7,7", "7.3");
        starPositions.put("7,9", "7.4");
        starPositions.put("7,11", "7.5");
        starPositions.put("7,13", "7.6");
        starPositions.put("7,15", "7.7");
        starPositions.put("7,17", "7.8");
        starPositions.put("7,19", "7.9");
        starPositions.put("7,21", "7.10");
        starPositions.put("7,23", "7.11");

        starPositions.put("8,4", "8.1");
        starPositions.put("8,6", "8.2");
        starPositions.put("8,8", "8.3");
        starPositions.put("8,10", "8.4");
        starPositions.put("8,12", "8.5");
        starPositions.put("8,14", "8.6");
        starPositions.put("8,16", "8.7");
        starPositions.put("8,18", "8.8");
        starPositions.put("8,20", "8.9");
        starPositions.put("8,22", "8.10");

        starPositions.put("9,5", "9.1");
        starPositions.put("9,7", "9.2");
        starPositions.put("9,9", "9.3");
        starPositions.put("9,11", "9.4");
        starPositions.put("9,13", "9.5");
        starPositions.put("9,15", "9.6");
        starPositions.put("9,17", "9.7");
        starPositions.put("9,19", "9.8");
        starPositions.put("9,21", "9.9");

        starPositions.put("10,4", "10.1");
        starPositions.put("10,6", "10.2");
        starPositions.put("10,8", "10.3");
        starPositions.put("10,10", "10.4");
        starPositions.put("10,12", "10.5");
        starPositions.put("10,14", "10.6");
        starPositions.put("10,16", "10.7");
        starPositions.put("10,18", "10.8");
        starPositions.put("10,20", "10.9");
        starPositions.put("10,22", "10.10");

        starPositions.put("11,3", "11.1");
        starPositions.put("11,5", "11.2");
        starPositions.put("11,7", "11.3");
        starPositions.put("11,9", "11.4");
        starPositions.put("11,11", "11.5");
        starPositions.put("11,13", "11.6");
        starPositions.put("11,15", "11.7");
        starPositions.put("11,17", "11.8");
        starPositions.put("11,19", "11.9");
        starPositions.put("11,21", "11.10");
        starPositions.put("11,23", "11.11");

        starPositions.put("12,2", "12.1");
        starPositions.put("12,4", "12.2");
        starPositions.put("12,6", "12.3");
        starPositions.put("12,8", "12.4");
        starPositions.put("12,10", "12.5");
        starPositions.put("12,12", "12.6");
        starPositions.put("12,14", "12.7");
        starPositions.put("12,16", "12.8");
        starPositions.put("12,18", "12.9");
        starPositions.put("12,20", "12.10");
        starPositions.put("12,22", "12.11");
        starPositions.put("12,24", "12.12");

        starPositions.put("13,1", "13.1");
        starPositions.put("13,3", "13.2");
        starPositions.put("13,5", "13.3");
        starPositions.put("13,7", "13.4");
        starPositions.put("13,9", "13.5");
        starPositions.put("13,11", "13.6");
        starPositions.put("13,13", "13.7");
        starPositions.put("13,15", "13.8");
        starPositions.put("13,17", "13.9");
        starPositions.put("13,19", "13.10");
        starPositions.put("13,21", "13.11");
        starPositions.put("13,23", "13.12");
        starPositions.put("13,25", "13.13");

        starPositions.put("14,10", "14.1");
        starPositions.put("14,12", "14.2");
        starPositions.put("14,14", "14.3");
        starPositions.put("14,16", "14.4");

        starPositions.put("15,11", "15.1");
        starPositions.put("15,13", "15.2");
        starPositions.put("15,15", "15.3");

        starPositions.put("16,12", "16.1");
        starPositions.put("16,14", "16.2");

        starPositions.put("17,13", "17.1");

    }

    public void putGraphicNode(int row, int col, double radius) 
    {
        String positionKey = row + "," + col;

        // Only add the node if it's a star position
        if (!starPositions.containsKey(positionKey)) 
        {
            return;
        }

        String id = starPositions.get(positionKey);  // Get the color for the position
        GraphicNode node = new GraphicNode(id, 0, 0, radius, Color.GRAY, Color.GRAY);
        add(node, col, row); // Place the node at (col, row)
    }

    public void createBoard(double radius) 
    {
        int rows = 19;
        int cols = 27;
        double spacing = 2.2 * radius; // Adjust spacing as needed
        
        for (int row = 0; row < rows; row++) 
        {
            for (int col = 0; col < cols; col++) 
            {
                putGraphicNode(row, col, radius);
            }
        }
    }
}
