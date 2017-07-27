package com.lavamy.clashbasedesigner.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.lavamy.clashbasedesigner.R;
import com.lavamy.clashbasedesigner.Utils.TownHallInterface;
import com.lavamy.clashbasedesigner.fragment.FavouriteFragment.FavouriteInterface;
import com.lavamy.clashbasedesigner.manager.FavouriteManager;
import com.lavamy.clashbasedesigner.manager.SettingsManager;
import com.lavamy.clashbasedesigner.manager.ViewExtras;
import com.lavamy.clashbasedesigner.model.Collection;

import java.util.ArrayList;


public class CollectionListViewAdapter extends BaseAdapter {

    private ArrayList<Collection> collections;
    private Context context;
    private FavouriteInterface favouriteInterface;
    private TownHallInterface townHallInterface;

    public CollectionListViewAdapter(Context context, FavouriteInterface favouriteInterface, TownHallInterface townHallInterface) {
        this.context = context;
        collections = new ArrayList<>();
        this.favouriteInterface = favouriteInterface;
        this.townHallInterface = townHallInterface;
    }

    public void addCollection(Collection collection) {
        this.collections.add(collection);
    }

    @Override
    public Object getItem(int position) {
        return collections.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return collections.size();
    }

    public void clear() {
        collections.clear();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Collection collection = collections.get(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        }

        final RelativeLayout progressBarRL = (android.widget.RelativeLayout) convertView.findViewById(R.id.progressBarRL);
        progressBarRL.setVisibility(View.VISIBLE);

        //load image from url by using Glide library
        try {
            ImageView photoImgView = (ImageView) convertView.findViewById(R.id.photoImgView);
            Glide.with(context)
                .load(Uri.parse("file:///android_asset/" + collection.getPath()))
                .centerCrop()
                .crossFade()
                    .into(new GlideDrawableImageViewTarget(photoImgView) {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                            super.onResourceReady(resource, animation);
                            progressBarRL.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            progressBarRL.setVisibility(View.GONE);
                        }
                    });

        }
        catch (Exception e) {

        }

        final ImageView favouriteImgView = (ImageView) convertView.findViewById(R.id.favouriteImgView);
        favouriteImgView.setTag(collection);
        if (collection.isFavourite()) {
            favouriteImgView.setImageDrawable(ViewExtras.getDrawable(context, R.drawable.ic_action_favourite_solid));
        }
        else {
            favouriteImgView.setImageDrawable(ViewExtras.getDrawable(context, R.drawable.ic_action_favourite_outline));
        }
        favouriteImgView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (((Collection)v.getTag()).isFavourite()) {
                    FavouriteManager.getInstance().remove(((Collection) v.getTag()).getPath());
                    favouriteImgView.setImageDrawable(ViewExtras.getDrawable(context, R.drawable.ic_action_favourite_outline));
                    ((Collection)v.getTag()).setFavourite(false);
                }
                else {
                    favouriteImgView.setImageDrawable(ViewExtras.getDrawable(context, R.drawable.ic_action_favourite_solid));
                    ((Collection)v.getTag()).setFavourite(true);
                    FavouriteManager.getInstance().collections.add((Collection)v.getTag());
                }

                SettingsManager.getInstance(context).seFavouriteList(FavouriteManager.getInstance().collections);

                if (favouriteInterface != null) {
                    favouriteInterface.favouriteClicked(position);
                }
            }
        });

        RelativeLayout abstractRL = (RelativeLayout) convertView.findViewById(R.id.abstractRL);
        abstractRL.setTag(collection);
        abstractRL.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (townHallInterface != null) {
                    townHallInterface.imageClicked(position);
                }
            }
        });
        return convertView;
    }
}

