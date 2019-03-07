package de.max_lo.mensareloaded.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import de.max_lo.mensareloaded.Mensa;
import de.max_lo.mensareloaded.database.entity.Offer;

@Dao
public interface OfferDao {
    @Insert
    void insert(Offer offer);

    @Query("DELETE FROM offer_table")
    void deleteAll();

    @Query("SELECT * FROM offer_table WHERE mensa = :mensa AND date = :date AND mealId = :mealId")
    Offer getOffer(Mensa mensa, long date, String mealId);

    @Query("SELECT * FROM offer_table WHERE mensa = :mensa AND date = :date")
    List<Offer> getOffers(Mensa mensa, long date);
}
