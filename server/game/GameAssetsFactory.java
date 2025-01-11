package server.game;

import java.util.Map;

import server.game.basic.Builder;
import server.game.ooc.OOCBuilder;
import server.game.test.TESTBuilder;

public class GameAssetsFactory
{
    private static Map<String, GameAssetsBuilder> builders = Map.of(
        "BASIC", new Builder(),
        "OOC", new OOCBuilder(),
        "TEST", new TESTBuilder()
    );

    public static GameAssetsBuilder get(String type)
    {
        return builders.get(type);
    }
}
