package utils;

import java.util.ArrayList;
import java.util.Arrays;

public class IntList extends ArrayList<int[]>
{
    @Override
    public boolean contains(Object id)
    {
        for(int[] element : this)
        {
            if(Arrays.equals(element, (int[])id))
            {
                return true;
            }
        }

        return false;
    }
}
