package server.game.test;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.DatabindException;

import server.game.GameAssetsBuilder;
import server.game.IBoard;
import server.game.IMoveChecker;
import server.game.basic.MoveChecker;

public class TESTBuilder implements GameAssetsBuilder
{
    private IBoard board = null;

    @Override
    public IBoard getBoard() throws IOException, DatabindException
    {
        File jsonStarFile = new File("data/star.json");
        board = new TESTBoard(jsonStarFile);
        return board;
    }

    @Override
    public IMoveChecker getMoveChecker()
    {
        return new MoveChecker(board);
    }
}
