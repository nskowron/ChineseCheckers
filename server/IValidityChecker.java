package server;

import shared.Move;

public interface IValidityChecker
{
    public boolean validMove(Move move);
}