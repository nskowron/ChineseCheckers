import java.io.EOFException;
import java.io.IOException;

public interface RequestRunnable
{
    public void run(Request request) throws EOFException, IOException, ClassNotFoundException;
}