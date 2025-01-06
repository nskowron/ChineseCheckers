package shared;

public enum Request
{
    // GREET
    // Server expects: nothing
    // Server sends:
    //  -- GREET with object Player as data
    GREET,

    // END_TURN
    // Server expects: nothing
    // Server sends:
    // -- END_TURN with object Player - current player
    // -- ERROR if it was not the client's turn
    END_TURN,

    // ACKNOWLEDGE
    // Server expects: nothing
    // Server sends: nothing
    ACKNOWLEDGE,

    // UPDATE
    // Server expects: nothing
    // Server sends:
    // -- UPDATE with object GameState
    UPDATE,

    //ERROR
    // Server expects: object Error
    // Server sends: nothing
    ERROR,

    // READY
    // Server expects: object Boolean
    // Server sends:
    // -- WAITING (to EVERYONE)
    // -- ERROR if data is null
    READY,

    // WAITING
    // Server expects: nothing, he only sends
    // Server sends:
    // -- int[] players ready, all players
    WAITING,

    // GAME_START
    // Server expects: nothing, he only sends
    // Server sends:
    // -- UPDATE with object GameState, this signals the clients to exit waiting room
    GAME_START,

    // GET_MOVES
    // Server expects: object String (or whatever key we'll think of) - beginId
    // Server sends:
    // -- GET_MOVES with object List<String> with endIds
    GET_MOVES,

    // MOVE
    // Server expects: object Move
    // Server sends:
    // -- MOVE
    // -- ERROR if the move is not valid
    MOVE,

    // WON
    // Server expects: nothing
    // Server sends:
    // -- object Player - the winner
    WON;

    protected Object data = null;

    public void setData(Object data)
    {
        this.data = data;
    }

    public Object getData()
    {
        return data;
    }
}