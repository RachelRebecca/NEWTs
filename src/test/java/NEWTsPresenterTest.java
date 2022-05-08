import io.reactivex.Observable;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;
import json.SpellList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
    void resetFlashCard()
    {

    }

    @Test
    void loadSpellInformation()
    {
        //given
        NEWTsPracticeExam view = mock(NEWTsPracticeExam.class);
        SpellGenerator model = mock(SpellGenerator.class);
        NEWTsPresenter presenter = new NEWTsPresenter(view, model);
        SpellList currentWeather = mock(SpellList.class);

    }

    @Test
    void checkAnswer()
    {

    }

    @Test
    void potentiallyEndPracticeExam()
    {

    }

    /*
    @Test
    void loadWeatherFromZipcode()
    {
        //given
        CurrentWeatherFrame view = mock(CurrentWeatherFrame.class);
        GetCurrentWeather model = mock(GetCurrentWeather.class);
        CurrentWeatherPresenter presenter = new CurrentWeatherPresenter(view, model);
        CurrentWeather currentWeather = mock(CurrentWeather.class);

        //GetCurrentWeather returns an Observable<CurrentWeather> object
        // by returning a Mock (presenter.loadWeatherFromZipcode("12345"),
        // you'll get a NullPointerException, because model.getCurrentWeather
        // is going to return null using the Mock

        doReturn(100.0).when(currentWeather).getTemperature();
        doReturn(Observable.just(currentWeather)).when(model).getCurrentWeather("00000");

        // when
        presenter.loadWeatherFromZipcode("00000");

        // then
        verify(view).setTemperature(100.0);
    }
     */

}