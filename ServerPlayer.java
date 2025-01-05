public class ServerPlayer
{
    public final int id;
    public Player gamePlayer;
    public ClientHandler playerClient;
    public Boolean ready;

    public ServerPlayer(int id, Player gamePlayer, ClientHandler playerClient, Boolean ready)
    {
        this.id = id;
        this.gamePlayer = gamePlayer;
        this.playerClient = playerClient;
        this.ready = ready;
    }
}