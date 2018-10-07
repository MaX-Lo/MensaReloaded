package de.max_lo.mensareloaded.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.time.LocalDate;

import de.max_lo.mensareloaded.Mensa;
import de.max_lo.mensareloaded.database.dao.MealDao;
import de.max_lo.mensareloaded.database.dao.OfferDao;
import de.max_lo.mensareloaded.database.entity.Meal;
import de.max_lo.mensareloaded.database.entity.Offer;


@Database(entities = {Meal.class, Offer.class}, version = 4)
@TypeConverters({MealTypeConverters.class})
public abstract class AppRoomDatabase extends RoomDatabase {

    public abstract MealDao mealDao();
    public abstract OfferDao offerDao();

    private static volatile AppRoomDatabase INSTANCE;

    public static AppRoomDatabase getDatabase(final Context context) {
        // make sure AppRoomDatabase is a singleton
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDatabase.class, "word_database")
                            .addCallback(appDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback appDatabaseCallback =
            new RoomDatabase.Callback() {
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final MealDao mealDao;
        private final OfferDao offerDao;

        PopulateDbAsync(AppRoomDatabase db) {
            mealDao = db.mealDao();
            offerDao = db.offerDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mealDao.deleteAll();
            Meal meal = new Meal("1", "Pizza", null, "2.5 Euro", "Chimneyfresh");
            mealDao.insert(meal);
            meal = new Meal("2", "Spaghetti Bolognese", null, "2.8 Euro", "Pasta");
            mealDao.insert(meal);

            long date = LocalDate.now().toEpochDay();

            offerDao.deleteAll();
            Offer offer = new Offer(date, Mensa.ALTE_MENSA, "1");
            offerDao.insert(offer);
            offer = new Offer(date, Mensa.ALTE_MENSA, "2");
            offerDao.insert(offer);
            return null;
        }
    }
}
