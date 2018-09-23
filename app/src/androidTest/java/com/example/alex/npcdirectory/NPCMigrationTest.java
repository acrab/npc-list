package com.example.alex.npcdirectory;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.testing.MigrationTestHelper;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.alex.npcdirectory.data.Campaign;
import com.example.alex.npcdirectory.data.NPC;
import com.example.alex.npcdirectory.data.NPCRoomDatabase;

import org.junit.Rule;
import org.junit.Test;

import static com.example.alex.npcdirectory.data.NPCRoomDatabase.MIGRATION_1_3;
import static com.example.alex.npcdirectory.data.NPCRoomDatabase.MIGRATION_2_3;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class NPCMigrationTest {

    private final String TEST_DB_NAME = "Test_NPC_Database.db";

    @Rule
    public MigrationTestHelper mMigrationTestHelper =
            new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
                    NPCRoomDatabase.class.getCanonicalName(),
                    new FrameworkSQLiteOpenHelperFactory());

    @Test
    public void migrationFrom2To3_createsTable() throws IOException
    {
        //Create v2 database
        SupportSQLiteDatabase db = mMigrationTestHelper.createDatabase(TEST_DB_NAME, 2);
        ContentValues values = new ContentValues();
        values.put("id", 1);
        values.put("name", "Bob");
        values.put("description", "Test NPC");
        db.insert("NPCs", SQLiteDatabase.CONFLICT_REPLACE, values);
        db.close();

        mMigrationTestHelper.runMigrationsAndValidate(TEST_DB_NAME, 3, true, MIGRATION_2_3);

        NPCRoomDatabase database = Room.databaseBuilder(InstrumentationRegistry.getTargetContext(),
                NPCRoomDatabase.class, TEST_DB_NAME)
                .addMigrations(MIGRATION_1_3, MIGRATION_2_3)
                .build();

        List<NPC> npcs = database.npcDao().getAllNPCsNonLive();

        NPC bob = npcs.get(0);
        assertNotNull(bob);
        //Data existed in v1, expect it to still be there.
        assertEquals(1, bob.getId());
        assertEquals("Bob", bob.getName());
        assertEquals("Test NPC", bob.getDescription());

        //Table not present in v2, expect it to exist, but be empty.

        List<Campaign> campaigns = database.campaignDao().getAllCampaignsNonLive();
        assertEquals(0, campaigns.size());
    }

    @Test
    public void migrationFrom1To3_createsTableContainsData() throws IOException
    {
        //Create v1 database
        SupportSQLiteDatabase db = mMigrationTestHelper.createDatabase(TEST_DB_NAME, 1);
        ContentValues values = new ContentValues();
        values.put("id", 1);
        values.put("name", "Bob");
        db.insert("NPCs", SQLiteDatabase.CONFLICT_REPLACE, values);
        db.close();

        mMigrationTestHelper.runMigrationsAndValidate(TEST_DB_NAME, 3, true, MIGRATION_1_3, MIGRATION_2_3);

        NPCRoomDatabase database = Room.databaseBuilder(InstrumentationRegistry.getTargetContext(),
                NPCRoomDatabase.class, TEST_DB_NAME)
                .addMigrations(MIGRATION_1_3, MIGRATION_2_3)
                .build();

        List<NPC> npcs = database.npcDao().getAllNPCsNonLive();

        NPC bob = npcs.get(0);
        assertNotNull(bob);
        //Data existed in v1, expect it to still be there.
        assertEquals(1, bob.getId());
        assertEquals("Bob", bob.getName());
        //Descriptions were added in v2, so expect them to be empty.
        assertEquals("", bob.getDescription());

        //Table not present in v1, expect it to exist, but be empty.
        List<Campaign> campaigns = database.campaignDao().getAllCampaignsNonLive();
        assertEquals(0, campaigns.size());
    }
}
