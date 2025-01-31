package shared;

import java.io.Serializable;

/**
 * Game player
 * Has an id and a color assigned
 */
public class Player implements Serializable
{
    protected final int id;
    protected String color;

    public Player(int id)
    {
        this.id = id;
        this.color = "DEFAULT";
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
}