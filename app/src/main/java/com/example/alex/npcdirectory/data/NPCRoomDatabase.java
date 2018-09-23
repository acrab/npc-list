package com.example.alex.npcdirectory.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {NPC.class}, version = 2)
public abstract class NPCRoomDatabase extends RoomDatabase {

    public abstract NPCDao npcDao();

    private static NPCRoomDatabase INSTANCE;

    public static NPCRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NPCRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NPCRoomDatabase.class, "npc_database")
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE NPCs " +
                    "ADD COLUMN description TEXT NOT NULL DEFAULT ''");
        }
    };
}
