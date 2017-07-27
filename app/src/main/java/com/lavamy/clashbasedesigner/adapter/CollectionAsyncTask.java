package com.lavamy.clashbasedesigner.adapter;

import android.content.Context;
import android.os.AsyncTask;

import com.lavamy.clashbasedesigner.Utils.Utils;
import com.lavamy.clashbasedesigner.model.Collection;

public class CollectionAsyncTask extends AsyncTask<Void, Collection, Void> {

    private CollectionListViewAdapter collectionListViewAdapter;
    private CollectionGridViewAdapter collectionGridViewAdapter;
    private Context context;
    private String directory;

    public CollectionAsyncTask(Context context, String directory, CollectionListViewAdapter collectionListViewAdapter, CollectionGridViewAdapter collectionGridViewAdapter) {
        this.collectionListViewAdapter = collectionListViewAdapter;
        this.collectionGridViewAdapter = collectionGridViewAdapter;
        this.context = context;
        this.directory = directory;
    }

    @Override
    protected void onPreExecute() {
        collectionListViewAdapter.clear();
        collectionListViewAdapter.notifyDataSetChanged();

        collectionGridViewAdapter.clear();
        collectionGridViewAdapter.notifyDataSetChanged();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        for (String path : Utils.listFiles(directory, context)) {
            Collection collection = new Collection(directory + "/" + path, false);
            publishProgress(collection);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Collection... values) {
        collectionListViewAdapter.addCollection(values[0]);
        collectionGridViewAdapter.addCollection(values[0]);
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void result) {
        collectionListViewAdapter.notifyDataSetChanged();
        collectionGridViewAdapter.notifyDataSetChanged();

        super.onPostExecute(result);
    }
}
