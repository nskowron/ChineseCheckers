package server;

import java.util.List;

import shared.Move;

public interface IMoveChecker
{
    public boolean winningMove(Piece piece, Move move);
    public List<int[]> getValidMoves(Piece piece, int[] beginId);
}