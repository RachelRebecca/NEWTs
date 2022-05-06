import io.reactivex.Observable;
import json.Spell;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
//import json.CurrentWeather;
import json.WizardWorldAPI;


public class SpellGenerator
{
    private WizardWorldAPI service;
    public SpellGenerator()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wizard-world-api.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        service = retrofit.create(WizardWorldAPI.class); // retrofit implements the interface for us

    }

    public Observable<Spell> getSpell(String spellType)
    {
        Observable<Spell> observable = service.getSpell(spellType);
        return observable;
    }
}