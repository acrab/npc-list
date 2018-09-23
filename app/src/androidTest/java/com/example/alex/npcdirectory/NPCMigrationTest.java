package com.example.alex.npcdirectory;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.framework.FrameworkSQLiteOpenHelperFactory;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.testing.MigrationTestHelper;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.alex.npcdirectory.data.NPC;
import com.example.alex.npcdirectory.data.NPCRoomDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static com.example.alex.npcdirectory.data.NPCRoomDatabase.MIGRATION_1_2;

@RunWith(AndroidJUnit4.class)
public class NPCMigrationTest {

    private final String TEST_DB_NAME = "Test_NPC_Database.db";

    @Rule
    public MigrationTestHelper mMigrationTestHelper =
            new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
                    NPCRoomDatabase.class.getCanonicalName(),
                    new FrameworkSQLiteOpenHelperFactory());

    @Test
    public void migrationFrom1To2_containsCorrectData() throws IOException
    {
        //Create v1 database
        SupportSQLiteDatabase db = mMigrationTestHelper.createDatabase(TEST_DB_NAME, 1);
        ContentValues values = new ContentValues();
        values.put("id", 1);
        values.put("name", "Bob");
        db.insert("NPCs", SQLiteDatabase.CONFLICT_REPLACE, values);
        db.close();
        //Migrate to v2
        mMigrationTestHelper.runMigrationsAndValidate(TEST_DB_NAME, 2, true, MIGRATION_1_2);

        //Reopen as a V2 database
        NPCRoomDatabase database = Room.databaseBuilder(InstrumentationRegistry.getTargetContext(),
                NPCRoomDatabase.class, TEST_DB_NAME)
                .addMigrations(MIGRATION_1_2)
                .build();
//Can't test live data, it only updates when it's observed, and the test needs to happen synchronously.
//        LiveData<List<NPC>> dao = database.npcDao().getAllNPCs();
//        assertNotNull(dao);
//        List<NPC> data = dao.getValue();
//        assertNotNull(data);

        List<NPC> data = database.npcDao().getAllNPCsNonLive();

        NPC bob = data.get(0);
        assertNotNull(bob);
        //Data existed in v1, expect it to still be there.
        assertEquals(1, bob.getId());
        assertEquals("Bob", bob.getName());
        //Descriptions were added in v2, so expect them to be empty.
        assertEquals("", bob.getDescription());

        // close the database and release any stream resources when the test finishes
        mMigrationTestHelper.closeWhenFinished(database);

    }
}
