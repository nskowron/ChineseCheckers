package memento;

import shared.GameState;

public class SavedMove 
{
    public int moveID;
    public GameState state;

    public SavedMove(int moveID, GameState state)
    {
        this.moveID = moveID;
        this.state = state;
    } 
}
