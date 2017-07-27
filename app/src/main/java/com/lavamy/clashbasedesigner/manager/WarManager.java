package com.lavamy.clashbasedesigner.manager;

import com.lavamy.clashbasedesigner.model.Collection;

import java.util.ArrayList;

public class WarManager {

    private static WarManager instance = new WarManager();

    public static WarManager getInstance() {
        return instance;
    }

    public ArrayList<Collection> collections;

    private WarManager() {
        collections = new ArrayList<>();

    }
}
