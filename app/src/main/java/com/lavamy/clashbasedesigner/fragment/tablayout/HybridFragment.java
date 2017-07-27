package com.lavamy.clashbasedesigner.fragment.tablayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.lavamy.clashbasedesigner.R;
import com.lavamy.clashbasedesigner.Utils.TownHallInterface;
import com.lavamy.clashbasedesigner.activity.CollectionDetailActivity;
import com.lavamy.clashbasedesigner.adapter.CollectionGridViewAdapter;
import com.lavamy.clashbasedesigner.adapter.CollectionListViewAdapter;
import com.lavamy.clashbasedesigner.adapter.hybrid.HybridAsyncTask;
import com.lavamy.clashbasedesigner.fragment.FavouriteFragment.FavouriteInterface;
import com.lavamy.clashbasedesigner.manager.SettingsManager;

public class HybridFragment extends Fragment {

    private ListView listView;
    private GridView gridView;
    private CollectionListViewAdapter collectionListViewAdapter;
    private CollectionGridViewAdapter collectionGridViewAdapter;
    private InterstitialAd mInterstitialAd;
    private int imagePosition = 0;
    private AdRequest adRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hybrid, container, false);

        initView(view);

        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
        adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {

            }

            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(adRequest);
                goToNextFullViewPage();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        collectionListViewAdapter.notifyDataSetChanged();
        collectionGridViewAdapter.notifyDataSetChanged();
    }

    private void initView(View view) {
        //init ListView
        listView = (ListView) view.findViewById(R.id.hybridLV);
        collectionListViewAdapter = new CollectionListViewAdapter(getContext(), new FavouriteInterfaceClass(), new TownHallInterfaceClass());
        listView.setAdapter(collectionListViewAdapter);

        //init GridView
        gridView = (GridView) view.findViewById(R.id.hybridGV);
        collectionGridViewAdapter = new CollectionGridViewAdapter(getContext(), new FavouriteInterfaceClass(), new TownHallInterfaceClass());
        gridView.setAdapter(collectionGridViewAdapter);

        loadData();
        updateUI();
    }

    public void loadData() {
        int townHallCurrent = SettingsManager.getInstance(getContext()).getTownHallCurrent();
        HybridAsyncTask asyncTask = new HybridAsyncTask(getContext(), "images/hybrid/" + String.valueOf(townHallCurrent + 1), collectionListViewAdapter, collectionGridViewAdapter);
        asyncTask.execute();
    }

    public void updateUI() {
        int typeList = SettingsManager.getInstance(getContext()).getTypeList();
        if (typeList == 0) {
            listView.setSelectionAfterHeaderView();
            listView.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
        }
        else {
            gridView.setSelection(0);
            listView.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);

        }
    }

    private class FavouriteInterfaceClass implements FavouriteInterface {
        @Override
        public void favouriteClicked(int position) {
            collectionListViewAdapter.notifyDataSetChanged();
            collectionGridViewAdapter.notifyDataSetChanged();
        }
    }

    private class TownHallInterfaceClass implements TownHallInterface {
        @Override
        public void imageClicked(int position) {
            imagePosition = position;
            //choose position if you like (0,2, 5)
            if (position == 0 || position == 2 || position == 5) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    goToNextFullViewPage();
                }
            } else {
                goToNextFullViewPage();
            }
        }
    }

    private void goToNextFullViewPage() {
        Intent intent = new Intent(getContext(), CollectionDetailActivity.class);
        intent.putExtra("position", imagePosition);
        getContext().startActivity(intent);
    }
}
