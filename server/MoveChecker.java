package server;

import java.util.ArrayList;
import java.util.List;

import shared.Move;
import shared.Player;

public class MoveChecker implements IMoveChecker
{
    private IBoard board;

    private List<Node> visitedNodes;

    public MoveChecker(IBoard board)
    {
        this.board = board;

        this.visitedNodes = null;
    }

    private boolean validMove(Player player, Move move)
    {
        Node startNode = board.findNodeById(move.startId);
        Node endNode = board.findNodeById(move.endId);

        Piece piece = startNode.getPiece();
        if(piece != null && !piece.getColor().equals(player.getColor()))
        {
            return false;
        }

        if(endNode.getPiece() != null)
        {
            return false;
        }

        if(startNode.getColorTarget().equals(player.getColor()) && !endNode.getColorTarget().equals(player.getColor()))
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
        Piece piece = board.findNodeById(move.startId).getPiece();
        Node startNode = board.findNodeById(move.startId);
        Node endNode = board.findNodeById(move.endId);

        if(piece == null || endNode.getColorTarget() != piece.getColor())
        {
            return false;
        }

        for(Node node : board.getNodes().values())
        {
            if(node.getColorTarget().equals(piece.getColor()))
            {
                if(node == endNode)
                {
                    if(startNode.getColorTarget().equals(piece.getColor()))
                    {
                        return false;
                    }
                }
                if(node.getPiece() == null || !node.getPiece().getColor().equals(piece.getColor()))
                {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean jumpMove(Move move)
    {
        List<Node> neigbors1 = board.findNodeById(move.startId).getNeighbors();
        List<Node> neigbors2 = board.findNodeById(move.endId).getNeighbors();
        List<Node> commonNeighbors = new ArrayList<>();

        for(Node neighbor : neigbors1)
        {
            if(neighbor != null && neigbors2.contains(neighbor))
            {
                commonNeighbors.add(neighbor);
            }
        }

        return commonNeighbors.size() == 1 && commonNeighbors.get(0).getPiece() != null;
    }

    @Override
    public List<int[]> getValidMoves(Player player, int[] beginId)
    {
        List<int[]> endIds = new ArrayList<>();

        Node beginNode = board.findNodeById(beginId);
        for(Node neighbor : beginNode.getNeighbors())
        {
            if(neighbor == null)
            {
                continue;
            }

            if(validMove(player, new Move(beginId, neighbor.getID())))
            {
                endIds.add(neighbor.getID());
            }
        }

        visitedNodes = new ArrayList<>();
        endIds.addAll(getValidMovesRecursive(player, beginNode));

        return endIds;
    }

    private List<int[]> getValidMovesRecursive(Player player, Node startNode)
    {
        List<int[]> endIds = new ArrayList<>();

        if(visitedNodes.contains(startNode))
        {
            return endIds;
        }
        
        visitedNodes.add(startNode);

        for(int i = 0; i < 6; ++i)
        {
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

            if(validMove(player, new Move(startNode.getID(), endNode.getID())))
            {
                endIds.add(endNode.getID());
                endIds.addAll(getValidMovesRecursive(player, endNode));
            }
        }

        return endIds;
    }
}