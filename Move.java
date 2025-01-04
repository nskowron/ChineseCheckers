import java.io.Serializable;

public class Move implements Serializable
{
    public final int playerId;
    public final String startId;
    public final String endId;

    public Move(int Player, String beginId, String endId)
    {
        this.playerId = player;
        this.startId = beginId;
        this.endId = endId;
    }
}
