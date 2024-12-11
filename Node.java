import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node implements Serializable
{
    private final int id;

    private final int playerTarget;

    private Piece placeholder;
    private List<Node> neighbors;

    public Node(int id, int playerTarget, Piece piece) 
    {
        this.id = id;
        this.playerTarget = playerTarget;
        this.placeholder = piece;
        this.neighbors = new ArrayList<>();
    }

    public Node(int id) 
    {
        this(id, -1, null);
    }

    public void addNeighbor(Node neighbor) 
    {
        this.neighbors.add(neighbor);
    }

    public void addNeighbors(List<Node> neighbors) 
    {
        for(Node input : neighbors)
        {
            this.neighbors.add(input);
        }
    }

    public List<Node> getNeighbors()
    {
        return neighbors;
    }

    public int getID()
    {
        return id;
    }

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