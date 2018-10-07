package de.max_lo.mensareloaded.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

import de.max_lo.mensareloaded.Mensa;
import de.max_lo.mensareloaded.database.AppRoomDatabase;
import de.max_lo.mensareloaded.database.dao.MealDao;
import de.max_lo.mensareloaded.database.dao.OfferDao;
import de.max_lo.mensareloaded.database.entity.Meal;
import de.max_lo.mensareloaded.database.entity.Offer;

public class MealRepository {

    private MealDao mealDao;
    private OfferDao offerDao;

    private LiveData<List<Meal>> meals;

    public MealRepository(Application application) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        mealDao = db.mealDao();
        offerDao = db.offerDao();
    }

    public LiveData<List<Meal>> getMeals(Mensa mensa, long date) {
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
            mAsyncTaskDao.insert(params[0]);
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
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
