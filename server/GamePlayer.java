package server;

import shared.Player;

public class GamePlayer extends Player
{
    public GamePlayer(int id)
    {
        super(id);
    }

    public void setWon()
    {
        won = true;
    }
}