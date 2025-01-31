package server.game.basic;

import java.util.ArrayList;
import java.util.List;

import server.game.*;
import shared.Move;
import utils.IntList;

/**
 * MoveChecker class for the basic game variant (but also for OOC)
 */
public class MoveChecker implements IMoveChecker
{
    private IBoard board;

    private List<Node> visitedNodes;

    /**
     * Requires a specific board
     */
    public MoveChecker(IBoard board)
    {
        this.board = board;

        this.visitedNodes = null;
    }

    /**
     * Pretty self-explanatory
     */
    public boolean validMove(Piece piece, Move move)
    {
        Node startNode = board.findNodeById(move.startId);
        Node endNode = board.findNodeById(move.endId);

        if(piece == null || startNode.getPiece() != null || endNode.getPiece() != null)
        {
            return false;
        }

        if(startNode.getColorTarget().equals(piece.getColor()) && !endNode.getColorTarget().equals(piece.getColor()))
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
    public Boolean winningMove(Piece piece, Move move)
    {
        String color = piece.getColor();
        Node startNode = board.findNodeById(move.startId);
        Node endNode = board.findNodeById(move.endId);

        if(piece == null || endNode.getPiece() != null)
        {
            return Boolean.FALSE;
        }

        if(startNode.getColorTarget().equals(color) || !endNode.getColorTarget().equals(color))
        {
            return Boolean.FALSE;
        }

        for(Node node : board.getNodes().values())
        {
            if(node.getColorTarget().equals(color))
            {
                if(node == endNode)
                {
                    continue;
                }

                if(node.getPiece() == null || !node.getPiece().getColor().equals(color))
                {
                    return Boolean.FALSE;
                }
            }
        }

        return Boolean.TRUE;
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

    /**
     * Returns a list of valid moves for a piece on a specific node
     * Assumes the piece is picked up (kind of as if a player was thinking about where to move it)
     */
    @Override
    public List<int[]> getValidMoves(Piece piece, int[] beginId)
    {
        List<int[]> endIds = new IntList();

        Node beginNode = board.findNodeById(beginId);
        for(Node neighbor : beginNode.getNeighbors())
        {
            if(neighbor == null)
            {
                continue;
            }

            if(validMove(piece, new Move(beginId, neighbor.getID())))
            {
                endIds.add(neighbor.getID());
            }
        }

        visitedNodes = new ArrayList<>();
        visitedNodes.add(beginNode);
        endIds.addAll(getValidMovesRecursive(piece, beginNode));

        return endIds;
    }

    private List<int[]> getValidMovesRecursive(Piece piece, Node startNode)
    {
        List<int[]> endIds = new ArrayList<>();
        
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

            if(validMove(piece, new Move(startNode.getID(), endNode.getID())))
            {
                if(!visitedNodes.contains(endNode))
                {
                    endIds.add(endNode.getID());
                    endIds.addAll(getValidMovesRecursive(piece, endNode));
                }
            }
        }

        return endIds;
    }
}