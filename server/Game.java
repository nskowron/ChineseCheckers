package server;

import shared.GameState;
import shared.Move;
import shared.Player;
import utils.IntMap;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class Game implements Serializable
{
    private IMoveChecker checker;
    private IBoard board;

    List<Player> players;
    List<Player> winners;

    private int currentTurn;
    private Map<int[], List<int[]> > validMoves;

    public Game(IMoveChecker checker, IBoard board, List<Player> gamePlayers) throws IllegalArgumentException
    {
        List<Player> players = new ArrayList<>();
        for(Player player : gamePlayers)
        {
            players.add(player);
        }
        board.layPieces(players);

        this.board = board;
        this.checker = checker;
        this.players = gamePlayers;
        this.winners = new ArrayList<>();

        this.currentTurn = 0;
        this.validMoves = new IntMap<>();
    }

    public GameState getState()
    {
        return new GameState(
            board.getNodes(),
            players.get(currentTurn),
            winners.size() > 0 ? winners.get(0) : null
        );
    }

    public Boolean move(Player player, Move move) throws IllegalAccessError
    {
        if(player != players.get(currentTurn))
        {
            throw new IllegalAccessError("It's not the player's turn");
        }

        Piece piece = board.findNodeById(move.startId).getPiece();
        if(piece != null && piece.getOwner() != player)
        {
            throw new IllegalAccessError("Can't move someone else's piece");
        }

        if(getValidMoves(player, move.startId).contains(move.endId))
        {
            board.move(move);
            return checker.winningMove(move);
        }
        else
        {
            throw new IllegalAccessError("Invalid move");
        }
    }

    public List<int[]> getValidMoves(Player player, int[] beginId)
    {
        if(validMoves.get(beginId) != null)
        {
            System.out.println("already calculated");
            return validMoves.get(beginId);
        }

        List<int[]> validEndIds;
    
        Piece piece = board.findNodeById(beginId).getPiece();
        if(piece == null || piece.getOwner() != player)
        {
            System.out.println("Not the player's piece");
            validEndIds = new ArrayList<>();
        }
        else if(players.get(currentTurn) != player)
        {
            validEndIds = checker.getValidMoves(beginId);
        }
        else
        {
            validEndIds = checker.getValidMoves(beginId);
        }

        validMoves.put(beginId, validEndIds);
        return validEndIds;
    }

    public void endTurn(Player player) throws IllegalAccessError
    {
        if(player != players.get(currentTurn))
        {
            throw new IllegalAccessError("It's not the player's turn");
        }

        for(int i = 1; i < players.size(); ++i)
        {
            currentTurn = (currentTurn + 1) % players.size();
            if(winners.contains(player) == false)
            {
                validMoves = new IntMap<>();
                return;
            }
        }
        throw new IllegalAccessError("There are no more players");
        
    }

    public IBoard getBoard()
    {
        return this.board;
    }

    public Player getCurrentTurn()
    {
        return players.get(currentTurn);
    }
}