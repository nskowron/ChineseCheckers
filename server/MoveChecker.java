package server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shared.Move;

public class MoveChecker implements IMoveChecker
{
    private IBoard board;

    public MoveChecker(IBoard board)
    {
        this.board = board;
    }

    public boolean validMove(Move move)
    {
        Node startNode = board.findNodeById(move.startId);
        Node endNode = board.findNodeById(move.endId);

        if(startNode.getPiece() == null || endNode.getPiece() != null)
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
        return true;
    }

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

    // add not getting out of the target nodes
    @Override
    public List<int[]> getValidMoves(int[] beginId)
    {
        List<int[]> endIds = new ArrayList<>();

        Node beginNode = board.findNodeById(beginId);
        for(Node neighbor : beginNode.getNeighbors())
        {
            System.out.println(neighbor);
            if(neighbor == null)
            {
                continue;
            }
            System.out.println(neighbor.getID()[0] + ", " + neighbor.getID()[0]);

            if(validMove(new Move(beginId, neighbor.getID())))
            {
                endIds.add(neighbor.getID());
            }
        }

        endIds.addAll(getValidMovesRecursive(beginNode, -1));

        return endIds;
    }

    private List<int[]> getValidMovesRecursive(Node startNode, int skipDirection)
    {
        List<int[]> endIds = new ArrayList<>();

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

            if(validMove(new Move(startNode.getID(), endNode.getID())))
            {
                endIds.add(endNode.getID());
                endIds.addAll(getValidMovesRecursive(endNode, i));
            }
        }

        return endIds;
    }
}