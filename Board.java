import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Board implements IBoard
{
    private Map<Integer, Node> nodes;

    public Board() 
    {
        this.nodes = new ArrayList<>();

        //TODO: Implement node linking(later)
        for(int i = 0; i < 100; ++i)
        {
            nodes.add(new Node(i));
        }
        for(int i = 0; i < 10; ++i)
        {
            nodes.get(i).place(new Piece(0));
        }
    }

    public void move(int startId, int endId) throws IllegalAccessError
    {
        Node start = findNodeById(startId);
        Node end = findNodeById(endId);

        end.place(start.getPiece());
        start.take();
    }

    public Node findNodeById(int id)
    {
        return nodes.get(id);
    }

    public List<Node> getNodes() 
    {
        return nodes;
    }
}
