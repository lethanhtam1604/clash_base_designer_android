package com.lavamy.clashbasedesigner.manager;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lavamy.clashbasedesigner.model.Collection;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SettingsManager {

    private final String MyPREFERENCES = "ClashBaseDesigner";
    private final String TypeList = "TypeList";
    private final String TownHallCurrent = "TownHallCurrent";
    private final String FavouriteList = "FavouriteList";

    private static SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    private static SettingsManager instance = null;

    private SettingsManager(Context context) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    public static SettingsManager getInstance(Context context) {
        if (instance == null)
            instance = new SettingsManager(context);
        return instance;
    }

    public void setTypeList(int value) {
        editor.putInt(TypeList, value);
        editor.commit();
    }

    public int getTypeList() {
        return sharedpreferences.getInt(TypeList, 0);
    }

    public void setTownHallCurrent(int value) {
        editor.putInt(TownHallCurrent, value);
        editor.commit();
    }

    public int getTownHallCurrent() {
        return sharedpreferences.getInt(TownHallCurrent, 0);
    }

    public void seFavouriteList(ArrayList<Collection> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        set(json);
    }

    public void set(String value) {
        editor.putString(FavouriteList, value);
        editor.commit();
    }

    public ArrayList<Collection> getFavouriteList() {
        ArrayList<Collection> result = new ArrayList<>();
        String json = sharedpreferences.getString(FavouriteList, null);
        if (json == null)
            return result;
        Gson gson = new Gson();
        Type type = new TypeToken<List<Collection>>() {
        }.getType();
        result = gson.fromJson(json, type);
        return result;
    }

}
