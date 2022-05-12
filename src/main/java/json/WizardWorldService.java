package json;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WizardWorldService
{
    @GET("Spells")
    Observable<SpellList> getSpell(@Query("type") String spellType);
}
