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
}
