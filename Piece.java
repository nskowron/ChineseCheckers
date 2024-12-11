public class Piece
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