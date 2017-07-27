package com.lavamy.clashbasedesigner.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.lavamy.clashbasedesigner.R;
import com.lavamy.clashbasedesigner.Utils.Utils;
import com.lavamy.clashbasedesigner.adapter.townhall.TownHallAsyncTask;
import com.lavamy.clashbasedesigner.adapter.townhall.TownHallListViewAdapter;
import com.lavamy.clashbasedesigner.fragment.tablayout.FarmingFragment;
import com.lavamy.clashbasedesigner.fragment.tablayout.HybridFragment;
import com.lavamy.clashbasedesigner.fragment.tablayout.TrophyFragment;
import com.lavamy.clashbasedesigner.fragment.tablayout.WarFragment;
import com.lavamy.clashbasedesigner.manager.Global;
import com.lavamy.clashbasedesigner.manager.SettingsManager;
import com.lavamy.clashbasedesigner.manager.TownHallManager;
import com.lavamy.clashbasedesigner.manager.ViewExtras;
import com.melnykov.fab.FloatingActionButton;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import static com.lavamy.clashbasedesigner.R.id.title_text;
import static com.lavamy.clashbasedesigner.R.id.viewPagerTab;
import static com.lavamy.clashbasedesigner.manager.Global.currentPage;


public class CollectionActivity extends AppCompatActivity {

    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;
    private FragmentPagerItemAdapter adapter;
    private ListView townHallListView;
    private FloatingActionButton floatingActionButton;
    private RelativeLayout abstractRL;
    private TextView titleTVBar;
    private AdView mAdView;
    private AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_collection);

        Global.currentPage = 0;

        init();
        createActionBar();

        mAdView = (AdView) findViewById(R.id.adView);
        adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdClosed() {

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            }

            @Override
            public void onAdLeftApplication() {
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdOpened() {
                mAdView.setVisibility(View.VISIBLE);
            }
        });
        mAdView.loadAd(adRequest);

    }

    private void init() {
        //init tab layout
        adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.war, WarFragment.class)
                .add(R.string.trophy, TrophyFragment.class)
                .add(R.string.farming, FarmingFragment.class)
                .add(R.string.hybrid, HybridFragment.class)
                .create());

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(3);
        smartTabLayout = (SmartTabLayout) findViewById(viewPagerTab);

        final LayoutInflater inflater = LayoutInflater.from(this);
        smartTabLayout.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                View itemView = inflater.inflate(R.layout.custom_tab, container, false);
                TextView text = (TextView) itemView.findViewById(R.id.custom_tab_text);
                Typeface type = Typeface.createFromAsset(getAssets(),"Font/supercell-magic.ttf");
                text.setTypeface(type);
                text.setText(adapter.getPageTitle(position));
                ImageView icon = (ImageView) itemView.findViewById(R.id.custom_tab_icon);
                switch (position) {
                    case 0:
                        text.setText(R.string.war);
                        icon.setImageDrawable(ViewExtras.getDrawable(CollectionActivity.this, R.drawable.ic_tab_war_selected));
                        break;
                    case 1:
                        text.setText(R.string.trophy);
                        icon.setImageDrawable(ViewExtras.getDrawable(CollectionActivity.this, R.drawable.ic_tab_trophy_selected));
                        break;
                    case 2:
                        text.setText(R.string.farming);
                        icon.setImageDrawable(ViewExtras.getDrawable(CollectionActivity.this, R.drawable.ic_tab_farming_selected));
                        break;
                    case 3:
                        text.setText(R.string.hybrid);
                        icon.setImageDrawable(ViewExtras.getDrawable(CollectionActivity.this, R.drawable.ic_tab_hybrid_selected));
                        break;
                    default:
                        throw new IllegalStateException("Invalid position: " + position);
                }

                return itemView;
            }
        });

        smartTabLayout.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageScrollStateChanged(int state) { }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                updatePager();
            }
        });

        viewPager.setAdapter(adapter);
        smartTabLayout.setViewPager(viewPager);

        //init town hall list for floating action button
        townHallListView = (ListView) findViewById(R.id.townHallLV);
        final TownHallListViewAdapter townHallListViewAdapter = new TownHallListViewAdapter(this);
        townHallListView.setAdapter(townHallListViewAdapter);
        townHallListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                TownHallManager.getInstance().resetData();
                TownHallManager.getInstance().townHalls.get(position).setSelected(true);
                townHallListViewAdapter.notifyDataSetChanged();
                SettingsManager.getInstance(CollectionActivity.this).setTownHallCurrent(position);
                hideTownHallPopup();
                updateData();
                titleTVBar.setText(TownHallManager.getInstance().townHalls.get(position).getTitle());
                titleTVBar.setText(getString(R.string.town_hall) + " " + String.valueOf(SettingsManager.getInstance(CollectionActivity.this).getTownHallCurrent() + 1));
            }
        });
        TownHallAsyncTask asyncTask = new TownHallAsyncTask(this, townHallListViewAdapter);
        asyncTask.execute();

        //init floating action button
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!townHallListView.isShown()) {
                    showTownHallPopup();
                }
            }
        });

        //abstract view is activated when town hall list view is showed
        abstractRL = (RelativeLayout)findViewById(R.id.abstractRL);
        abstractRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (townHallListView.isShown()) {
                    hideTownHallPopup();
                }
            }
        });
    }

    private void hideTownHallPopup() {
        townHallListView.setVisibility(View.GONE);
        abstractRL.setVisibility(View.GONE);
        floatingActionButton.show();
    }

    private void showTownHallPopup() {
        townHallListView.setVisibility(View.VISIBLE);
        abstractRL.setVisibility(View.VISIBLE);
        floatingActionButton.hide();
    }

    private void createActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_collection);
        setSupportActionBar(toolbar);

        titleTVBar = (TextView) toolbar.findViewById(title_text);
        titleTVBar.setText(TownHallManager.getInstance().townHalls.get(SettingsManager.getInstance(this).getTownHallCurrent() + 1).getTitle());
        titleTVBar.setText(getString(R.string.town_hall) + " " + String.valueOf(SettingsManager.getInstance(this).getTownHallCurrent() + 1));
        Typeface type = Typeface.createFromAsset(getAssets(),"Font/supercell-magic.ttf");
        titleTVBar.setTypeface(type);

        final ImageView typeListImgView = (ImageView) toolbar.findViewById(R.id.typeListImgView);

        RelativeLayout typeListRL = (RelativeLayout) toolbar.findViewById(R.id.typeListRL);
        typeListRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int typeList = SettingsManager.getInstance(CollectionActivity.this).getTypeList();
                if (typeList == 0) {
                    SettingsManager.getInstance(CollectionActivity.this).setTypeList(1);
                    typeListImgView.setImageDrawable(ViewExtras.getDrawable(CollectionActivity.this, R.drawable.ic_action_list));
                }
                else {
                    SettingsManager.getInstance(CollectionActivity.this).setTypeList(0);
                    typeListImgView.setImageDrawable(ViewExtras.getDrawable(CollectionActivity.this, R.drawable.ic_action_grid));
                }

                updatePager();
            }
        });

        int typeList = SettingsManager.getInstance(CollectionActivity.this).getTypeList();
        if (typeList == 0) {
            typeListImgView.setImageDrawable(ViewExtras.getDrawable(CollectionActivity.this, R.drawable.ic_action_grid));
        }
        else {
            typeListImgView.setImageDrawable(ViewExtras.getDrawable(CollectionActivity.this, R.drawable.ic_action_list));
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void updatePager() {
        Fragment fragment = adapter.getPage(Global.currentPage);

        if (Global.currentPage == 0) {
            ((WarFragment)fragment).updateUI();
        }
        else if  (Global.currentPage == 1) {
            ((TrophyFragment)fragment).updateUI();
        }
        else if  (Global.currentPage == 2) {
            ((FarmingFragment)fragment).updateUI();
        }
        else if  (Global.currentPage == 3) {
            ((HybridFragment)fragment).updateUI();
        }
    }

    private void updateData() {
        WarFragment warFragment = (WarFragment)adapter.getPage(0);
        TrophyFragment trophyFragment = (TrophyFragment)adapter.getPage(1);
        FarmingFragment farmingFragment = (FarmingFragment)adapter.getPage(2);
        HybridFragment hybridFragment = (HybridFragment)adapter.getPage(3);

        warFragment.loadData();
        trophyFragment.loadData();
        farmingFragment.loadData();
        hybridFragment.loadData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null && Utils.isConnectNetWork(this)) {
            mAdView.resume();
            mAdView.loadAd(adRequest);
            mAdView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
