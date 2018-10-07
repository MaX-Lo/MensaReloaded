package de.max_lo.mensareloaded.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import de.max_lo.mensareloaded.database.entity.Offer;

@Dao
public interface OfferDao {
    @Insert
    void insert(Offer offer);

    @Query("DELETE FROM offer_table")
    void deleteAll();
}
