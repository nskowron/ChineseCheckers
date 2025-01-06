package server;

import shared.*;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.HashMap;
import java.util.logging.Logger;

public class ClientHandler implements Runnable 
{
    private final int id;
    private final Socket clientSocket;
    private final Player player;
    private Object gameStarted;

    private Map<Request, RequestRunnable> requestHandler;
    ObjectOutputStream out;
    ObjectInputStream in;

    private final Logger LOGGER;

    private boolean running;

    public ClientHandler(int id, Socket clientSocket, Player player, Object gameStarted) 
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

        requestHandler = getDefaultRequestHandler();
        requestHandler.get(Request.GREET).run(Request.GREET);

        Thread readiness = new Thread(() -> {
            while(running)
            {
                Request request = receive();
                if(request == Request.READY)
                {
                    requestHandler.get(Request.READY).run(request);
                }
                else
                {
                    Request error = Request.ERROR;
                    error.setData(new Error("Game has not started yet"));
                    send(error);
                }
            }
        });

        readiness.start();
        synchronized(gameStarted){}
        readiness.stop(); // I know the risk
        
        while(running)
        {
            Request request = receive();
            RequestRunnable action = requestHandler.get(request);
            if(action != null)
            {
                synchronized(this)
                {
                    action.run(request);
                }
            }
            else
            {
                Request error = Request.ERROR;
                error.setData("Non-handled request");
                send(error);
            }
        }

        LOGGER.info("ClientHandler died");
    }

    public void disconnect(boolean ok)
    {
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
            clientSocket.close();
            in.close();
            out.close();
        }
        catch( IOException e )
        {
            LOGGER.severe("Couldn't close streams");
        }
    }

    public void send(Request request)
    {
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
            return (Request)in.readObject();
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

    private Map<Request, RequestRunnable> getDefaultRequestHandler()
    {
        Map<Request, RequestRunnable> requestHandler = new HashMap<>();

        requestHandler.put(Request.GREET, (Request greet) -> {
            greet.setData(player);
            send(greet);
        });

        requestHandler.put(Request.END_TURN, (Request end_turn) -> {
            try
            {
                Request update = Request.UPDATE;
                synchronized(CheckersServer.class)
                {
                    Game game = CheckersServer.getGame();
                    game.endTurn(player);
                    update.setData(game.getState());
                    CheckersServer.broadcast(update);
                }
            }
            catch(IllegalAccessError e)
            {
                Request error = Request.ERROR;
                error.setData(e);
                send(error);
            }
        });

        requestHandler.put(Request.UPDATE, (Request update) -> {
            synchronized(CheckersServer.class)
            {
                Game game = CheckersServer.getGame();
                update.setData(game.getState());
            }
            send(update);
        });

        requestHandler.put(Request.ACKNOWLEDGE, (Request ack) -> {
            LOGGER.info("Acknowledged");
        });

        requestHandler.put(Request.ERROR, (Request error) -> {
            if(error.getData() instanceof Error)
            {
                LOGGER.severe(((Error)error.getData()).getMessage());
            }
        });

        requestHandler.put(Request.READY, (Request ready) -> {
            if(ready.getData() instanceof Boolean)
            {
                synchronized(CheckersServer.class)
                {
                    CheckersServer.setReady((Boolean)ready.getData(), id);
                }
            }
            else
            {
                Request error = Request.ERROR;
                error.setData(new Error("Unknown type"));
                send(error);
            }
        });

        requestHandler.put(Request.GET_MOVES, (Request moves) -> {
            if(moves.getData() instanceof int[])
            {
                synchronized(CheckersServer.class)
                {
                    Game game = CheckersServer.getGame();
                    moves.setData(game.getValidMoves(player, (int[])moves.getData()));
                }
                send(moves);
            }
            else
            {
                Request error = Request.ERROR;
                error.setData(new Error("Unknown type"));
                send(error);
            }
        });

        requestHandler.put(Request.MOVE, (Request move) -> {
            if(move.getData() instanceof Move)
            {
                try
                {
                    synchronized(CheckersServer.class)
                    {
                        Game game = CheckersServer.getGame();
                        Boolean won = game.move(player, (Move)move.getData());

                        Request update = Request.UPDATE;
                        update.setData(game.getState());
                        CheckersServer.broadcast(update);

                        if(won)
                        {
                            Request win =  Request.WON;
                            win.setData(player);
                            send(win);
                        }
                    }
                }
                catch(IllegalAccessError e)
                {
                    Request error = Request.ERROR;
                    error.setData(e);
                    send(error);
                }
            }
            else
            {
                Request error = Request.ERROR;
                error.setData(new Error("Unknown type"));
                send(error);
            }
        });

        return requestHandler;
    }
}
