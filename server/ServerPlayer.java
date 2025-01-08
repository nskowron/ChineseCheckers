package server;

import shared.Player;

public class ServerPlayer
{
    public final int id;
    public Player gamePlayer;
    public ClientHandler playerClient;
    public Thread clientThread;
    public Boolean ready;

    public ServerPlayer(int id, Player gamePlayer, ClientHandler playerClient, Thread clientThread)
    {
        this.id = id;
        this.gamePlayer = gamePlayer;
        this.playerClient = playerClient;
        this.clientThread = clientThread;
        this.ready = false;
    }
}