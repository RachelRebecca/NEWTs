package newts.json;

public class Spell
{
    String name;
    String incantation;
    String effect;
    boolean canBeVerbal;
    String type;
    String light;

    public String getName()
    {
        return name;
    }

    public String getIncantation()
    {
        return incantation;
    }

    public String getEffect()
    {
        return effect;
    }

    public boolean getVerbal()
    {
        return canBeVerbal;
    }

    public String getType()
    {
        return type;
    }

    public String getLight()
    {
        return light;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setIncantation(String incantation)
    {
        this.incantation = incantation;
    }

    public void setEffect(String effect)
    {
        this.effect = effect;
    }

    public void setVerbal(boolean canBeVerbal)
    {
        this.canBeVerbal = canBeVerbal;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public void setLight(String light)
    {
        this.light = light;
    }
}
