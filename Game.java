import java.io.Serializable;
import java.util.List;

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

    public void move(Move move) throws IllegalArgumentException
    {
        Node start = board.findNodeById(move.startId);
        Node end = board.findNodeById(move.endId);

        end.place(start.getPiece());
        start.take();
    }

    public IBoard getBoard()
    {
        return this.board;
    }
}