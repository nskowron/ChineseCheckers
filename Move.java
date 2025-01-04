import java.io.Serializable;

public class Move implements Serializable
{
    public final String startId;
    public final String endId;

    public Move(String beginId, String endId)
    {
        this.startId = beginId;
        this.endId = endId;
    }
}
