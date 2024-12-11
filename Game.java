import java.io.Serializable;

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

    public move(int beginID, int endID, int playerID) throws IllegalArgumentException
    {
        if(checker.validMove(beginID, endID, playerID))
        {
            board.move(beginID, endID);
        }
        else
        {
            throw new IllegalArgumentException("Invalid Move");
        }
    }
}