import java.nio.ReadOnlyBufferException;

public enum Request
{
    // GREET
    // Server expects: nothing
    // Server sends:
    //  -- ACKNOWLEDGE with object Player as data
    GREET,

    // END_TURN
    // Server expects: nothing
    // Server sends:
    // -- ACKNOWLEDGE with object Player - current player
    // -- ERROR if it was not the client's turn
    END_TURN,

    // UPDATE
    // Server expects: nothing
    // Server sends:
    // -- ACKNOWLEDGE with object GameState
    UPDATE,

    // ACKNOWLEDGE
    // Server expects: nothing
    // Server sends: nothing
    ACKNOWLEDGE,

    //ERROR
    // Server expects: object Error
    // Server sends: nothing
    ERROR
    {
        @Override
        public void setData(Object data) throws ClassCastException
        {
            if(data instanceof Error)
            {
                super.setData(data);
            }
            else
            {
                throw new ClassCastException("Request.ERROR only takes in Error");
            }
        }
    },

    // READY
    // Server expects: object Boolean
    // Server sends:
    // -- ACKNOWLEDGE
    // -- ERROR if data is null
    READY
    {
        @Override
        public void setData(Object data) throws ClassCastException
        {
            if(data instanceof Boolean)
            {
                super.setData(data);
            }
            else
            {
                throw new ClassCastException("Request.READY only takes in boolean");
            }
        }
    },

    // GET_MOVES
    // Server expects: object String (or whatever key we'll think of) - beginId
    // Server sends:
    // -- ACKNOWLEDGE with object List<String> with endIds
    GET_MOVES
    {
        @Override
        public void setData(Object data) throws ClassCastException
        {
            if(data instanceof String)
            {
                super.setData(data);
            }
            else
            {
                throw new ClassCastException("Request.GET_MOVES only takes in String");
            }
        }
    },

    // MOVE
    // Server expects: object Move
    // Server sends:
    // -- ACKNOWLEDGE
    // -- ERROR if the move is not valid
    MOVE
    {
        @Override
        public void setData(Object data) throws ClassCastException
        {
            if(data instanceof Move)
            {
                super.setData(data);
            }
            else
            {
                throw new ClassCastException("Request.MOVE only takes in Move");
            }
        }
    };

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