import java.nio.ReadOnlyBufferException;

public enum Request
{
    GREET,
    END_TURN,
    UPDATE,
    ACKNOWLEDGE,
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
    GET_MOVES
    {
        @Override
        public void setData(Object data) throws ClassCastException
        {
            if(data instanceof Integer)
            {
                super.setData(data);
            }
            else
            {
                throw new ClassCastException("Request.GET_MOVES only takes in int");
            }
        }
    },
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