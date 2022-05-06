import json.Spell;
import json.SpellList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpellGeneratorTest
{
    @Test
    void getSpell()
    {
        SpellGenerator spellGenerator = new SpellGenerator();

        SpellList spellList = spellGenerator.getSpell("Conjuration")
                .blockingFirst();

        System.out.println("name: " + spellList.get(0).getName() +
                "\neffect: " +  spellList.get(0).getEffect() +
                "\nincantation: " + spellList.get(0).getIncantation() +
                "\nis it verbal: " + spellList.get(0).getVerbal() +
                "\nlight: " + spellList.get(0).getLight());
    }
}