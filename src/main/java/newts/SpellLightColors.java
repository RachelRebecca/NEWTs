package newts;

import java.awt.*;
import java.util.HashMap;

public class SpellLightColors
{
    public HashMap<String, Color> lightToColor = new HashMap<>();

    public SpellLightColors()
    {
        lightToColor.put("Blue", Color.BLUE);
        lightToColor.put("IcyBlue", new Color(0, 255, 255));
        lightToColor.put("Red", Color.RED);
        lightToColor.put("Gold", new Color(212, 175, 55));
        lightToColor.put("Purple", new Color(255, 0, 255));
        lightToColor.put("Transparent", new Color(192, 90, 192, 60));
        lightToColor.put("White", Color.WHITE);
        lightToColor.put("Green", Color.GREEN);
        lightToColor.put("Orange", Color.ORANGE);
        lightToColor.put("Yellow", Color.YELLOW);
        lightToColor.put("BrightBlue", new Color(0, 175, 255));
        lightToColor.put("Pink", Color.PINK);
        lightToColor.put("Violet", new Color(207, 159, 255));
        lightToColor.put("Scarlet", new Color(255, 24, 0));
        lightToColor.put("BlueishWhite", new Color(150, 233, 244));
        lightToColor.put("Silver", new Color(192, 192, 192));
        lightToColor.put("FieryScarlet", new Color(205, 33, 42));
        lightToColor.put("Grey", Color.GRAY);
        lightToColor.put("DarkRed", new Color(139, 0, 0));
        lightToColor.put("Turquoise", new Color(48, 213, 200));
        lightToColor.put("Fire", new Color(255, 65, 0));
        lightToColor.put("PsychedelicTransparentWave", new Color(99, 230, 232));
        lightToColor.put("BrightYellow", new Color(230, 255, 0));
        lightToColor.put("BlackSmoke", new Color(0, 0, 0, 99));
        lightToColor.put("Black", Color.BLACK);
    }
}
