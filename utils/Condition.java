package utils;

/**
 * A simple condition class because Boolean is not synchronizable
 */
public class Condition
{
    public Boolean met;

    public Condition()
    {
        met = false;
    }
}