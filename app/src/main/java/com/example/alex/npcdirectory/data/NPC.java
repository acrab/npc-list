package com.example.alex.npcdirectory.data;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "NPCs")
public class NPC {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "name")
    private String mName = "";

    @NonNull
    @ColumnInfo(name = "description")
    private String mDescription = "";

    public NPC(@NonNull String name, @NonNull String description, int id) {
        this.mName = name;
        this.mDescription = description;
        this.id = id;
    }

    @Ignore
    public NPC(@NonNull String name, @NonNull String description) {
        this.mName = name;
        this.mDescription = description;
    }

    public String getName() {return mName;}

    public String getDescription() {return mDescription;}

    public int getId() {return id;}

}
