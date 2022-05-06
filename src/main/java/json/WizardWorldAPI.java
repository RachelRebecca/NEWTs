package json;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

 public interface WizardWorldAPI
 {
     @GET("Spells")
     Observable<Spell> getSpell(@Query("type") String spellType); //represents that something is going to be returned here
 }

