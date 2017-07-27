package com.lavamy.clashbasedesigner.model;

import java.io.Serializable;

public class TownHall implements Serializable {

    private int image;
    private String title;
    private boolean isSelected;

    public TownHall() {

    }

    public TownHall(int image, String title, boolean isSelected) {
        this.image = image;
        this.title = title;
        this.isSelected = isSelected;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
