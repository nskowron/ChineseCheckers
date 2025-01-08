package server;

import java.util.List;

import shared.Move;

public interface IMoveChecker
{
    public boolean winningMove(Move move);
    public List<int[]> getValidMoves(int[] beginId);
}