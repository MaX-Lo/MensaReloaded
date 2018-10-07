package de.max_lo.mensareloaded.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import de.max_lo.mensareloaded.Mensa;

public class MealTypeConverters {

    @TypeConverter
    public static List stringToObjectList(String data) {
        Gson gson = new Gson();
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String objectListToString(List objectList) {
        Gson gson = new Gson();
        return gson.toJson(objectList);
    }

    @TypeConverter
    public static String mensaToString(Mensa mensa) {
        return mensa.toString();
    }

    @TypeConverter
    public static Mensa stringToMensa(String string) {
        return Mensa.valueOf(string);
    }


}
