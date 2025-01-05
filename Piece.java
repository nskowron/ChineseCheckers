import java.io.Serializable;

public class Piece implements Serializable
{
    private final Player owner;
    private final Color color;

    public Piece(Color color, Player owner) throws IllegalArgumentException
    {
        if(owner.getColor() != color)
        {
            throw new IllegalArgumentException("Player is of different color");
        }

        this.color = color;
        this.owner = owner;
    }

    public Player getOwner()
    {
        return owner;
    }

    public Color getColor()
    {
        return color;
    }
}