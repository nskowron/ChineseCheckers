import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IBoard extends Serializable
{
    public void move(Move move) throws IllegalAccessError;
    public Map<String, Node> getNodes();
    public Node findNodeById(String id);
    public void layPieces(List<Player> players) throws IllegalArgumentException;
}