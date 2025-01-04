import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GraphicNode extends Circle 
{
    private String id;
    private boolean isHighlighted = false;
    private final Color originalIN;
    private final Color originalOUT;

    public GraphicNode(String id, double centerX, double centerY, double radius, Color colorIN, Color colorOUT) 
    {
        super(centerX, centerY, radius);
        this.id = id;

        setStrokeWidth(5);

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
    }

    public void removeHighlight() 
    {
        isHighlighted = false;
        setStroke(originalOUT);
    }

    public boolean isHighlighted() 
    {
        return isHighlighted;
    }

    public String getGameId() 
    {
        return id;
    }
}
