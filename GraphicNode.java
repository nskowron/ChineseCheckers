import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GraphicNode extends Circle 
{
    private int id;

    public GraphicNode(int id, double centerX, double centerY, double radius, Color colorIN, Color colorOUT) 
    {
        super(centerX, centerY, radius);
        this.id = id;
        setStrokeWidth(5);
        setFill(colorIN);
        setStroke(colorOUT);
    }
}
