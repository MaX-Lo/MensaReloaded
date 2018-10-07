package de.max_lo.mensareloaded;

import de.max_lo.mensareloaded.database.entity.Meal;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Webservice {

    @GET("/canteens/{mensa}/days/{date}/meals")
    Call<Meal> getMeal(@Path("mensa") String mensaId, @Path("date") String date);
}
