import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node implements Serializable
{
    public final int[] id;
    public final Color colorStarting;
    public final Color colorTarget;

    private Piece placeholder;
    private List<Node> neighbors;

    public Node(int[] id, Color colorStarting, Color colorTarget) 
    {
        this.id = id;
        this.colorStarting = colorStarting;
        this.colorTarget = colorTarget;

        this.neighbors = new ArrayList<>();
        for(int i = 0; i < 6; ++i)
        {
            neighbors.add(null);
        }
    }

    public Node(int[] id)
    {
        this(id, Color.DEFAULT, Color.DEFAULT);
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

    public int[] getID()
    {
        return id;
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