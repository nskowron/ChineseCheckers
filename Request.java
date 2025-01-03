public enum Request
{
    MOVE
    {
        private Move move;

        @Override
        public void setData(Object data) throws ClassCastException
        {
            move = (Move)data;
        }

        @Override
        public Object getData()
        {
            return move;
        }
    };

    public abstract void setData(Object data);
    public abstract Object getData();
}