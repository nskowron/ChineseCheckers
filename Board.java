import java.util.ArrayList;
import java.util.List;

public class Board implements IBoard
{
    private List<Node> nodes;

    public Board() 
    {
        this.nodes = new ArrayList<>();

        //TODO: Implement node linking - but not now Janek
        for(int i = 0; i < 100; ++i)
        {
            nodes.add(new Node(i));
        }
    }

    public void move(int startId, int endId) throws IllegalAccessError
    {
        Node start = findNodeById(startId);
        Node end = findNodeById(endId);

        // Temporary
        for(int i = 0; i < 100; ++i)
        {
            nodes.add(new Node(i));
        }
    }

    public Node findNodeById(int id) throws IllegalAccessError
    {
        for(Node node : nodes) 
        {
            if(node.getID() == id) return node;
        }
        
        throw new IllegalAccessError("Can't find node");
    }

    public List<Node> getNodes() 
    {
        return nodes;
    }
}
