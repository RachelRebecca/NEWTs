package newts;

import newts.json.Spell;
import newts.json.SpellList;
import newts.json.WizardWorldService;
import newts.json.WizardWorldServiceFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WizardWorldServiceTest
{
    @Test
    void getSpell()
    {
        //given
        WizardWorldServiceFactory factory = new WizardWorldServiceFactory();
        WizardWorldService service = factory.getInstance();

        // when
        SpellList spellList = service.getSpell("Conjuration")
                .blockingGet();

        // then
        Spell firstSpell = spellList.get(0);
        System.out.println("name: " + firstSpell.getName()
                + "\neffect: " + firstSpell.getEffect()
                + "\nincantation: " + firstSpell.getIncantation()
                + "\nis it verbal: " + firstSpell.getVerbal()
                + "\nlight: " + firstSpell.getLight());

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