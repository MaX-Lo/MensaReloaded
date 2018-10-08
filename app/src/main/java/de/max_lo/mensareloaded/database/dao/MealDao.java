package de.max_lo.mensareloaded.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import de.max_lo.mensareloaded.Mensa;
import de.max_lo.mensareloaded.database.entity.Meal;

@Dao
public interface MealDao {

    @Insert
    void insert(Meal meal);

    @Query("DELETE FROM meal_table")
    void deleteAll();

    @Query("SELECT * FROM meal_table " +
            "INNER JOIN offer_table ON meal_table.id = offer_table.mealId " +
            "WHERE offer_table.mensa = :mensa AND offer_table.date = :date")
    LiveData<List<Meal>> getMeals(Mensa mensa, long date);

    @Query("SELECT * FROM meal_table WHERE meal_table.id = :mealId")
    Meal getMeal(String mealId);
}
