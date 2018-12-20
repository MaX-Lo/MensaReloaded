package de.max_lo.mensareloaded.helper;

import android.util.Log;

import de.max_lo.mensareloaded.database.entity.Meal;

public class MealHelper {
    public static Meal fixAPIInconsistencies(Meal meal) {
        if (meal.getDescription().contains("Pizza")) {
            meal.setCategory("Pizza");
            meal.setStudentPrice("1,77€ / 2,66€");
        }
        if (meal.getDescription().contains("Hausgemachte frische Pasta")) {
            meal.setCategory("Hausgemachte Pasta");
            meal.setDescription(meal.getDescription().replace("Hausgemachte frische Pasta, heute ", ""));
        }
        if (meal.getCategory().equals("Pasta")) {
            meal.setStudentPrice("1,67€ / 2,36€");
        }

        Log.d("bla", "wrong price:" + meal.getStudentPrice());
        if (meal.getCategory().equals("Terrine") && meal.getStudentPrice().contains("0.0")) {
            meal.setStudentPrice("1,67€");
        }

        if (meal.getCategory().equals("Auflauf") && meal.getStudentPrice().contains("0.0")) {
            meal.setStudentPrice("1.97€");
        }

        return meal;
    }

    /**
     * @param oldPriceFormat x.x
     * @return new price format x.xx
     */
    public static String formatPricing(String oldPriceFormat) {
        if (oldPriceFormat.charAt(oldPriceFormat.length() - 2) == '.') {
            return oldPriceFormat + "0€";
        } else {
            return oldPriceFormat + "€";
        }
    }
}
