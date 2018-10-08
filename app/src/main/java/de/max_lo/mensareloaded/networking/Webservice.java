package de.max_lo.mensareloaded.networking;

import java.util.List;

import de.max_lo.mensareloaded.networking.gson_object.MealRetro;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Webservice {

    @GET("canteens/{mensa}/days/{date}/meals")
    Call<List<MealRetro>> getMeals(@Path("mensa") String mensaId, @Path("date") String date);
}
