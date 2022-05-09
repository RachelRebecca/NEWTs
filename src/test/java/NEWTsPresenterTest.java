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

        when(spellList.size()).thenReturn(1);
        System.out.println("size: " + spellList.size());
        when(spellList.get(0)).thenReturn(new Spell());
        System.out.println("size[0]: " + spellList.get(0));

        doReturn("Conjures water").when(spellList.get(0)).getEffect();
        System.out.println("effect: " + spellList.get(0).getEffect());
        doReturn("Aguamenti").when(spellList.get(0)).getIncantation();
        doReturn("Water-Making Spell").when(spellList.get(0)).getName();
        doReturn("Conjuration").when(spellList.get(0).getType());
        doReturn(Observable.just(spellList)).when(model).getSpell("Conjuration");

        // when
        presenter.loadSpellInformation("Conjuration");

        // then
        verify(view).setCategorySelected("");
        verify(view).setEffect("Conjures water");
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