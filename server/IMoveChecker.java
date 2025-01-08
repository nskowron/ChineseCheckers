package server;

import java.util.List;

import shared.Move;

public interface IMoveChecker
{
    public boolean validMove(Move move, MoveData prev);
    public boolean winningMove(Move move);
    public boolean jumpMove(Move move);
    
    public MoveData checkMove(Move move, MoveData prev);
    public List<int[]> getValidMoves(int[] beginId, MoveData prev);
}