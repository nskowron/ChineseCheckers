import java.io.Serializable;
import java.util.List;

public class Game implements Serializable
{
    private IValidityChecker checker;
    private IBoard board;

    List<GamePlayer> players;

    private int currentTurn;

    public Game(IValidityChecker checker, IBoard board, List<GamePlayer> players) throws IllegalArgumentException
    {
        board.layPieces(players);

        this.board = board;
        this.checker = checker;
        this.players = players;

        this.currentTurn = 0;
    }

    @Deprecated
    public Game(IValidityChecker checker, IBoard board) throws IllegalArgumentException
    {
        this(checker, board, null);
    }

    public void move(Move move) throws IllegalAccessError
    {
        if(move.playerId != players.get(currentTurn).id)
        {
            throw new IllegalAccessError("It's not the player's turn");
        }
        if(!checker.validMove(move))
        {
            throw new IllegalAccessError("Invalid move");
        }
        else
        {
            board.move(move.startId, move.endId);
        }
    }

    public List<String> getValidMoves(int playerId, String beginId)
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
                if(checker.validMove(Move(playerId, beginId, neighbor.id)))
                {
                    endIds.add(neigbor.id);
                    break;
                }
                neighbor = neighbor.getNeighbors().get(i);
            }
        }

        return endIds;
    }

    public void endTurn(int playerId) throws IllegalAccessError
    {
        if(playerId == players.get(currentTurn).id)
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
}