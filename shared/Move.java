package shared;

import java.io.Serializable;

/**
 * Structure for the move
 */
public class Move implements Serializable
{
    public final int[] startId;
    public final int[] endId;
    
    public Move(int[] beginId, int[] endId)
    {
        this.startId = beginId;
        this.endId = endId;
    }
}
