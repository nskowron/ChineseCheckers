import java.io.Serializable;
import java.util.List;

public interface IBoard extends Serializable
{
    public List<Node> getNodes(); 
    public Node findNodeById(int id);
}