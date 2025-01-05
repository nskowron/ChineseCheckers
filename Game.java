import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Game implements Serializable
{
    private IValidityChecker checker;
    private IBoard board;

    List<GamePlayer> players;

    private int currentTurn;

    public Game(IValidityChecker checker, IBoard board, List<GamePlayer> gamePlayers) throws IllegalArgumentException
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

        this.currentTurn = 0;
    }

    @Deprecated
    public Game(IValidityChecker checker, IBoard board) throws IllegalArgumentException
    {
        this(checker, board, null);
    }

    public void move(Player player, Move move) throws IllegalAccessError
    {
        if(player.id != players.get(currentTurn).id)
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
    }

    public List<String> getValidMoves(Player player, String beginId)
    {
        List<String> endIds = new ArrayList<>();

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
        if(player.id == players.get(currentTurn).id)
        {
            for(int i = 1; i < players.size(); ++i)
            {
                currentTurn = (currentTurn + 1) % players.size();
                if(players.get(currentTurn).didWin() == false)
                {
                    return;
                }
            }
            throw new IllegalAccessError("The game is finished");
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