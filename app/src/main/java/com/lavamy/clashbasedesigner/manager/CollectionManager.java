package com.lavamy.clashbasedesigner.manager;

import com.lavamy.clashbasedesigner.model.Collection;

import java.util.ArrayList;

public class CollectionManager {

    private static CollectionManager instance = new CollectionManager();

    public static CollectionManager getInstance() {
        return instance;
    }

    public ArrayList<Collection> collections;

    private CollectionManager() {
        collections = new ArrayList<>();

   }
}
