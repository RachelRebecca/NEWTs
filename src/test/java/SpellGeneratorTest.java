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
        boolean foundAguamenti = false;

        // when
        SpellList spellList = spellGenerator.getSpell("Conjuration")
                .blockingFirst();

        // then
        System.out.println("name: " + spellList.get(0).getName() +
                "\neffect: " +  spellList.get(0).getEffect() +
                "\nincantation: " + spellList.get(0).getIncantation() +
                "\nis it verbal: " + spellList.get(0).getVerbal() +
                "\nlight: " + spellList.get(0).getLight());

        for (int i = 0; i < spellList.size(); i++)
        {
            if (spellList.get(i).getEffect().equals("Conjures water"))
            {
                assertEquals(spellList.get(i).getName(), "Water-Making Spell");
                assertEquals(spellList.get(i).getIncantation(), "Aguamenti");
                assertTrue(spellList.get(i).getVerbal());
                assertEquals(spellList.get(i).getLight(), "IcyBlue");
                assertEquals(spellList.get(i).getType(), "Conjuration");
            }
            foundAguamenti = true;
            break;
        }

        assertTrue(foundAguamenti);
    }
}