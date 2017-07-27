package com.lavamy.clashbasedesigner.manager;

import com.lavamy.clashbasedesigner.model.Collection;

import java.util.ArrayList;

public class FavouriteManager {

    private static FavouriteManager instance = new FavouriteManager();

    public static FavouriteManager getInstance() {
        return instance;
    }

    public ArrayList<Collection> collections;

    private FavouriteManager() {
        collections = new ArrayList<>();
    }

    public void remove(String path) {
        for (Collection collection: collections) {
            if (collection.getPath().compareTo(path) == 0) {
                collections.remove(collection);
                break;
            }
        }
    }
}
