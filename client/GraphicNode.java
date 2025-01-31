package client;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * A graphical representation of a node on the board
 */
public class GraphicNode extends Circle 
{
    private int[] id = new int[2]; // row, column
    private boolean isHighlighted = false;
    private final Color originalIN;
    private final Color originalOUT;

    public GraphicNode(int[] id, double centerX, double centerY, double radius, Color colorIN, Color colorOUT) 
    {
        super(centerX, centerY, radius);

        if (id == null || id.length != 2) 
        {
            throw new IllegalArgumentException("id must be an array of exactly 2 integers.");
        }

        this.id = id;

        setStrokeWidth(3);

        setFill(colorIN);
        setStroke(colorOUT);
        this.originalIN = colorIN;
        this.originalOUT = colorOUT;
    }

    public void highlight() 
    {
        isHighlighted = true;

        double red = originalOUT.getRed();
        double green = originalOUT.getGreen();
        double blue = originalOUT.getBlue();

        red = Math.min(1.0, red + 30.0 / 255.0);
        green = Math.min(1.0, green + 30.0 / 255.0);
        blue = Math.min(1.0, blue + 30.0 / 255.0);

        setStroke(new Color(red, green, blue, originalOUT.getOpacity()));

        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(new Color(red, green, blue, originalOUT.getOpacity()-0.2));
        dropShadow.setRadius(70);
        dropShadow.setOffsetX(2);
        dropShadow.setOffsetY(2);
        this.setEffect(dropShadow);
    }

    public void removeHighlight() 
    {
        isHighlighted = false;
        setStroke(originalOUT);
        this.setEffect(null);
    }

    public boolean isHighlighted() 
    {
        return isHighlighted;
    }

    public int[] getGameId() 
    {
        return id;
    }
}
