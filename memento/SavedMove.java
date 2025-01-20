package memento;

import java.util.List;
import java.util.Map;

import shared.GameState;

public class SavedMove 
{
    public int moveID;
    public String playerColor;
    public Map<List<Integer>,String> board;

    public SavedMove(int moveID, GameState state)
    {
        this.moveID = moveID;
        this.playerColor = state.currentTurn.getColor();
        
        for(Map.Entry<int[], String> entry : state.board.entrySet())
        {
            List<Integer> list = List.of(entry.getKey()[0], entry.getKey()[1]);
            String string = entry.getValue();
            this.board.put(list, string);
        }
    } 
}
