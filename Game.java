import java.io.Serializable;

public class Game implements Serializable
{
    private IValidityChecker checker;
    private IBoard board;

    List<Integer> players;

    public Game(IValidityChecker checker, IBoard board)
    {
        this.checker = checker;
        this.board = board;
    }

    public move(int beginID, int endID, int playerID) throws IllegalArgumentException
    {

    }
}