import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import json.Spell;
import json.SpellList;
import json.WizardWorldService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class NewtsPresenterTest
{
    // this will run one time before all tests in this class
    @BeforeAll
    public static void beforeAllTests()
    {
        // the following code makes tests run single-threaded
        RxJavaPlugins.setIoSchedulerHandler((scheduler) -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler((scheduler) -> Schedulers.trampoline());
    }

    @Test
    void loadSpellInformation()
    {
        //given
        NewtsPracticeExam view = mock(NewtsPracticeExam.class);
        WizardWorldService model = mock(WizardWorldService.class);
        NewtsPresenter presenter = new NewtsPresenter(view, model);

        setUpAguamentiSpell(model);

        // when
        presenter.loadSpellInformation("Conjuration");

        // then
        verify(view).setCategorySelected("");
        verify(view).setEffect("<html>Conjures water</html>");
    }

    @Test
    void loadSpellInformation_NoSelectedCategory()
    {
        //given
        NewtsPracticeExam view = mock(NewtsPracticeExam.class);
        WizardWorldService model = mock(WizardWorldService.class);
        NewtsPresenter presenter = new NewtsPresenter(view, model);

        // when
        presenter.loadSpellInformation("--");

        // then
        verify(view).resetToDefaults();
        verify(view).setCategorySelected("No Category Selected.");
    }

    @Test
    void onSubmitAnswerBlankCategory()
    {
        //given
        NewtsPracticeExam view = mock(NewtsPracticeExam.class);
        WizardWorldService model = mock(WizardWorldService.class);
        NewtsPresenter presenter = new NewtsPresenter(view, model);

        presenter.spellSelected = false;

        // when
        presenter.onSubmitAnswer("Aguamenti");

        // then
        verify(view).setResult("You must select a category first.");
    }


    @Test
    void onSubmitAnswer_Right()
    {
        //given
        NewtsPracticeExam view = mock(NewtsPracticeExam.class);
        WizardWorldService model = mock(WizardWorldService.class);
        NewtsPresenter presenter = new NewtsPresenter(view, model);

        presenter.spellSelected = true;
        presenter.incantation = "Aguamenti";
        presenter.effect = "Conjures Water";
        presenter.category = "Conjuration";

        setUpShootingArrowSpell(model); // prepare for next question

        // when
        presenter.onSubmitAnswer("Aguamenti");

        // then
        verify(view).setResult("Correct!");
        verify(view).setEffect("<html>Conjures a shooting arrow "
                + "from the caster's wand</html>");

    }

    @Test
    void onSubmitAnswer_NullIncantation()
    {
        //given
        NewtsPracticeExam view = mock(NewtsPracticeExam.class);
        WizardWorldService model = mock(WizardWorldService.class);
        NewtsPresenter presenter = new NewtsPresenter(view, model);

        presenter.spellSelected = true;
        presenter.incantation = null;
        presenter.name = "Arrow Shooting Spell";
        presenter.category = "Conjuration";

        setUpAguamentiSpell(model); // prepare for next question

        // when
        presenter.onSubmitAnswer("Arrow Shooting Spell");

        // then
        verify(view).setResult("Correct!");
        verify(view).setEffect("<html>Conjures water</html>");
    }

    @Test
    void onSubmitAnswer_Wrong()
    {
        //given
        NewtsPracticeExam view = mock(NewtsPracticeExam.class);
        WizardWorldService model = mock(WizardWorldService.class);
        NewtsPresenter presenter = new NewtsPresenter(view, model);

        presenter.spellSelected = true;
        presenter.incantation = "Aguamenti";
        presenter.effect = "Conjures Water";
        presenter.category = "Conjuration";

        setUpShootingArrowSpell(model); // prepare for next question

        // when
        presenter.onSubmitAnswer("Agua Spell");

        // then
        verify(view).setResult("Incorrect, the correct answer was Aguamenti");
    }

    @Test
    void onSubmitAnswer_EndExamination()
    {
        //given
        NewtsPracticeExam view = mock(NewtsPracticeExam.class);
        WizardWorldService model = mock(WizardWorldService.class);
        NewtsPresenter presenter = new NewtsPresenter(view, model);

        presenter.spellSelected = true;
        presenter.incantation = "Aguamenti";
        presenter.totalCorrect = 1;
        presenter.totalAsked = 9;
        presenter.effect = "Conjures Water";

        // when
        presenter.onSubmitAnswer("Aguamenti");

        // then
        verify(view).showResultsMessage("You scored " + 2 + " / "
                + 10 + ": " + 20.0 + "%");
        verify(view).setCategorySelectedIndex(0);
    }

    private void setUpAguamentiSpell(WizardWorldService model)
    {
        SpellList spellList = mock(SpellList.class);
        Spell spell = mock(Spell.class);

        when(spellList.size()).thenReturn(1);
        when(spellList.get(0)).thenReturn(spell);

        doReturn("Conjures water").when(spell).getEffect();
        doReturn("Aguamenti").when(spell).getIncantation();
        doReturn("Water-Making Spell").when(spell).getName();
        doReturn("Conjuration").when(spell).getType();
        doReturn(Single.just(spellList)).when(model).getSpell("Conjuration");
    }

    private void setUpShootingArrowSpell(WizardWorldService model)
    {
        SpellList spellList = mock(SpellList.class);
        Spell spell = mock(Spell.class);

        when(spellList.size()).thenReturn(1);
        when(spellList.get(0)).thenReturn(spell);

        doReturn("Conjures a shooting arrow from the caster's wand").when(spell).getEffect();
        doReturn(null).when(spell).getIncantation();
        doReturn("Arrow Shooting Spell").when(spell).getName();
        doReturn("Conjuration").when(spell).getType();
        doReturn(Single.just(spellList)).when(model).getSpell("Conjuration");
    }

}