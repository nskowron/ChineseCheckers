package shared;

import javafx.scene.paint.Color;

public class Player
{
    protected final int id;
    protected Color color;

    protected boolean won;

    public Player(int id)
    {
        this.id = id;
        this.color = Color.rgb(50, 50, 50);
        this.won = false;
    }

    public void setColor(Color color)
    {
        if(this.color == Color.rgb(50, 50, 50))
        {
            this.color = color;
        }
    }
    
    public int getId()
    {
        return id;
    }

    public Color getColor()
    {
        return color;
    }

    public boolean didWin()
    {
        return won;
    }
}