package de.max_lo.mensareloaded.networking;

import java.util.List;

import de.max_lo.mensareloaded.Mensa;
import de.max_lo.mensareloaded.database.entity.Meal;

public interface NetworkListener {
    void onReceivedMeals(Mensa mensa, long date, List<Meal> mealsRetro);
}
