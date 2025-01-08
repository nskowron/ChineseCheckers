package shared;

import java.io.Serializable;

public class Request implements Serializable
{
    protected final String type;
    protected Object data;

    public Request(String type, Object data)
    {
        this.type = type;
        this.data = data;
    }

    public Request(String type)
    {
        this(type, null);
    }

    public void setData(Object data)
    {
        this.data = data;
    }

    public Object getData()
    {
        return data;
    }

    public String getType()
    {
        return type;
    }
}