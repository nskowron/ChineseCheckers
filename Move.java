import java.io.Serializable;

public class Move implements Serializable
{
    public final String startId;
    public final String endId;
    public final int playerId;

    public Move(String begin, String end, int player)
    {
        this.startId = begin;
        this.endId = end;
        this.playerId = player;
    }
}
