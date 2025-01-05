public enum Color
{
    DEFAULT(0xAAAAAA),
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF),
    YELLOW(0xFFFF00),
    BLACK(0x000000),
    WHITE(0xFFFFFF);

    private final int hex;

    private Color(int hex)
    {
        this.hex = hex;
    }

    public int getHex()
    {
        return hex;
    }
}