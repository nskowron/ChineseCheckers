import java.io.IOException;

public interface RequestRunnable
{
    public void run(Request request) throws IOException, ClassNotFoundException;
}