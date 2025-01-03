import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GraphicNode extends Circle 
{
    private int id;

    public GraphicNode(int id, double centerX, double centerY, double radius, Color color) 
    {
        super(centerX, centerY, radius);
        this.id = id;
        setFill(color);
        setStroke(Color.BLACK);
    }

    public int getId() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }
}
