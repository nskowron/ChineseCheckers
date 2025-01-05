import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class Board implements IBoard
{
    private Map<String, Node> nodes;

    public Board() 
    {
        this.nodes = new HashMap<>();

        //TODO: Implement node linking(later)
    }

    @Override
    public void move(Move move) throws IllegalAccessError
    {
        Node start = findNodeById(move.startId);
        Node end = findNodeById(move.endId);

        end.place(start.getPiece());
        start.take();
    }

    @Override
    public Node findNodeById(String id)
    {
        return nodes.get(id);
    }

    @Override
    public Map<String, Node> getNodes() 
    {
        return nodes;
    }

    @Override
    public void layPieces(List<Player> players) throws IllegalArgumentException
    {
        // TODO: Implement laying and assigning pieces
        // check number of players
    }
}
