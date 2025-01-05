import java.util.Map;
import java.util.HashMap;

public class GameState
{
    public final Map<int[], Color> board;
    public final Player currentTurn;
    public final boolean won;

    public GameState(Map<int[], Node> nodes, Player currentTurn, boolean won)
    {
        this.currentTurn = currentTurn;
        this.won = won;

        Map<int[], Color> boardColors = new HashMap<>();
        for(Map.Entry<int[], Node> node : nodes.entrySet())
        {
            Piece piece = node.getValue().getPiece();
            if(piece == null)
            {
                boardColors.put(node.getKey(), Color.DEFAULT);
            }
            else
            {
                boardColors.put(node.getKey(), piece.getColor());
            }
        }
        this.board = boardColors;
    }
}