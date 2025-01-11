package server.game;

import java.io.IOException;

import com.fasterxml.jackson.databind.DatabindException;

public interface GameAssetsBuilder
{
    public IBoard getBoard() throws IOException, DatabindException;
    public IMoveChecker getMoveChecker();
}
