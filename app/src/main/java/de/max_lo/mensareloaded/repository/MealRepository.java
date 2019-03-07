package de.max_lo.mensareloaded.repository;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import java.util.List;

import de.max_lo.mensareloaded.Mensa;
import de.max_lo.mensareloaded.networking.NetworkController;
import de.max_lo.mensareloaded.networking.NetworkListener;
import de.max_lo.mensareloaded.database.AppRoomDatabase;
import de.max_lo.mensareloaded.database.dao.MealDao;
import de.max_lo.mensareloaded.database.dao.OfferDao;
import de.max_lo.mensareloaded.database.entity.Meal;
import de.max_lo.mensareloaded.database.entity.Offer;

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

    public LiveData<List<Meal>> getMeals(Mensa mensa, long dateAsDaysFromEpoch) {
        checkForExistingMealData(mensa, dateAsDaysFromEpoch);

        return mealDao.getMeals(mensa, dateAsDaysFromEpoch);
    }

    private void fetchMeals(Mensa mensa, long daysSinceEpoch) {
        nc.fetchMeals(mensa, daysSinceEpoch);
    }

    private void insertMeal(Meal meal) {
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

    private void insertOffer(Offer offer) {
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

    private void checkForExistingMealData(Mensa mensa, long date) {
        Pair<Mensa, Long> params = new Pair<>(mensa, date);
        try {
            boolean dataFromDBAvailable= new checkMealDataAsyncTask(offerDao).execute(params).get();
            if (!dataFromDBAvailable) {
                fetchMeals(mensa, date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class checkMealDataAsyncTask extends AsyncTask<Pair<Mensa, Long>, Void, Boolean> {
        private OfferDao mAsyncOfferDao;

        checkMealDataAsyncTask(OfferDao offerDao) {
            mAsyncOfferDao = offerDao;
        }

        @Override
        protected Boolean doInBackground(Pair<Mensa, Long>... params) {
            return mAsyncOfferDao.getOffers(params[0].first, params[0].second).size() > 0;
        }
    }
}
