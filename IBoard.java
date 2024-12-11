import java.io.Serializable;
import java.util.List;

public interface IBoard extends Serializable
{
    public void move(int startId, int endId) throws IllegalAccessError;
    public List<Node> getNodes();
    public Node findNodeById(int id) throws IllegalAccessError;
}