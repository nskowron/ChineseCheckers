public class GamePlayer extends Player
{
    public GamePlayer(int id, Color color)
    {
        super(id, color);
    }

    public void setWon()
    {
        won = true;
    }
}