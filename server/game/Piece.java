package server.game;

import shared.Player;

import java.io.Serializable;

/**
 * Piece class
 * Has a color and an owner - player
 */
public class Piece implements Serializable
{
    private final Player owner;
    private final String color;

    public Piece(String color, Player owner) throws IllegalArgumentException
    {
        if(!owner.getColor().equals(color))
        {
            throw new IllegalArgumentException("Player is of different color");
        }

        this.color = color;
        this.owner = owner;
    }

    public Player getOwner()
    {
        return owner;
    }

    public String getColor()
    {
        return color;
    }
}