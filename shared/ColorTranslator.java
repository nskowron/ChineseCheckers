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
        "BLUE", Color.BLUE,
        "DEFAULT", Color.rgb(50, 50, 50)
    );

    public static Color get(String name)
    {
        return translation.get(name);
    }
}
