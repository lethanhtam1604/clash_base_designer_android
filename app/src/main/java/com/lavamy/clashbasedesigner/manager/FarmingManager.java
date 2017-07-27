package com.lavamy.clashbasedesigner.manager;

import com.lavamy.clashbasedesigner.model.Collection;

import java.util.ArrayList;

public class FarmingManager {

    private static FarmingManager instance = new FarmingManager();

    public static FarmingManager getInstance() {
        return instance;
    }

    public ArrayList<Collection> collections;

    private FarmingManager() {
        collections = new ArrayList<>();

    }
}
