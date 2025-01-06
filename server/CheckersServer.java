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
        try(ServerSocket serverSocket = new ServerSocket(PORT)) 
        {
            LOGGER.info("Server is running on port " + PORT + "...");

            IValidityChecker validator = new ValidityChecker(); // Will depend on the argument
            IBoard board = new Board();

            synchronized(gameStarted)
            {
                Thread acceptance = new Thread(() -> {
                    while(true)
                    {
                        if(connectedClients.size() < 6)
                        {
                            try
                            {
                                Socket clientSocket = serverSocket.accept();
                                LOGGER.info("New socket accepted");

                                synchronized(connectedClients)
                                {
                                    Player player = new Player(clientIdCounter);
                                    ClientHandler client = new ClientHandler(clientIdCounter, clientSocket, player, gameStarted);
                                    ServerPlayer connectedClient = new ServerPlayer(clientIdCounter, player, client, false);

                                    everyoneReady = false;

                                    connectedClients.add(connectedClient);
                                    ++clientIdCounter;

                                    Thread clientThread = new Thread(client);
                                    clientThread.start();
                                }
                            }
                            catch(IOException e){ LOGGER.severe(e.getMessage()); }
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
                            LOGGER.info("trying to create game");
                            List<Player> players = new ArrayList<>();
                            for(ServerPlayer client : connectedClients)
                            {
                                players.add(client.gamePlayer);
                            }
                            game = new Game(validator, board, players);
                            LOGGER.info("succeeded");

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

                gameStarted.met = true;
                LOGGER.info("Game has started");
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

            Request waiting = Request.WAITING;
            waiting.setData(players);
            broadcast(waiting);
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
                        Request error = Request.ERROR;
                        error.setData(new Error("Client " + id + " disconnected"));
                        broadcast(error);
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
