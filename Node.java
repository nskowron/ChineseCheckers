import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node implements Serializable
{
    public final int id;
    public final int playerTarget;

    private Piece placeholder;
    private List<Node> neighbors;

    public Node(int id, int playerTarget, Piece piece) 
    {
        this.id = id;
        this.playerTarget = playerTarget;
        this.placeholder = piece;
        this.neighbors = new ArrayList<>(6);
    }

    public Node(int id, int playerTarget)
    {
        this(id, playerTarget, null);
    }

    public Node(int id) 
    {
        this(id, -1, null);
    }

    public void addNeighbor(int idx, Node neighbor) 
    {
        this.neighbors.add(idx, neighbor);
    }

    public void addNeighbors(ArrayList<Node> neighbors) 
    {
        if(neighbors.size() == 6)
        {
            this.neighbors = neighbors;
        }
    }

    public List<Node> getNeighbors()
    {
        return neighbors;
    }

    @Deprecated
    public int getID()
    {
        return id;
    }

    @Deprecated
    public int getPlayerTarget()
    {
        return playerTarget;
    }

    public Piece getPiece()
    {
        return placeholder;
    }

    public Piece take() throws IllegalAccessError
    {
        if(placeholder == null)
        {
            throw new IllegalAccessError("There's nothing to take");
        }
        Piece piece = placeholder;
        placeholder = null;
        return piece;
    }

    public void place(Piece piece) throws IllegalAccessError
    {
        if(placeholder != null)
        {
            throw new IllegalAccessError("Can't place two pieces");
        }
        placeholder = piece;
    }
}