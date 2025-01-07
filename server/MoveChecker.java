package server;

import java.util.ArrayList;
import java.util.List;

import shared.Move;

public class MoveChecker implements IMoveChecker
{
    private IBoard board;

    public MoveChecker(IBoard board)
    {
        this.board = board;
    }

    @Override
    public boolean validMove(Move move, MoveData prev)
    {
        return true;
    }

    @Override
    public boolean winningMove(Move move)
    {
        return false;
    }

    @Override
    public boolean jumpMove(Move move)
    {
        return false;
    }

    @Override
    public MoveData checkMove(Move move, MoveData prev)
    {
        MoveData data = new MoveData();
        data.valid = validMove(move, prev);
        data.jump = jumpMove(move);
        data.winning = winningMove(move);
        return data;
    }

    @Override
    public List<int[]> getValidMoves(int[] beginId, MoveData prev)
    {
        return new ArrayList<>();
    }
}