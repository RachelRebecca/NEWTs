package json;

public class Spell
{
    Main main; // This is causing an issue,
    // 1) I don't know what to name it
    // 2) It's expecting an object but is given an array

    public String getName()
    {
        return main.name;
    }

    public String getIncantation()
    {
        return main.incantation;
    }

    public String getEffect()
    {
        return main.effect;
    }

    public boolean getVerbal()
    {
        return main.canBeVerbal;
    }

    public String getType()
    {
        return main.type;
    }

    public String getLight()
    {
        return main.light;
    }
}
