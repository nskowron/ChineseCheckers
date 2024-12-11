import java.io.Serializable;
import java.util.List;

public class Game implements Serializable
{
    private IValidityChecker checker;
    private IBoard board;

    List<Integer> players;

    public Game(IValidityChecker checker, IBoard board, List<Integer> players) throws IllegalArgumentException
    {
        this.checker = checker;
        this.board = board;

        //TODO: Add checking for right number of players etc
        if(players.size() == 0)
        {
            throw new IllegalArgumentException("Wrong players");
        }
        this.players = players;
    }

    public void move(Move move) throws IllegalArgumentException
    {
        if(checker.validMove(move))
        {
            board.move(move.startId, move.endId);
        }
        else
        {
            throw new IllegalArgumentException("Invalid move");
        }
    }

    public IBoard getBoard()
    {
        return this.board;
    }
}