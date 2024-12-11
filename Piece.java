import java.io.Serializable;

public class Piece implements Serializable
{
    private final int ownerID;

    public Piece(int ownerID)
    {
        this.ownerID = ownerID;
    }

    public int getOwner()
    {
        return ownerID;
    }
}