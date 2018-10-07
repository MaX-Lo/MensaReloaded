package de.max_lo.mensareloaded.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import android.support.annotation.NonNull;

import java.time.LocalDate;
import java.util.List;

import de.max_lo.mensareloaded.Mensa;
import de.max_lo.mensareloaded.database.entity.Meal;
import de.max_lo.mensareloaded.repository.MealRepository;

public class MealsViewModel extends AndroidViewModel {

    private MealRepository mealRepository;
    private LiveData<List<Meal>> meals;
    private Mensa mensa;
    private long date;

    public MealsViewModel(@NonNull Application application) {
        super(application);

        mealRepository = new MealRepository(application);

        mensa = Mensa.ALTE_MENSA;
        date = LocalDate.now().toEpochDay();

        refreshMeals();
    }

    public LiveData<List<Meal>> getMeals() {
        return meals;
    }

    public void setMensa(Mensa newMensa) {
        this.mensa = newMensa;
        refreshMeals();
    }

    private void refreshMeals() {
        meals = mealRepository.getMeals(mensa, date);
    }
}
