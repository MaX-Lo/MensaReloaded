package de.max_lo.mensareloaded.database.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

@Entity(tableName = "meal_table")
public class Meal {

    @PrimaryKey
    @NonNull
    private String id;

    private String description;

    private List<String> notes;

    private String studentPrice;

    private String category;

    public Meal(@NonNull String id, String description, List<String> notes,
                String studentPrice, String category) {
        this.id = id;
        this.description = description;
        this.notes = notes;
        this.studentPrice = studentPrice;
        this.category = category;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getNotes() {
        return notes;
    }

    public String getCategory() {
        return category;
    }

    public String getStudentPrice() {
        return studentPrice;
    }
}
