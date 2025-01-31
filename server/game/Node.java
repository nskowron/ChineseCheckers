package server.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Node on the board
 * All nodes are connected to 6 (possibly null) neighbors and create a graph
 * Each node has an id which corresponds to its position on the board
 * ColorStarting is the color of the piece that starts on this node
 * ColorTarget is the color of the piece that should end on this node
 */
public class Node implements Serializable
{
    public final int[] id;
    public final String colorStarting;
    public final String colorTarget;

    private Piece placeholder;
    private List<Node> neighbors;

    public Node(int[] id, String colorStarting, String colorTarget) 
    {
        this.id = id;
        this.colorStarting = colorStarting;
        this.colorTarget = colorTarget;

        this.neighbors = new ArrayList<>();
    }

    public Node(int[] id)
    {
        this(id, "DEFAULT", "DEFAULT");
    }

    public void addNeighbor(int idx, Node neighbor) 
    {
        this.neighbors.add(idx, neighbor);
    }

    public void addNeighbors(List<Node> neighbors) 
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

    public String getColorStarting()
    {
        return colorStarting;
    }

    public String getColorTarget()
    {
        return colorTarget;
    }

    /**
     * Takes the piece from the node
     * If there's no piece, throws an error
     */
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

    /**
     * Places a piece on the node
     * If there's already a piece, throws an error
     */
    public void place(Piece piece) throws IllegalAccessError
    {
        if(placeholder != null)
        {
            throw new IllegalAccessError("Can't place two pieces");
        }
        placeholder = piece;
    }
}