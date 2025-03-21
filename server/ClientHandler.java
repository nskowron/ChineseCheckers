package server;

import shared.*;
import utils.Condition;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;

import server.game.Game;

/**
 * Client handler class
 * Handles client requests and responses for each socket connection
 * Designed to run in a separate thread
 */
public class ClientHandler implements Runnable 
{
    private final int id;
    private final Socket clientSocket;
    private final Player player;
    private Condition gameStarted;

    private Map<String, RequestRunnable> requestHandler;
    ObjectOutputStream out;
    ObjectInputStream in;

    private final Logger LOGGER;

    private boolean running;

    /**
     * Takes the client id, socket, game player object and game started condition which it listens to
     */
    public ClientHandler(int id, Socket clientSocket, Player player, Condition gameStarted) 
    {
        this.id = id;
        this.clientSocket = clientSocket;
        this.player = player;
        this.gameStarted = gameStarted;

        this.requestHandler = null;
        this.out = null;
        this.in = null;

        this.running = false;

        LOGGER = Logger.getLogger("ServerPlayer " + player.getId());
        LOGGER.info("Client handler created");
    }
    
    /**
     * Main client handler loop
     * Handles client requests and responses based on the request type and map of request handlers
     */
    @Override
    public void run() 
    {
        running = true;
        try
        {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
        }
        catch(IOException e) 
        {
            disconnect(false);
        }

        // Greet the client
        requestHandler = getDefaultRequestHandler();
        requestHandler.get("GREET").run(null);
        requestHandler.get("READY").run(Boolean.FALSE);

        // Wait for the game to start and listen to ready requests
        Thread readiness = new Thread(() -> {
            while(!gameStarted.met && running)
            {
                Request request = receive();
                if(request == null)
                {
                    continue;
                }
                if(request.getType().equals("READY"))
                {
                    requestHandler.get("READY").run(request.getData());
                }
                else if(gameStarted.met) // ugly but works
                {
                    RequestRunnable action = requestHandler.get(request.getType());
                    if(action != null)
                    {
                        synchronized(this)
                        {
                            action.run(request.getData());
                        }
                    }
                    else
                    {
                        send(new Request("ERROR", new Error("Non-handled request")));
                    }

                    break;
                }
                else
                {
                    send(new Request("ERROR", new Error("Game has not started yet")));
                }
            }
        });

        readiness.start();
        synchronized(gameStarted){} // wait for game to start
        readiness.interrupt();

        requestHandler.get("GAME_START").run(null);
        
        // Main game loop - handle all requests
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
                synchronized(this)
                {
                    action.run(request.getData());
                }
            }
            else
            {
                send(new Request("ERROR", new Error("Non-handled request")));
            }
        }

        LOGGER.info("ClientHandler died");
    }

    // Disconnect the client and remove from server list
    public void disconnect(boolean ok)
    {
        if(!running)
        {
            return;
        }

        running = false;

        if(ok)
        {
            LOGGER.info("Client disconnected");
        }
        else
        {
            LOGGER.severe("Connection error!");
        }
        
        CheckersServer.removeClient(id);
        try
        {
            if(in != null && out != null)
            {
                in.close();
                out.close();
            }
            clientSocket.close();
        }
        catch( IOException e )
        {
            LOGGER.severe("Couldn't close streams");
        }
    }

    public void send(Request request)
    {
        LOGGER.info("sending " + request.getType());
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

    public Request receive()
    {
        try
        {
            synchronized(in)
            {
                Request request = (Request)in.readObject();
                LOGGER.info("receiving " + request.getType());
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

    /**
     * Default request handler
     */
    private Map<String, RequestRunnable> getDefaultRequestHandler()
    {
        Map<String, RequestRunnable> requestHandler = new HashMap<>();

        requestHandler.put("GREET", (Object greet) -> {
            send(new Request("GREET", player));
        });

        requestHandler.put("READY", (Object ready) -> {
            if(ready instanceof Boolean)
            {
                synchronized(CheckersServer.class)
                {
                    CheckersServer.setReady((Boolean)ready, id);
                }
            }
            else
            {
                send(new Request("ERROR", new Error("Unknown type")));
            }
        });

        requestHandler.put("GAME_START", (Object start) -> {
            synchronized(CheckersServer.class)
            {
                send(new Request("GAME_START", player.getColor()));
                requestHandler.get("UPDATE").run(null);
            }
        });

        requestHandler.put("UPDATE", (Object update) -> {
            synchronized(CheckersServer.class)
            {
                Game game = CheckersServer.getGame();
                send(new Request("UPDATE", game.getState()));
            }
        });

        requestHandler.put("ACKNOWLEDGE", (Object ack) -> {
            LOGGER.info("Acknowledged");
        });

        requestHandler.put("ERROR", (Object error) -> {
            if(error instanceof Error)
            {
                LOGGER.severe(((Error)error).getMessage());
            }
        });

        requestHandler.put("GET_MOVES", (Object moves) -> {
            if(moves instanceof int[])
            {
                synchronized(CheckersServer.class)
                {
                    Game game = CheckersServer.getGame();
                    send(new Request("GET_MOVES", game.getValidMoves(player, (int[])moves)));
                }
            }
            else
            {
                send(new Request("ERROR", new Error("Unknown type")));
            }
        });

        requestHandler.put("MOVE", (Object move) -> {
            if(move instanceof Move)
            {
                try
                {
                    synchronized(CheckersServer.class)
                    {
                        Game game = CheckersServer.getGame();
                        Boolean won = game.move(player, (Move)move);
                        requestHandler.get("END_TURN").run(null);

                        if(won)
                        {
                            send(new Request("WON", player)); // or broadcast?
                        }
                    }
                }
                catch(IllegalAccessError e)
                {
                    send(new Request("ERROR", e));
                }
            }
            else
            {
                send(new Request("ERROR", new Error("Unknown type")));
            }
        });

        requestHandler.put("END_TURN", (Object end_turn) -> {
            try
            {
                synchronized(CheckersServer.class)
                {
                    Game game = CheckersServer.getGame();
                    game.endTurn(player);
                    CheckersServer.broadcast(new Request("UPDATE", game.getState()));
                }
            }
            catch(IllegalAccessError e)
            {
                send(new Request("ERROR", e));
            }
        });

        return requestHandler;
    }
}
