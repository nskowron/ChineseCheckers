import java.util.Map;
import java.util.HashMap;

public class GameState
{
    public final Map<String, Color> board;
    public final Player currentTurn;
    public final boolean won;

    public GameState(Map<String, Node> nodes, Player currentTurn, boolean won)
    {
        this.currentTurn = currentTurn;
        this.won = won;

        Map<String, Color> boardColors = new HashMap<>();
        for(Map.Entry<String, Node> node : nodes.entrySet())
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