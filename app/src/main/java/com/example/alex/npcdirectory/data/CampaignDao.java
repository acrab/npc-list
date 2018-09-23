package com.example.alex.npcdirectory.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CampaignDao {

    @Insert
    void insert(Campaign campaign);

    @Update
    void update(Campaign campaign);

    @Query("SELECT * from Campaigns ORDER BY name ASC")
    LiveData<List<Campaign>> getAllCampaigns();

    @Query("SELECT * from Campaigns ORDER BY name ASC")
    List<Campaign> getAllCampaignsNonLive();
}
