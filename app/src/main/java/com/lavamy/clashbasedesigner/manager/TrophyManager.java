package com.lavamy.clashbasedesigner.manager;

import com.lavamy.clashbasedesigner.model.Collection;

import java.util.ArrayList;

public class TrophyManager {

    private static TrophyManager instance = new TrophyManager();

    public static TrophyManager getInstance() {
        return instance;
    }

    public ArrayList<Collection> collections;

    private TrophyManager() {
        collections = new ArrayList<>();

    }
}
