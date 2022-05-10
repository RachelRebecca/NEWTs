import io.reactivex.Observable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import json.Spell;
import json.SpellList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NEWTsPresenterTest
{
    @BeforeAll
    public static void beforeAllTests()
    {
        // the following code makes tests run single-threaded
        RxJavaPlugins.setIoSchedulerHandler((scheduler) -> Schedulers.trampoline());
        RxJavaPlugins.setNewThreadSchedulerHandler((scheduler) -> Schedulers.trampoline());

        // this will run one time before all tests in this class
    }

    @Test
    void loadSpellInformation()
    {
        //given
        NEWTsPracticeExam view = mock(NEWTsPracticeExam.class);
        SpellGenerator model = mock(SpellGenerator.class);
        NEWTsPresenter presenter = new NEWTsPresenter(view, model);

        SpellList spellList = mock(SpellList.class);
        Spell spell = mock(Spell.class);

        when(spellList.size()).thenReturn(1);
        when(spellList.get(0)).thenReturn(spell);

        doReturn("Conjures water").when(spell).getEffect();
        doReturn("Aguamenti").when(spell).getIncantation();
        doReturn("Water-Making Spell").when(spell).getName();
        doReturn("Conjuration").when(spell).getType();
        doReturn(Observable.just(spellList)).when(model).getSpell("Conjuration");

        // when
        presenter.loadSpellInformation("Conjuration");

        // then
        verify(view).setCategorySelected("");
        verify(view).setEffect("<html>Conjures water</html>");
    }

    @Test
    void onSubmitAnswer()
    {
        //given
        NEWTsPracticeExam view = mock(NEWTsPracticeExam.class);
        SpellGenerator model = mock(SpellGenerator.class);
        NEWTsPresenter presenter = new NEWTsPresenter(view, model);

        // when

        presenter.onSubmitAnswer("Aguamenti");

        // then
    }

}