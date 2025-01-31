package server.game;

import java.io.IOException;

import com.fasterxml.jackson.databind.DatabindException;

/**
 * Builder class for the game assets
 * Provides the board and move checker for the game variant
 */
public interface GameAssetsBuilder
{
    public IBoard getBoard() throws IOException, DatabindException;
    public IMoveChecker getMoveChecker();
}
