package server.game;

import shared.Move;
import shared.Player;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Interface for the game board
 */
public interface IBoard extends Serializable
{
    public void move(Move move) throws IllegalAccessError;
    public Map<int[], Node> getNodes();
    public Node findNodeById(int[] id);
    public void layPieces(List<Player> players) throws IllegalArgumentException;
}