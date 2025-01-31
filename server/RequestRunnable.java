package server;

/**
 * Used by request handler map to run requests with data
 */
public interface RequestRunnable
{
    public void run(Object data);
}