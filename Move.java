import java.io.Serializable;

public class Move implements Serializable
{
    public final int startId;
    public final int endId;
    public final int playerId;

    public Move(int begin, int end, int player)
    {
        this.startId = begin;
        this.endId = end;
        this.playerId = player;
    }
}
