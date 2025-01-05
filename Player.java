public class Player
{
    public final int id;
    protected Color color;

    protected boolean won;

    public Player(int id)
    {
        this.id = id;
        this.color = Color.DEFAULT;
        this.won = false;
    }

    public void setColor(Color color)
    {
        if(this.color == Color.DEFAULT)
        {
            this.color = color;
        }
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