package server.game;

import java.util.List;

import shared.Move;

/**
 * Interface for the move checker
 */
public interface IMoveChecker
{
    public Boolean winningMove(Piece piece, Move move);
    public List<int[]> getValidMoves(Piece piece, int[] beginId);
}