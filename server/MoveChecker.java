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
        List<Node> neigbors1 = board.findNodeById(move.startId).getNeighbors();
        List<Node> neigbors2 = board.findNodeById(move.startId).getNeighbors();
        List<Node> commonNeighbors = new ArrayList<>();

        for(Node neighbor : neigbors1)
        {
            if(neigbors2.contains(neighbor))
            {
                commonNeighbors.add(neighbor);
            }
        }

        return commonNeighbors.size() == 1 && commonNeighbors.get(0).getPiece() != null;
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