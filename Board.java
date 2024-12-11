import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board implements IBoard
{
    private List<Node> nodes;

    public Board() 
    {
        this.nodes = new ArrayList<>();
        //TODO: Implement node linking
    }

    public void move(int startId, int endId) throws IllegalAccessError
    {
        Node start = findNodeById(startId);
        Node end = findNodeById(endId);

        end.place(start.getPiece());
        start.take();
    }

    private Node findNodeById(int id) throws IllegalAccessError
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
