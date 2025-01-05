package shared;

import java.util.Map;

import javafx.scene.paint.Color;
import server.Node;
import server.Piece;

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
                boardColors.put(node.getKey(), Color.rgb(50, 50, 50));
            }
            else
            {
                boardColors.put(node.getKey(), piece.getColor());
            }
        }
        this.board = boardColors;
    }
}