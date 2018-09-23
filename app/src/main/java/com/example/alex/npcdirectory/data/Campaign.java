package com.example.alex.npcdirectory.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "Campaigns")
public class Campaign {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String mName = "";

    public Campaign(int id, @NonNull String mName) {
        this.id = id;
        this.mName = mName;
    }

    @Ignore
    public Campaign(@NonNull String mName) {
        this.mName = mName;
    }

    public String getName() {return mName;}

    public int getId() {return id;}
}
