package server;

import shared.GameState;
import shared.Move;
import shared.Player;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Game implements Serializable
{
    private IMoveChecker checker;
    private IBoard board;

    List<Player> players;
    List<Player> winners;

    private int currentTurn;
    private MoveData previousMove;

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
        this.previousMove = null;
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

        MoveData thisMove = checker.checkMove(move, previousMove);
        if(!thisMove.valid)
        {
            throw new IllegalAccessError("Invalid move");
        }
        else
        {
            board.move(move);
        }
        previousMove = thisMove;

        if(thisMove.winning)
        {
            winners.add(player);
        }

        return thisMove.winning;
    }

    public List<int[]> getValidMoves(Player player, int[] beginId)
    {
        Piece piece = board.findNodeById(beginId).getPiece();
        if(piece.getOwner() != player)
        {
            System.out.println("Not my turn?");
            return new ArrayList<>();
        }

        if(players.get(currentTurn) != player)
        {
            return checker.getValidMoves(beginId, null);
        }
        else
        {
            return checker.getValidMoves(beginId, previousMove);
        }
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
                previousMove = null;
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