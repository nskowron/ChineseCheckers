import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board implements Serializable
{
    private List<Node> nodes;

    public Board() 
    {
        this.nodes = new ArrayList<>();
    }

    public void addNode(Node node)
    {
        this.nodes.add(node);
    }
    
    public void addNode(List<Node> nodeList)
    {
        for(Node input : nodeList)
        {
            this.nodes.add(input);
        }
    }

    public boolean validMove(int startId, int endId) 
    {
        Node start = findNodeById(startId);
        Node end = findNodeById(endId);

        if (start == null || end == null) return false;

        // TODO add jump validation!!! (you can jump over 1 node if its taken)

        return start.neighbors.contains(end) && end.owner == 0;
    }

    public void makeMove(int startId, int endId, int playerId) 
    {
        if (validMove(startId, endId)) 
        {
            Node start = findNodeById(startId);
            Node end = findNodeById(endId);

            if (start != null && end != null) 
            {
                start.owner = 0;
                end.owner = playerId;
            }
        }
    }

    private Node findNodeById(int id) 
    {
        for (Node node : nodes) 
        {
            if (node.id == id) return node;
        }
        return null;
    }

    public List<Node> getNodes() 
    {
        return nodes;
    }
}
