package server;

import utils.Condition;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import shared.Player;
import shared.Request;

public class CheckersServer 
{
    private static final int PORT = 12345;

    private static int clientIdCounter = 0;
    private static ArrayList<ServerPlayer> connectedClients = new ArrayList<>();
    private static Game game = null;

    private static boolean everyoneReady = false;
    private static Condition gameStarted = new Condition();

    private static Logger LOGGER = Logger.getLogger("Server");

    public static void main(final String[] args) 
    {
        IBoard board;
        IValidityChecker validator;
        try
        {
            File jsonStarFile = new File("data/star.json");
            validator = new ValidityChecker(); // Will depend on the argument
            board = new Board(jsonStarFile);
        }
        catch(IOException e)
        {
            LOGGER.severe(e.getMessage());
            return;
        }

        try(ServerSocket serverSocket = new ServerSocket(PORT)) 
        {
            LOGGER.info("Server is running on port " + PORT + "...");

            synchronized(gameStarted)
            {
                Thread acceptance = new Thread(() -> {
                    while(!gameStarted.met)
                    {
                        if(connectedClients.size() < 6)
                        {
                            try
                            {
                                Socket clientSocket = serverSocket.accept();
                                LOGGER.info("New socket accepted");

                                synchronized(connectedClients)
                                {
                                    everyoneReady = false;

                                    Player player = new Player(clientIdCounter);
                                    ClientHandler client = new ClientHandler(clientIdCounter, clientSocket, player, gameStarted);
                                    Thread clientThread = new Thread(client);
                                    ServerPlayer connectedClient = new ServerPlayer(clientIdCounter, player, client, clientThread);

                                    connectedClients.add(connectedClient);
                                    setReady(false, clientIdCounter);
                                    ++clientIdCounter;


                                    clientThread.start();
                                }
                            }
                            catch(IOException e){ LOGGER.severe(e.getMessage()); try{ Thread.sleep(1000); }catch( InterruptedException f ){}}
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
                    while(everyoneReady == false)
                    {
                        try{ Thread.sleep(1000); }catch( InterruptedException e ){}
                    }
                    try
                    {
                        synchronized(connectedClients)
                        {
                            LOGGER.info("trying to create game - players: " + connectedClients.size());
                            List<Player> players = new ArrayList<>();
                            for(ServerPlayer client : connectedClients)
                            {
                                players.add(client.gamePlayer);
                            }
                            game = new Game(validator, board, players);
                            LOGGER.info("succeeded");

                            // Succeeded at creating game
                            acceptance.interrupt();
                            break;
                        }
                    }
                    catch(IllegalArgumentException e) // Wrong no. of players
                    {
                        try{ Thread.sleep(1000); }catch( InterruptedException f ){}
                    }
                }

                gameStarted.met = true;
                LOGGER.info("Game has started");
            }
            // ClientThreads wake up
            // Wait for them?
            // yup
            for(ServerPlayer client : connectedClients)
            {
                try
                {
                    client.clientThread.join();
                }
                catch(InterruptedException e) {}
            }
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
            int[] players = {0, connectedClients.size()};
            for(ServerPlayer client : connectedClients)
            {
                if(client.id == id)
                {
                    client.ready = ready;
                }

                if(client.ready)
                {
                    players[0] += 1;
                }
            }
            everyoneReady = players[0] == players[1];

            broadcast(new Request("WAITING", players));
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
                    LOGGER.info("Client " + id + " removed.");

                    if(gameStarted.met)
                    {
                        broadcast(new Request("ERROR", new Error("Client " + id + " disconnected")));
                    }

                    return;
                }
            }
        }
    }

    public static void broadcast(Request request)
    {
        synchronized(connectedClients)
        {
            for(ServerPlayer client : connectedClients)
            {
                client.playerClient.send(request);
            }
        }
    }
}
