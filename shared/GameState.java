package shared;

import java.util.Map;

import server.game.Node;
import server.game.Piece;
import utils.IntMap;

import java.io.Serializable;

public class GameState implements Serializable
{
    public final Map<int[], String> board;
    public final Player currentTurn;
    public final Player won;

    public GameState(Map<int[], Node> nodes, Player currentTurn, Player won)
    {
        this.currentTurn = currentTurn;
        this.won = won;

        Map<int[], String> boardColors = new IntMap<>();
        for(Map.Entry<int[], Node> node : nodes.entrySet())
        {
            Piece piece = node.getValue().getPiece();
            if(piece == null)
            {
                boardColors.put(node.getKey(), "DEFAULT");
            }
            else
            {
                boardColors.put(node.getKey(), piece.getColor());
            }
        }
        this.board = boardColors;
    }
}