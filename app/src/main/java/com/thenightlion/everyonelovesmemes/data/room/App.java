package com.thenightlion.everyonelovesmemes.data.room;

import android.app.Application;
import android.arch.persistence.room.Room;

public class App  extends Application {
    public static App instance;

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
    }

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
