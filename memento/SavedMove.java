package memento;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import shared.GameState;

public class SavedMove implements Serializable
{
    public int moveID;
    public String playerColor;
    public Map<List<Integer>,String> board = new HashMap<>();

    @JsonCreator
    public SavedMove(
        @JsonProperty("moveID") int moveID,
        @JsonProperty("playerColor") String playerColor,
        @JsonProperty("board") Map<List<Integer>, String> board) 
    {
        this.moveID = moveID;
        this.playerColor = playerColor;
        this.board = board;
    }
    
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
