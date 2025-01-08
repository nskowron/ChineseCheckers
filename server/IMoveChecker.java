package server;

import java.util.List;

import shared.Move;
import shared.Player;

public interface IMoveChecker
{
    public boolean winningMove(Move move);
    public List<int[]> getValidMoves(Player player, int[] beginId);
}