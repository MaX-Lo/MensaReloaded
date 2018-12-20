package de.max_lo.mensareloaded.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import de.max_lo.mensareloaded.Mensa;
import de.max_lo.mensareloaded.helper.MealHelper;
import de.max_lo.mensareloaded.networking.NetworkController;
import de.max_lo.mensareloaded.networking.NetworkListener;
import de.max_lo.mensareloaded.networking.Webservice;
import de.max_lo.mensareloaded.database.AppRoomDatabase;
import de.max_lo.mensareloaded.database.dao.MealDao;
import de.max_lo.mensareloaded.database.dao.OfferDao;
import de.max_lo.mensareloaded.database.entity.Meal;
import de.max_lo.mensareloaded.database.entity.Offer;
import de.max_lo.mensareloaded.helper.DateHelper;
import de.max_lo.mensareloaded.helper.MensaHelper;
import de.max_lo.mensareloaded.networking.gson_object.MealRetro;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealRepository implements NetworkListener {

    private static final String TAG = MealRepository.class.getName();

    private NetworkController nc;

    private MealDao mealDao;
    private OfferDao offerDao;

    private LiveData<List<Meal>> meals;

    public MealRepository(Application application) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        mealDao = db.mealDao();
        offerDao = db.offerDao();

        nc = new NetworkController(this);

    }

    @Override
    public void onReceivedMeals(Mensa mensa, long date, List<Meal> meals) {
        for (Meal meal : meals) {
            insertMeal(meal);
            insertOffer(new Offer(date, mensa, meal.getId()));
        }
    }

    public LiveData<List<Meal>> getMeals(Mensa mensa, long date) {
        // Todo only fetch from network when not already in db
        nc.fetchMeals(mensa, date);

        return mealDao.getMeals(mensa, date);
    }

    public void insertMeal(Meal meal) {
        new insertMealAsyncTask(mealDao).execute(meal);
    }

    private static class insertMealAsyncTask extends AsyncTask<Meal, Void, Void> {

        private MealDao mAsyncTaskDao;

        insertMealAsyncTask(MealDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Meal... params) {
            if (mAsyncTaskDao.getMeal(params[0].getId()) == null) {
                mAsyncTaskDao.insert(params[0]);
            }
            return null;
        }
    }

    public void insertOffer(Offer offer) {
        new insertOfferAsyncTask(offerDao).execute(offer);
    }

    private static class insertOfferAsyncTask extends AsyncTask<Offer, Void, Void> {

        private OfferDao mAsyncTaskDao;

        insertOfferAsyncTask(OfferDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Offer... params) {
            if (mAsyncTaskDao.getOffer(params[0].getMensa(), params[0].getDate(),
                    params[0].getMealId()) == null) {
                mAsyncTaskDao.insert(params[0]);
            }
            return null;
        }
    }
}
