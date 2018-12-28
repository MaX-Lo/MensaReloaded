package de.max_lo.mensareloaded.networking;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import de.max_lo.mensareloaded.Mensa;
import de.max_lo.mensareloaded.database.entity.Meal;
import de.max_lo.mensareloaded.helper.DateHelper;
import de.max_lo.mensareloaded.helper.MealHelper;
import de.max_lo.mensareloaded.helper.MensaHelper;
import de.max_lo.mensareloaded.networking.gson_object.MealRetro;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkController {

    private static final String TAG = NetworkController.class.getName();
    private static final String BASE_URL = "http://openmensa.org/api/v2/";

    private Webservice webservice;
    private NetworkListener caller;

    public NetworkController(NetworkListener caller) {
        this.caller = caller;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        webservice = retrofit.create(Webservice.class);
    }

    public void fetchMeals(final Mensa mensa, final long date) {
        Call<List<MealRetro>> call = webservice.getMeals(
                MensaHelper.getMensaId(mensa),
                DateHelper.getDateStringFromDaysSinceEpoch(date));

        call.enqueue(new Callback<List<MealRetro>>() {
            @Override
            public void onResponse(Call<List<MealRetro>> call, Response<List<MealRetro>> response) {
                int statusCode = response.code();
                List<MealRetro> receivedMeals = response.body();
                if (receivedMeals != null) {
                    Log.d(TAG, "received meals with statusCode " + statusCode + " " + receivedMeals.get(0).getName());
                    onReceivedMeals(mensa, date, receivedMeals);
                }
            }

            @Override
            public void onFailure(Call<List<MealRetro>> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, "network request failed");
                t.printStackTrace();
            }
        });
    }

    public void onReceivedMeals(Mensa mensa, long date, List<MealRetro> mealsRetro) {
        List<Meal> meals = new ArrayList<>();
        for (MealRetro mealRetro : mealsRetro) {
            // add price information for meals the api doesn't provide prices for
            meals.add((MealHelper.fixAPIInconsistencies(toMeal(mealRetro))));
        }
        caller.onReceivedMeals(mensa, date, meals);
    }

    public Meal toMeal(MealRetro mealRetro) {
        return new Meal(
                mealRetro.getId().toString(),
                mealRetro.getName(),
                mealRetro.getNotes(),
                MealHelper.formatPricing(String.valueOf(mealRetro.getPrices().getStudents())),
                mealRetro.getCategory()
        );
    }
}
