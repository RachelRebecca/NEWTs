package newts;

import io.reactivex.Single;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import newts.json.Spell;
import newts.json.SpellList;
import newts.json.WizardWorldService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.inject.Provider;

import static org.mockito.Mockito.*;

class NewtsPresenterTest
{
    private SpellLightColors colors = new SpellLightColors();

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
        Provider<NewtsPracticeExam> viewProvider = () -> view;
        WizardWorldService model = mock(WizardWorldService.class);
        NewtsPresenter presenter = new NewtsPresenter(viewProvider, model);

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
        Provider<NewtsPracticeExam> viewProvider = () -> view;
        WizardWorldService model = mock(WizardWorldService.class);
        NewtsPresenter presenter = new NewtsPresenter(viewProvider, model);

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
        Provider<NewtsPracticeExam> viewProvider = () -> view;
        WizardWorldService model = mock(WizardWorldService.class);
        NewtsPresenter presenter = new NewtsPresenter(viewProvider, model);

        doReturn("Conjuration").when(view).getCategory();

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
        Provider<NewtsPracticeExam> viewProvider = () -> view;
        WizardWorldService model = mock(WizardWorldService.class);
        NewtsPresenter presenter = new NewtsPresenter(viewProvider, model);

        doReturn("Conjuration").when(view).getCategory();

        presenter.spellSelected = true;
        presenter.currSpell = new Spell();
        presenter.currSpell.setIncantation("Aguamenti");
        presenter.currSpell.setEffect("Conjures Water");
        presenter.currSpell.setType("Conjuration");

        setUpShootingArrowSpell(model); // prepare for next question

        // when
        presenter.onSubmitAnswer("Aguamenti");

        // then
        verify(view).setResult("Correct!");
        verify(view).setEffect("<html>Conjures a shooting arrow "
                + "from the caster's wand</html>");
        verify(view).setSpellColor("Transparent");

    }

    @Test
    void onSubmitAnswer_NullIncantation()
    {
        //given
        NewtsPracticeExam view = mock(NewtsPracticeExam.class);
        Provider<NewtsPracticeExam> viewProvider = () -> view;
        WizardWorldService model = mock(WizardWorldService.class);
        NewtsPresenter presenter = new NewtsPresenter(viewProvider, model);

        doReturn("Conjuration").when(view).getCategory();

        presenter.spellSelected = true;
        presenter.currSpell = new Spell();
        presenter.currSpell.setIncantation(null);
        presenter.currSpell.setName("Arrow Shooting Spell");
        presenter.currSpell.setType("Conjuration");

        setUpAguamentiSpell(model); // prepare for next question

        // when
        presenter.onSubmitAnswer("Arrow Shooting Spell");

        // then
        verify(view).setResult("Correct!");
        verify(view).setEffect("<html>Conjures water</html>");
        verify(view).setSpellColor("IcyBlue");
    }

    @Test
    void onSubmitAnswer_Wrong()
    {
        //given
        NewtsPracticeExam view = mock(NewtsPracticeExam.class);
        Provider<NewtsPracticeExam> viewProvider = () -> view;
        WizardWorldService model = mock(WizardWorldService.class);
        NewtsPresenter presenter = new NewtsPresenter(viewProvider, model);

        doReturn("Conjuration").when(view).getCategory();

        presenter.spellSelected = true;
        presenter.currSpell = new Spell();
        presenter.currSpell.setIncantation("Aguamenti");
        presenter.currSpell.setEffect("Conjures Water");
        presenter.currSpell.setType("Conjuration");

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
        Provider<NewtsPracticeExam> viewProvider = () -> view;
        WizardWorldService model = mock(WizardWorldService.class);
        NewtsPresenter presenter = new NewtsPresenter(viewProvider, model);

        doReturn("Conjuration").when(view).getCategory();

        presenter.spellSelected = true;
        presenter.currSpell = new Spell();
        presenter.currSpell.setIncantation("Aguamenti");
        presenter.totalCorrect = 1;
        presenter.totalAsked = 9;
        presenter.currSpell.setEffect("Conjures Water");

        // when
        presenter.onSubmitAnswer("Aguamenti");

        // then
        String scoreMessage = "You scored 2 / 10: 20.0%";
        verify(view).showResultsMessage(scoreMessage);
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
        doReturn("IcyBlue").when(spell).getLight();
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
        doReturn("Transparent").when(spell).getLight();
        doReturn(Single.just(spellList)).when(model).getSpell("Conjuration");
    }

}