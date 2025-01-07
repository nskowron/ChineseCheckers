package server;

import shared.Move;

public class ValidityChecker implements IValidityChecker
{
    @Override
    public boolean validMove(Move move)
    {
        return true;
    }
}