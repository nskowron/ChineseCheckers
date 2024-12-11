import java.io.Serializable;

public interface IBoard extends Serializable
{
    public void move(int beginID, int endID);
}