package de.max_lo.mensareloaded.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

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
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
