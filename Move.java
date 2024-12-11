import java.io.Serializable;

public class Move implements Serializable
{
    public Move(int begin, int end, int player)
    {
        this.startId = begin;
        this.endId = end;
        this.playerId = player;
    }

    public int startId;
    public int endId;
    public int playerId;
}
