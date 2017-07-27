package com.lavamy.clashbasedesigner.activity;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.lavamy.clashbasedesigner.R;
import com.lavamy.clashbasedesigner.manager.FarmingManager;
import com.lavamy.clashbasedesigner.manager.FavouriteManager;
import com.lavamy.clashbasedesigner.manager.Global;
import com.lavamy.clashbasedesigner.manager.HybridManager;
import com.lavamy.clashbasedesigner.manager.SettingsManager;
import com.lavamy.clashbasedesigner.manager.TrophyManager;
import com.lavamy.clashbasedesigner.manager.ViewExtras;
import com.lavamy.clashbasedesigner.manager.WarManager;
import com.lavamy.clashbasedesigner.model.Collection;

import uk.co.senab.photoview.PhotoViewAttacher;

public class CollectionDetailActivity extends AppCompatActivity {

    private int position;
    private Collection collection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_detail);

        position = getIntent().getIntExtra("position", 0);
        if (Global.currentPage == 0) {
            collection = WarManager.getInstance().collections.get(position);
        }
        else if (Global.currentPage == 1) {
            collection = TrophyManager.getInstance().collections.get(position);
        }
        else if (Global.currentPage == 2) {
            collection = FarmingManager.getInstance().collections.get(position);
        }
        else if (Global.currentPage == 3) {
            collection = HybridManager.getInstance().collections.get(position);
        }
        else {
            collection = FavouriteManager.getInstance().collections.get(position);
        }

        init();
        createActionBar();

    }

    private void init() {
        final ImageView photoDetailImgView = (ImageView) findViewById(R.id.photoDetailImgView);

        final RelativeLayout progressBarRL = (android.widget.RelativeLayout) findViewById(R.id.progressBarRL);
        progressBarRL.setVisibility(View.VISIBLE);

        //load image from url by using Glide library
        Glide.with(this)
                .load("")
                .centerCrop()
                .crossFade()
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        progressBarRL.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBarRL.setVisibility(View.GONE);

                        //zoom in/out image by using PhotoView library
                        new PhotoViewAttacher(photoDetailImgView);
                        return false;
                    }
                })
                .into(photoDetailImgView);
    }

    private void createActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_collection_detail);
        setSupportActionBar(toolbar);

        TextView title_text = (TextView) toolbar.findViewById(R.id.title_text);
        title_text.setText(getString(R.string.full_view));
        Typeface type = Typeface.createFromAsset(getAssets(),"Font/supercell-magic.ttf");
        title_text.setTypeface(type);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        final ImageView favouriteImgView = (ImageView) toolbar.findViewById(R.id.favouriteImgView);
        if (collection.isFavourite()) {
            favouriteImgView.setImageDrawable(ViewExtras.getDrawable(this, R.drawable.ic_action_favourite_solid));
        }
        else {
            favouriteImgView.setImageDrawable(ViewExtras.getDrawable(this, R.drawable.ic_action_favourite_outline));
        }

        //event favourite
        RelativeLayout favouriteRL = (RelativeLayout) toolbar.findViewById(R.id.favouriteRL);
        favouriteRL.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (collection.isFavourite()) {
                    FavouriteManager.getInstance().remove((collection.getPath()));
                    favouriteImgView.setImageDrawable(ViewExtras.getDrawable(CollectionDetailActivity.this, R.drawable.ic_action_favourite_outline));
                    collection.setFavourite(false);
                }
                else {
                    favouriteImgView.setImageDrawable(ViewExtras.getDrawable(CollectionDetailActivity.this, R.drawable.ic_action_favourite_solid));
                    collection.setFavourite(true);
                    FavouriteManager.getInstance().collections.add(collection);
                }
                SettingsManager.getInstance(CollectionDetailActivity.this).seFavouriteList(FavouriteManager.getInstance().collections);
            }
        });
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
}

