package com.lavamy.clashbasedesigner.manager;

import com.lavamy.clashbasedesigner.model.Collection;

import java.util.ArrayList;

public class HybridManager {

    private static HybridManager instance = new HybridManager();

    public static HybridManager getInstance() {
        return instance;
    }

    public ArrayList<Collection> collections;

    private HybridManager() {
        collections = new ArrayList<>();

    }
}
