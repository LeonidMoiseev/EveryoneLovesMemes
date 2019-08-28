package com.thenightlion.everyonelovesmemes.Room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class MyMemInfo {

    @PrimaryKey
    private long id;

    private String title;

    private String description;

    private boolean isFavorite;

    private long createdDate;

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

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public String getPhotoUtl() {
        return photoUtl;
    }

    public void setPhotoUtl(String photoUtl) {
        this.photoUtl = photoUtl;
    }
}
