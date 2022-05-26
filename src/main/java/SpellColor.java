import javax.swing.*;
import java.awt.*;

public class SpellColor extends JComponent
{
    private String spellColorName = "none";
    private Color spellColor;
    private SpellLightColors spellLightColors;

    public SpellColor()
    {
        spellLightColors = new SpellLightColors();
        setPreferredSize(new Dimension(60, 60));
    }

    public void setColorName(String spellColorName)
    {
        this.spellColorName = spellColorName;
    }

    @Override
    protected void paintComponent(Graphics graphics)
    {
        Graphics2D graphics2D = (Graphics2D) graphics;
        super.paintComponent(graphics2D);

        spellColor = spellLightColors.lightToColor.get(spellColorName);
        if (spellColor != null)
        {
            graphics.setColor(spellColor);
            graphics.fillOval(0, 20, 15, 15);
        } else
        {
            graphics.setColor(Color.BLACK);
            graphics.drawOval(0, 20, 15, 15);
        }
    }

}
