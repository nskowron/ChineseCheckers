import java.io.Serializable;

public class Piece implements Serializable
{
    private final int ownerID;
    private Color color;

    public Piece(Color color)
    {
        this.color = color;
        this.ownerID = -1;
    }

    @Deprecated
    public Piece(int ownerID)
    {
        this.ownerID = ownerID;
    }

    @Deprecated
    public int getOwner()
    {
        return ownerID;
    }

    public Color getColor()
    {
        return color;
    }
}