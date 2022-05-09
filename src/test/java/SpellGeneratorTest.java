import json.Spell;
import json.SpellList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpellGeneratorTest
{
    @Test
    void getSpell()
    {
        //given
        SpellGenerator spellGenerator = new SpellGenerator();
        boolean spellFound = false;

        // when
        SpellList spellList = spellGenerator.getSpell("Conjuration")
                .blockingFirst();

        // then
        Spell firstSpell = spellList.get(0);
        System.out.println("name: " + firstSpell.getName() +
                "\neffect: " +  firstSpell.getEffect() +
                "\nincantation: " + firstSpell.getIncantation() +
                "\nis it verbal: " + firstSpell.getVerbal() +
                "\nlight: " + firstSpell.getLight());

        for (Spell spell : spellList)
        {
            if (spell.getEffect().equals("Conjures water"))
            {
                assertEquals(spell.getName(), "Water-Making Spell");
                assertEquals(spell.getIncantation(), "Aguamenti");
                assertTrue(spell.getVerbal());
                assertEquals(spell.getLight(), "IcyBlue");
                assertEquals(spell.getType(), "Conjuration");

                spellFound = true;
                break;
            }
        }

        assertTrue(spellFound);
    }
}