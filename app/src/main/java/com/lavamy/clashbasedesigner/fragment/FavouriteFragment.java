package com.lavamy.clashbasedesigner.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.lavamy.clashbasedesigner.R;
import com.lavamy.clashbasedesigner.Utils.TownHallInterface;
import com.lavamy.clashbasedesigner.activity.CollectionDetailActivity;
import com.lavamy.clashbasedesigner.adapter.CollectionGridViewAdapter;
import com.lavamy.clashbasedesigner.adapter.CollectionListViewAdapter;
import com.lavamy.clashbasedesigner.adapter.favourite.FavouriteAsyncTask;
import com.lavamy.clashbasedesigner.manager.Global;
import com.lavamy.clashbasedesigner.manager.SettingsManager;
import com.lavamy.clashbasedesigner.manager.ViewExtras;

public class FavouriteFragment extends Fragment {

    private ListView listView;
    private GridView gridView;
    private CollectionListViewAdapter collectionListViewAdapter;
    private CollectionGridViewAdapter collectionGridViewAdapter;
    private InterstitialAd mInterstitialAd;
    private int imagePosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);

        Global.currentPage = 4;
        initView(view);
        initToolBar();

        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
            }

            @Override
            public void onAdClosed() {
                goToNextFullViewPage();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FavouriteAsyncTask asyncTask = new FavouriteAsyncTask(collectionListViewAdapter, collectionGridViewAdapter);
        asyncTask.execute();
    }

    private void initToolBar() {
        Toolbar toolbar_main = (Toolbar) getActivity().findViewById(R.id.toolbar_main);
        TextView title_text = (TextView) toolbar_main.findViewById(R.id.title_text);
        title_text.setText(getString(R.string.favourite));

        final ImageView updateBtn = (ImageView) toolbar_main.findViewById(R.id.updateBtn);
        updateBtn.setVisibility(View.VISIBLE);
        RelativeLayout updateRL = (RelativeLayout) toolbar_main.findViewById(R.id.updateRL);
        updateRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int typeList = SettingsManager.getInstance(getContext()).getTypeList();
                if (typeList == 0) {
                    SettingsManager.getInstance(getContext()).setTypeList(1);
                    updateBtn.setImageDrawable(ViewExtras.getDrawable(getContext(), R.drawable.ic_action_list));
                    gridView.setVisibility(View.VISIBLE);
                    gridView.setSelection(0);
                    listView.setVisibility(View.GONE);
                }
                else {
                    SettingsManager.getInstance(getContext()).setTypeList(0);
                    updateBtn.setImageDrawable(ViewExtras.getDrawable(getContext(), R.drawable.ic_action_grid));
                    gridView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    listView.setSelectionAfterHeaderView();
                }
            }
        });

        int typeList = SettingsManager.getInstance(getContext()).getTypeList();
        if (typeList == 0) {
            updateBtn.setImageDrawable(ViewExtras.getDrawable(getContext(), R.drawable.ic_action_grid));
            gridView.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        }
        else {
            updateBtn.setImageDrawable(ViewExtras.getDrawable(getContext(), R.drawable.ic_action_list));
            gridView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
    }

    private void initView(View view) {

        //init ListView
        listView = (ListView) view.findViewById(R.id.favouriteLV);
        collectionListViewAdapter = new CollectionListViewAdapter(getContext(), new FavouriteInterfaceClass(), new TownHallInterfaceClass());
        listView.setAdapter(collectionListViewAdapter);


        //init GridView
        gridView = (GridView) view.findViewById(R.id.favouriteGV);
        collectionGridViewAdapter = new CollectionGridViewAdapter(getContext(), new FavouriteInterfaceClass(), new TownHallInterfaceClass());
        gridView.setAdapter(collectionGridViewAdapter);

    }

    private class FavouriteInterfaceClass implements FavouriteInterface {
        @Override
        public void favouriteClicked(int position) {
            FavouriteAsyncTask asyncTask = new FavouriteAsyncTask(collectionListViewAdapter, collectionGridViewAdapter);
            asyncTask.execute();
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

    public interface FavouriteInterface {
        void favouriteClicked(int position);
    }
}
