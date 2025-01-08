package utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class IntMap <T> extends HashMap<int[], T>
{
    @Override
    public T get(Object id)
    {
        if(id instanceof int[])
        {
            for(Map.Entry<int[], T> entry : this.entrySet())
            {
                if(Arrays.equals(entry.getKey(), (int[])id))
                {
                    return entry.getValue();
                }
            }
        }
        
        return null;
    }
}
