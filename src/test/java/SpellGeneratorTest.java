import json.Spell;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpellGeneratorTest
{
    @Test
    void getSpell()
    {
        SpellGenerator spellGenerator = new SpellGenerator();

        Spell spell = spellGenerator.getSpell("Conjuration")
                .blockingFirst();

        System.out.println("name: " + spell.getName() +
                "\neffect: " +  spell.getEffect() + " incantation: " + spell.getIncantation() +
                "\nis it verbal: " + spell.getVerbal() + " light: " + spell.getLight());
    }
}