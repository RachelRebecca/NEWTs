import json.Spell;
import json.SpellList;
import json.WizardWorldAPI;
import json.WizardWorldAPIFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WizardWorldAPITest
{
    @Test
    void getSpell()
    {
        //given
        WizardWorldAPIFactory factory = new WizardWorldAPIFactory();
        WizardWorldAPI service = factory.getInstance();

        // when
        SpellList spellList = service.getSpell("Conjuration")
                .blockingFirst();

        // then
        Spell firstSpell = spellList.get(0);
        System.out.println("name: " + firstSpell.getName() +
                "\neffect: " + firstSpell.getEffect() +
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

                return;
            }
        }

        fail("Spell was not found.");
    }
}