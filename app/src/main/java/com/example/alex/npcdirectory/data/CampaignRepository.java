package com.example.alex.npcdirectory.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class CampaignRepository {

    private CampaignDao mCampaignDao;
    private LiveData<List<Campaign>> mAllCampaigns;

    public CampaignRepository(Application application)
    {
        NPCRoomDatabase db = NPCRoomDatabase.getDatabase(application);
        mCampaignDao = db.campaignDao();
        mAllCampaigns = mCampaignDao.getAllCampaigns();
    }

    public LiveData<List<Campaign>> getAllCampaigns() {
        return mAllCampaigns;
    }

    public void insert(Campaign campaign)
    {
        new insertAsyncTask(mCampaignDao).execute(campaign);
    }

    private static class insertAsyncTask extends AsyncTask<Campaign, Void, Void> {

        private CampaignDao mAsyncTaskDao;

        insertAsyncTask(CampaignDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Campaign... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void update(Campaign campaign)
    {
        new updateAsyncTask(mCampaignDao).execute(campaign);
    }

    private static class updateAsyncTask extends AsyncTask<Campaign, Void, Void> {

        private CampaignDao mAsyncTaskDao;

        updateAsyncTask(CampaignDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Campaign... params) {
            mAsyncTaskDao.update(params[0]);
            return null;
        }
    }
}
