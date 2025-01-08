package server;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        Node startNode = board.findNodeById(move.startId);
        Node endNode = board.findNodeById(move.endId);

        if(startNode.getPiece() == null || endNode.getPiece() != null)
        {
            return false;
        }

        if(prev != null && !prev.jump)
        {
            return false;
        }

        if(jumpMove(move))
        {
            return true;
        }

        return startNode.getNeighbors().contains(endNode);
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
        Set<int[]> endIds = Set.of();

        Node beginNode = board.findNodeById(beginId);
        for(Node neighbor : beginNode.getNeighbors())
        {
            if(validMove(new Move(beginId, neighbor.getID()), prev))
            {
                endIds.add(neighbor.getID());
            }
        }

        endIds.addAll(getValidMovesRecursive(beginNode, prev, -1));

        return new ArrayList<>(endIds);
    }

    private Set<int[]> getValidMovesRecursive(Node startNode, MoveData prev, int skipDirection)
    {
        Set<int[]> endIds = Set.of();

        for(int i = 0; i < 6; ++i)
        {
            if(i == skipDirection)
            {
                continue;
            }

            Node inBetween = startNode.getNeighbors().get(i);
            if(inBetween == null)
            {
                continue;
            }

            Node endNode = inBetween.getNeighbors().get(i);
            if(endNode == null)
            {
                continue;
            }

            MoveData tryMove = checkMove(new Move(startNode.getID(), endNode.getID()), prev);
            if(tryMove.valid)
            {
                endIds.add(endNode.getID());
                endIds.addAll(getValidMovesRecursive(endNode, tryMove, i));
            }
        }

        return endIds;
    }
}