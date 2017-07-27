package com.lavamy.clashbasedesigner.adapter.townhall;

import android.content.Context;
import android.os.AsyncTask;

import com.lavamy.clashbasedesigner.manager.SettingsManager;
import com.lavamy.clashbasedesigner.manager.TownHallManager;
import com.lavamy.clashbasedesigner.model.TownHall;

public class TownHallAsyncTask extends AsyncTask<Void, TownHall, Void> {

    private TownHallListViewAdapter townHallListViewAdapter;
    private Context context;

    public TownHallAsyncTask(Context context, TownHallListViewAdapter townHallListViewAdapter) {
        this.townHallListViewAdapter = townHallListViewAdapter;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        townHallListViewAdapter.clear();
        townHallListViewAdapter.notifyDataSetChanged();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        for (int i = 0; i < TownHallManager.getInstance().townHalls.size(); i++) {
            TownHall townHall = TownHallManager.getInstance().townHalls.get(i);
            if (SettingsManager.getInstance(context).getTownHallCurrent() == i) {
                townHall.setSelected(true);
            }
            publishProgress(townHall);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(TownHall... values) {
        townHallListViewAdapter.addTownHall(values[0]);
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void result) {
        townHallListViewAdapter.notifyDataSetChanged();
        super.onPostExecute(result);
    }
}