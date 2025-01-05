import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntBinaryOperator;
import java.util.logging.Logger;

public class CheckersServer 
{
    private static final int PORT = 12345;

    private static int clientIdCounter = 0;
    private static ArrayList<ServerPlayer> connectedClients = new ArrayList<>();
    private static Game game = null;

    private static Condition everyoneReady = new Condition();

    private static Logger LOGGER = Logger.getLogger("Server");

    public static void main(final String[] args) 
    {
        try(ServerSocket serverSocket = new ServerSocket(PORT)) 
        {
            LOGGER.info("Server is running on port " + PORT + "...");

            IValidityChecker validator = new ValidityChecker(); // Will depend on the argument
            IBoard board = new Board();

            synchronized(everyoneReady)
            {
                Thread acceptance = new Thread(() -> {
                    while(true)
                    {
                        if(connectedClients.size() < 6)
                        {
                            synchronized(connectedClients)
                            {
                                try
                                {
                                    Socket clientSocket = serverSocket.accept();
                                    GamePlayer player = new GamePlayer(clientIdCounter);
                                    ClientHandler client = new ClientHandler(clientIdCounter, clientSocket, player, everyoneReady);
                                    ServerPlayer connectedClient = new ServerPlayer(clientIdCounter, player, client, false);

                                    everyoneReady.met = false;

                                    connectedClients.add(connectedClient);
                                    ++clientIdCounter;

                                    Thread clientThread = new Thread(client);
                                    clientThread.start();
                                }
                                catch(IOException e) {}
                            }
                        }
                        else
                        {
                            try{ Thread.sleep(1000); }catch( InterruptedException e ){}
                        }
                    }
                });
                acceptance.start();

                while(true)
                {
                    while(everyoneReady.met == false)
                    {
                        try{ Thread.sleep(1000); }catch( InterruptedException e ){}
                    }
                    try
                    {
                        synchronized(connectedClients)
                        {
                            List<GamePlayer> players = new ArrayList<>();
                            for(ServerPlayer client : connectedClients)
                            {
                                players.add((GamePlayer)client.gamePlayer);
                            }
                            game = new Game(validator, board, players);

                            // Succeeded at creating game
                            acceptance.stop();
                            break;
                        }
                    }
                    catch(IllegalArgumentException e) // Wrong no. of players
                    {
                        try{ Thread.sleep(1000); }catch( InterruptedException f ){}
                    }
                }
            }
            // ClientThreads wake up
        } 
        catch(IOException e) 
        {
            LOGGER.severe(e.getMessage());
        }
    }

    public static Game getGame()
    {
        return game;
    }

    public static void setReady(Boolean ready, int id)
    {
        synchronized(connectedClients)
        {
            Boolean startGame = true;
            for(ServerPlayer client : connectedClients)
            {
                if(client.ready == false)
                {
                    startGame = false;
                }

                if(client.id == id)
                {
                    client.ready = ready;
                    return;
                }
            }
            everyoneReady.met = startGame;
        }
    }

    public static void removeClient(int id) 
    {
        synchronized(connectedClients)
        {
            for(int i = 0; i < connectedClients.size(); ++i)
            {
                if(connectedClients.get(i).id == id)
                {
                    connectedClients.remove(i);
                    // probably do sth else

                    return;
                }
            }
        }
    }

    public static void broadcastUpdate()
    {
        synchronized(connectedClients)
        {
            for(ServerPlayer client : connectedClients)
            {
                try
                {
                    client.playerClient.sendUpdate();
                }
                catch(IOException e)
                {
                    LOGGER.severe("Client " + client.id + e.getMessage());
                }
            }
        }
    }
}
