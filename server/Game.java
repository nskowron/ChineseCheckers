package server;

import shared.GameState;
import shared.Move;
import shared.Player;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Game implements Serializable
{
    private IValidityChecker checker;
    private IBoard board;

    List<Player> players;
    List<Player> winners;

    private int currentTurn;

    public Game(IValidityChecker checker, IBoard board, List<Player> gamePlayers) throws IllegalArgumentException
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
        if(player.getId() != players.get(currentTurn).getId())
        {
            throw new IllegalAccessError("It's not the player's turn");
        }
        if(!checker.validMove(move))
        {
            throw new IllegalAccessError("Invalid move");
        }
        else
        {
            board.move(move);
        }
        return false; // TODO: add winning mechanics
    }

    //make recursive for jumps
    public List<int[]> getValidMoves(Player player, int[] beginId)
    {
        List<int[]> endIds = new ArrayList<>();

        // TODO: add checking for turn?

        Node beginNode = board.findNodeById(beginId);
        if(beginNode == null)
        {
            return endIds;
        }

        for(int i = 0; i < 6; ++i)
        {
            Node neighbor = beginNode.getNeighbors().get(i); // consider Node.getNeighbor(int)
            while(neighbor != null)
            {
                if(checker.validMove(new Move(beginId, neighbor.id)))
                {
                    endIds.add(neighbor.id);
                    break;
                }
                neighbor = neighbor.getNeighbors().get(i);
            }
        }

        return endIds;
    }

    public void endTurn(Player player) throws IllegalAccessError
    {
        if(player.getId() == players.get(currentTurn).getId())
        {
            for(int i = 1; i < players.size(); ++i)
            {
                currentTurn = (currentTurn + 1) % players.size();
                if(players.get(currentTurn).didWin() == false)
                {
                    return;
                }
            }
            throw new IllegalAccessError("There are no more players");
        }
        throw new IllegalAccessError("It's not the player's turn");
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