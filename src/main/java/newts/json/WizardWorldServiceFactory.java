package newts.json;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Inject;

public class WizardWorldServiceFactory
{
    @Inject
    public WizardWorldServiceFactory()
    {
        // dagger needs a blank constructor annotated with inject to function
    }

    public WizardWorldService getInstance()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wizard-world-api.herokuapp.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(WizardWorldService.class);
    }
}
