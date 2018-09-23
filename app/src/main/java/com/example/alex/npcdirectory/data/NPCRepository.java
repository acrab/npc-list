package com.example.alex.npcdirectory.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NPCRepository {

    private NPCDao mNPCDao;
    private LiveData<List<NPC>> mAllNPCs;

    public NPCRepository(Application application)
    {
        NPCRoomDatabase db = NPCRoomDatabase.getDatabase(application);
        mNPCDao = db.npcDao();
        mAllNPCs = mNPCDao.getAllNPCs();
    }

    public LiveData<List<NPC>> getAllNPCs()
    {
        return mAllNPCs;
    }

    public void insert(NPC npc)
    {
        new insertAsyncTask(mNPCDao).execute(npc);
    }

    private static class insertAsyncTask extends AsyncTask<NPC, Void, Void> {

        private NPCDao mAsyncTaskDao;

        insertAsyncTask(NPCDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final NPC... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void update(NPC npc)
    {
        new updateAsyncTask(mNPCDao).execute(npc);
    }

    private static class updateAsyncTask extends AsyncTask<NPC, Void, Void> {

        private NPCDao mAsyncTaskDao;

        updateAsyncTask(NPCDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final NPC... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
