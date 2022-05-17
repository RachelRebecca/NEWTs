package json;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WizardWorldServiceFactory
{
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
