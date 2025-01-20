package server;

import utils.Condition;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import server.bots.ClosestMoveBot;
import server.game.Game;
import server.game.GameAssetsBuilder;
import server.game.IBoard;
import server.game.IMoveChecker;
import server.game.GameAssetsFactory;
import shared.Player;
import shared.Request;
import memento.Recorder;

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
        IMoveChecker validator;
        try
        {
            Recorder.initialize("game_updates.json");
            GameAssetsBuilder builder;
            if(args.length == 0)
            {
                builder = GameAssetsFactory.get("BASIC");
            }
            else
            {
                LOGGER.info("Custom game: " + args[0]);
                builder = GameAssetsFactory.get(args[0]);
            }

            board = builder.getBoard();
            validator = builder.getMoveChecker();
        }
        catch(IOException e)
        {
            LOGGER.severe(e.getMessage());
            return;
        }

        try(ServerSocket serverSocket = new ServerSocket(PORT)) 
        {
            LOGGER.info("Server is running on port " + PORT + "...");

            // Consider putting commands and acceptance into their own classes
            Thread commands = new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                while(true)
                {
                    String command = scanner.nextLine();

                    // probably could add checking for args which bot
                    // and make factory command -> runnable, instead of ifs
                    if(command.equals("ADD_BOT"))
                    {
                        try
                        {
                            Thread bot = new Thread(new ClosestMoveBot(board));
                            bot.start();
                        }
                        catch(IOException e)
                        {
                            LOGGER.severe("Bot creation failed");
                        }
                    }
                    else if(command.equals("OFF"))
                    {
                        break;
                    }
                }
                scanner.close();
            });
            commands.start();

            synchronized(gameStarted)
            {
                Thread acceptance = new Thread(() -> {
                    while(!gameStarted.met)
                    {
                        try
                        {
                            Socket clientSocket = serverSocket.accept();
                            if(Thread.interrupted())
                            {
                                break;
                            }

                            LOGGER.info("New socket accepted");

                            synchronized(connectedClients)
                            {
                                everyoneReady = false;

                                Player player = new Player(clientIdCounter);
                                ClientHandler client = new ClientHandler(clientIdCounter, clientSocket, player, gameStarted);
                                Thread clientThread = new Thread(client);
                                ServerPlayer connectedClient = new ServerPlayer(clientIdCounter, player, client, clientThread);

                                connectedClients.add(connectedClient);
                                ++clientIdCounter;

                                clientThread.start();
                            }
                        }
                        catch(IOException e){ LOGGER.severe(e.getMessage()); try{ Thread.sleep(1000); }catch( InterruptedException f ){ break; }}
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
                            Socket dummySocket = new Socket("localhost", PORT); // for breaking acceptance thread
                            dummySocket.close();
                            break;
                        }
                    }
                    catch(IllegalArgumentException | IOException e) // Wrong no. of players
                    {
                        try{ Thread.sleep(1000); }catch( InterruptedException f ){}
                    }
                }

                gameStarted.met = true;
                LOGGER.info("Game has started");
            }
            
            List<Thread> clientThreads = new ArrayList<>();
            for(ServerPlayer client : connectedClients)
            {
                clientThreads.add(client.clientThread);
            }
            for(Thread thread : clientThreads)
            {
                try
                {
                    thread.join();
                }
                catch(InterruptedException e) {}
            }
        } 
        catch(IOException e) 
        {
            LOGGER.severe(e.getMessage());
        }
        finally
        {
            Recorder.shutdown();
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

                players[0] += client.ready ? 1 : 0;
            }
            everyoneReady = players[0] == players[1];

            broadcast(new Request("WAITING", players));
        }
    }

    public static void removeClient(int id) 
    {
        LOGGER.info("Client " + id + " disconnected");
        synchronized(connectedClients)
        {
            for(int i = 0; i < connectedClients.size(); ++i)
            {
                ServerPlayer client = connectedClients.get(i);
                if(gameStarted.met)
                {
                    connectedClients.remove(i--);
                    if(client.id != id)
                    {
                        client.playerClient.send(new Request("ERROR", new Error("Client " + id + " disconnected")));
                    }
                }
                else if(client.id == id)
                {
                    connectedClients.remove(i);
                    setReady(null, id);
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
            Recorder.record(request);
        }
    }
}
