package de.max_lo.mensareloaded.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import de.max_lo.mensareloaded.Mensa;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "offer_table", foreignKeys = @ForeignKey(entity = Meal.class,
            parentColumns = "id", childColumns = "mealId", onDelete = CASCADE))
public class Offer {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private long date;

    private Mensa mensa;

    private String mealId;

    public Offer(long date, Mensa mensa, @NonNull String mealId) {
        this.date = date;
        this.mensa = mensa;
        this.mealId = mealId;
    }

    public int getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

    public Mensa getMensa() {
        return mensa;
    }

    public String getMealId() {
        return mealId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setMensa(Mensa mensa) {
        this.mensa = mensa;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }
}
