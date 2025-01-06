package shared;

import java.io.Serializable;

public class Player implements Serializable
{
    protected final int id;
    protected String color;

    protected boolean won;

    public Player(int id)
    {
        this.id = id;
        this.color = "DEFAULT";
        this.won = false;
    }

    public void setColor(String color)
    {
        if(this.color.equals("DEFAULT"))
        {
            this.color = color;
        }
    }
    
    public int getId()
    {
        return id;
    }

    public String getColor()
    {
        return color;
    }

    public boolean didWin()
    {
        return won;
    }
}