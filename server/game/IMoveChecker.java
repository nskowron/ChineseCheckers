package server.game;

import java.util.List;

import shared.Move;

public interface IMoveChecker
{
    public Boolean winningMove(Piece piece, Move move);
    public List<int[]> getValidMoves(Piece piece, int[] beginId);
}