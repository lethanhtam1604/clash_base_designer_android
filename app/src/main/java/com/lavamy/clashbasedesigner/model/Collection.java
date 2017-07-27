package com.lavamy.clashbasedesigner.model;

import java.io.Serializable;

public class Collection implements Serializable{

    private String path;
    private boolean isFavourite;

    public Collection() {

    }

    public Collection(String path, boolean isFavourite) {
        this.path = path;
        this.isFavourite = isFavourite;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
