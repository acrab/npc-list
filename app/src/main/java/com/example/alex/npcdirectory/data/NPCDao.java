package com.example.alex.npcdirectory.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NPCDao {

    @Insert
    void insert(NPC npc);

    @Update
    void update(NPC npc);

    @Query("DELETE FROM NPCs")
    void deleteAll();

    @Query("SELECT * from NPCs ORDER BY name ASC")
    LiveData<List<NPC>> getAllNPCs();

    @Query("SELECT * from NPCs ORDER BY name ASC")
    List<NPC> getAllNPCsNonLive();
}
