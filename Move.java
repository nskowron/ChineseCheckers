import java.io.Serializable;

public class Move implements Serializable
{
    public final Player player;
    public final String startId;
    public final String endId;

    public Move(Player player, String beginId, String endId)
    {
        this.player = player;
        this.startId = beginId;
        this.endId = endId;
    }
}
