package json;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WizardWorldService
{
    @GET("Spells")
    Single<SpellList> getSpell(@Query("type") String spellType);
}

