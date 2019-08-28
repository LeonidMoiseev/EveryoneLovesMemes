package com.thenightlion.everyonelovesmemes.Model;

import android.annotation.SuppressLint;
import android.content.Intent;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MemDto implements Serializable {

    @SerializedName("id")
    private long id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")
    private String description;
    @SerializedName("isFavorite")
    private boolean isFavorite;
    @SerializedName("createdDate")
    private int createdDate;
    @SerializedName("photoUtl")
    private String photoUtl;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getCreatedDate() {
        long dayOfCreation = (long) createdDate *1000;
        dayOfCreation = (System.currentTimeMillis() - dayOfCreation) / (1000*60*60*24);
        return dayOfCreation + " Дней назад";
    }

    public void setCreatedDate(int createdDate) {
        this.createdDate = createdDate;
    }

    public String getPhotoUtl() {
        return photoUtl;
    }

    public void setPhotoUtl(String photoUtl) {
        this.photoUtl = photoUtl;
    }
}
