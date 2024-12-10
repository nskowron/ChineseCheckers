import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node implements Serializable
{
    public int id;        
    public int owner;     
    public List<Node> neighbors;

    public Node(int id, int owner) 
    {
        this.id = id;
        this.owner = owner;
        this.neighbors = new ArrayList<>();
    }
    public Node(int id) 
    {
        this.id = id;
        this.owner = 0;
        this.neighbors = new ArrayList<>();
    }

    public void addNeighbor(Node neighbor) 
    {
        this.neighbors.add(neighbor);
    }

    public void addNeighbor(List<Node> neighbor) 
    {
        for(Node input : neighbor)
        {
            this.neighbors.add(input);
        }
    }
}