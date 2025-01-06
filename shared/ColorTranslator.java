package shared;

import java.util.Map;

import javafx.scene.paint.Color;

public class ColorTranslator
{
    private static final Map<String, Color> translation = Map.of(
        "GREY", Color.GREY,
        "CYAN", Color.CYAN,
        "GREEN", Color.GREEN,
        "RED", Color.RED,
        "MAGENTA", Color.MAGENTA,
        "YELLOW", Color.YELLOW,
        "BLUE", Color.BLUE
    );

    public static Color get(String name)
    {
        return translation.get(name);
    }
}
