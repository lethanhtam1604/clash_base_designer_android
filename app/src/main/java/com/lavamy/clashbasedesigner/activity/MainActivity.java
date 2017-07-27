package com.lavamy.clashbasedesigner.activity;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.lavamy.clashbasedesigner.R;
import com.lavamy.clashbasedesigner.Utils.Utils;
import com.lavamy.clashbasedesigner.fragment.FavouriteFragment;
import com.lavamy.clashbasedesigner.fragment.HomeFragment;
import com.lavamy.clashbasedesigner.fragment.MoreFragment;
import com.lavamy.clashbasedesigner.fragment.SettingFragment;
import com.lavamy.clashbasedesigner.manager.FavouriteManager;
import com.lavamy.clashbasedesigner.manager.Global;
import com.lavamy.clashbasedesigner.manager.SettingsManager;
import com.mikepenz.iconics.context.IconicsContextWrapper;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PrimaryDrawerItem homeItem, favouriteItem, shareItem, rateItem, settingItem, moreItem;
    private AccountHeader mDrawerHeader;
    private Drawer mDrawer;

    private Toolbar toolbar_home;
    private AdView mAdView;
    private AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FavouriteManager.getInstance().collections = new ArrayList<>(SettingsManager.getInstance(this).getFavouriteList());

        mAdView = (AdView) findViewById(R.id.adView);
        adRequest = new AdRequest.Builder()
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

            }

            @Override
            public void onAdOpened() {
                mAdView.setVisibility(View.VISIBLE);
            }
        });
        mAdView.loadAd(adRequest);

        begin();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    private void begin() {
        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        Global.screenWidth = size.x;
        Global.screenHeight = size.y;
        Global.screenRatio = (float) size.x / size.y;

        initialize();
        InitializeDrawerMenu();
    }

    public void initialize() {
        createActionBar();
    }

    private void createActionBar() {
        toolbar_home = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar_home);

        TextView title_text = (TextView) findViewById(R.id.title_text);
        Typeface type = Typeface.createFromAsset(getAssets(),"Font/supercell-magic.ttf");
        title_text.setTypeface(type);

        RelativeLayout icon_menu_RL = (RelativeLayout)toolbar_home.findViewById(R.id.icon_menu_RL);
        icon_menu_RL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer();
            }
        });
    }

    private void InitializeDrawerMenu() {

        homeItem = new PrimaryDrawerItem().withName("Home").withIcon(R.drawable.ic_home_white_24dp).withIdentifier(1)
                .withSelectedIconColorRes(R.color.white).withIconColorRes(R.color.colorMain).withSelectedColorRes(R.color.colorMain)
                .withSelectedTextColorRes(R.color.white).withIconTinted(true).withSelectable(true);

        favouriteItem = new PrimaryDrawerItem().withName("Favourite").withIcon(R.drawable.ic_favorite_black_24dp).withIdentifier(2)
                .withSelectedIconColorRes(R.color.white).withIconColorRes(R.color.colorMain).withSelectedColorRes(R.color.colorMain)
                .withSelectedTextColorRes(R.color.white).withIconTinted(true).withSelectable(true);

        shareItem = new PrimaryDrawerItem().withName("Share with Friends").withIcon(R.drawable.ic_share_black_24dp).withIdentifier(3)
                .withSelectedIconColorRes(R.color.white).withIconColorRes(R.color.colorMain).withSelectedColorRes(R.color.colorMain)
                .withSelectedTextColorRes(R.color.white).withIconTinted(true).withSelectable(false);

        rateItem = new PrimaryDrawerItem().withName("Rate Me").withIcon(R.drawable.ic_star_rate_black_18dp).withIdentifier(4)
                .withSelectedIconColorRes(R.color.white).withIconColorRes(R.color.colorMain).withSelectedColorRes(R.color.colorMain)
                .withSelectedTextColorRes(R.color.white).withIconTinted(true).withSelectable(false);

        settingItem = new PrimaryDrawerItem().withName("Settings").withIcon(R.drawable.ic_settings_white_24dp).withIdentifier(5)
                .withSelectedIconColorRes(R.color.white).withIconColorRes(R.color.colorMain).withSelectedColorRes(R.color.colorMain)
                .withSelectedTextColorRes(R.color.white).withIconTinted(true).withSelectable(true);

        moreItem = new PrimaryDrawerItem().withName("More").withIcon(R.drawable.ic_more_horiz_black_18dp).withIdentifier(6)
                .withSelectedIconColorRes(R.color.white).withIconColorRes(R.color.colorMain).withSelectedColorRes(R.color.colorMain)
                .withSelectedTextColorRes(R.color.white).withIconTinted(true).withSelectable(true);

        mDrawerHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(false)
                .withHeaderBackground(R.drawable.header)
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, final IProfile profile, boolean currentProfile) {
                        return true;
                    }
                })
                .build();

        mDrawer = new DrawerBuilder()
                .withAccountHeader(mDrawerHeader)
                .withTranslucentStatusBar(false)
                .withActivity(this)
                .addDrawerItems(
                        homeItem,
                        favouriteItem,
                        shareItem,
                        rateItem,
                        settingItem,
                        moreItem
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        mDrawer.closeDrawer();
                        final long optionID = drawerItem.getIdentifier();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                selectItemMenu((int) optionID);
                            }
                        }, 300);
                        return true;
                    }
                })
                .build();


        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentName);
        if (fragment != null)
            fragmentManager.beginTransaction().remove(fragment).commit();
        if (mDrawer != null)
            mDrawer.setSelection(homeItem, true);
    }

    private String fragmentName;
    Fragment fragment = null;

    private void selectItemMenu(int optionID) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (optionID) {
            case 1:
                fragmentName = "HomeFragment";
                fragment = fragmentManager.findFragmentByTag(fragmentName);
                if (fragment == null);
                    fragment = new HomeFragment();
                break;
            case 2:
                fragmentName = "FavouriteFragment";
                fragment = fragmentManager.findFragmentByTag(fragmentName);
                if (fragment == null)
                    fragment = new FavouriteFragment();

                break;
            case 3:
                Utils.shareLinkStore(this);
                break;
            case 4:
                Utils.openAppOnStore(this);
                break;
            case 5:
                fragmentName = "SettingFragment";
                fragment = fragmentManager.findFragmentByTag(fragmentName);
                if (fragment == null)
                    fragment = new SettingFragment();
                break;
            case 6:
                fragmentName = "MoreFragment";
                fragment = fragmentManager.findFragmentByTag(fragmentName);
                if (fragment == null)
                    fragment = new MoreFragment();
                break;
        }

        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment, fragmentName)
                    .commit();
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
