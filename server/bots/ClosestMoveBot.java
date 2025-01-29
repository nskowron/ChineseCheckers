package server.bots;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import server.RequestRunnable;
import server.game.IBoard;
import server.game.Node;
import shared.GameState;
import shared.Move;
import shared.Player;
import shared.Request;

public class ClosestMoveBot implements Runnable
{
    private final Socket socket;
    private Player player;
    private int[] target;

    private Map<String, RequestRunnable> requestHandler;
    ObjectOutputStream out;
    ObjectInputStream in;

    private final Logger LOGGER;

    private boolean running;

    public ClosestMoveBot(IBoard board) throws IOException
    {
        LOGGER = Logger.getLogger("Bot");
        socket = new Socket("localhost", 12345);
        target = null;
        requestHandler = getDefaultRequestHandler(board);
    }

    @Override
    public void run()
    {
        running = true;
        try
        {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }
        catch(IOException e) 
        {
            disconnect(false);
        }

        while(running)
        {
            Request request = receive();
            if(request == null)
            {
                continue;
            }
            RequestRunnable action = requestHandler.get(request.getType());
            if(action != null)
            {
                action.run(request.getData());
            }
            else
            {
                send(new Request("ERROR", new Error("Non-handled request")));
            }
        }

        LOGGER.info("Bot died");
    }

    public void disconnect(boolean ok)
    {
        running = false;

        if(ok)
        {
            LOGGER.info("Bot disconnected");
        }
        else
        {
            LOGGER.severe("Connection error!");
        }
        
        try
        {
            if(in != null && out != null)
            {
                in.close();
                out.close();
            }
            socket.close();
        }
        catch( IOException e )
        {
            LOGGER.severe("Couldn't close streams");
        }
    }

    private void send(Request request)
    {
        LOGGER.info("bot sending " + request.getType());
        try
        {
            out.writeObject(request);
        }
        catch(EOFException e)
        {
            disconnect(true);
        }
        catch(IOException e)
        {
            disconnect(false);
        }
    }

    private Request receive()
    {
        try
        {
            synchronized(in)
            {
                Request request = (Request)in.readObject();
                LOGGER.info("bot receiving " + request.getType());
                return request;
            }
        }
        catch(EOFException e)
        {
            disconnect(true);
            return null;
        }
        catch(IOException | ClassNotFoundException e)
        {
            disconnect(false);
            return null;
        }
    }

    private Map<String, RequestRunnable> getDefaultRequestHandler(IBoard board)
    {
        Map<String, RequestRunnable> requestHandler = new HashMap<>();

        requestHandler.put("GREET", (Object greet) -> {
            if(greet instanceof Player)
            {
                player = (Player)greet;
                send(new Request("READY", Boolean.TRUE));
            }
            else
            {
                send(new Request("ERROR", new Error("Unknown type")));
            }
        });

        requestHandler.put("WAITING", (Object waiting) -> {});

        requestHandler.put("GAME_START", (Object start) -> {
            if(start instanceof String)
            {
                player.setColor((String)start);
                target = calculateTarget(board);
            }
            else
            {
                send(new Request("ERROR", new Error("Unknown type")));
            }
        });

        requestHandler.put("UPDATE", (Object update) -> {
            if(update instanceof GameState)
            {
                GameState state = (GameState)update;
                if(state.won != null)
                {
                    disconnect(true);
                    return;
                }
                if(state.currentTurn.getId() == player.getId())
                {
                    List<Map.Entry<int[], String> > nodes = new ArrayList<>(state.board.entrySet());
                    Collections.shuffle(nodes);

                    for(Map.Entry<int[], String> node : nodes)
                    {
                        if(node.getValue().equals(player.getColor()))
                        {
                            send(new Request("GET_MOVES", node.getKey()));
                            Request get_moves = receive();
                            if(get_moves.getType().equals("GET_MOVES") && get_moves.getData() instanceof List)
                            {
                                List<?> moves = (List<?>)get_moves.getData();
                                if(!moves.isEmpty() && moves.get(0) instanceof int[])
                                {
                                    send(new Request("MOVE", new Move(node.getKey(), getClosestToTarget((List<int[]>)moves))));
                                    return;
                                }
                            }
                            else
                            {
                                send(new Request("ERROR", new Error("Unknown type")));
                            }
                        }
                    }
                    send(new Request("END_TURN"));
                }
            }
            else
            {
                send(new Request("ERROR", new Error("Unknown type")));
            }
        });

        requestHandler.put("WON", (Object win) -> {
            disconnect(true);
        });

        requestHandler.put("ACKNOWLEDGE", (Object ack) -> {
            LOGGER.info("Acknowledged");
        });

        requestHandler.put("ERROR", (Object error) -> {
            if(error instanceof Error)
            {
                LOGGER.severe(((Error)error).getMessage());
            }
            disconnect(false);
        });

        return requestHandler;
    }

    private int[] calculateTarget(IBoard board)
    {
        for(Node node : board.getNodes().values())
        {
            if(node.getColorTarget().equals(player.getColor()))
            {
                int nulls = 0;
                for(Node neigbor : node.getNeighbors())
                {
                    if(neigbor == null)
                    {
                        ++nulls;
                    }
                }
                if(nulls == 4)
                {
                    return node.getID();
                }
                LOGGER.info("NULLS: " + nulls);
            }
        }

        LOGGER.severe("Problem calculating target node");
        return null;
    }

    private int[] getClosestToTarget(List<int[]> nodes)
    {
        int[] closestNode = null;
        double minDistance = Double.MAX_VALUE;

        for(int[] node : nodes)
        {
            int dx = node[0] - target[0];
            int dy = node[1] - target[1];
            double distance = Math.sqrt(dx * dx + dy * dy);

            if(distance < minDistance)
            {
                minDistance = distance;
                closestNode = node;
            }
        }

        return closestNode;
    }
}
