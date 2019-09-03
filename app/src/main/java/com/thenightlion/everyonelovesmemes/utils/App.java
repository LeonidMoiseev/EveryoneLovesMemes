package com.thenightlion.everyonelovesmemes.utils;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.thenightlion.everyonelovesmemes.data.room.AppDatabase;

public class App  extends Application {
    public static App instance;

    private SharedPreferencesUtils sharedPreferencesUtils;
    private OpenGalleryAndCameraUtils openGalleryAndCameraUtils;
    private AppDatabase database;
    private PermissionsUtils permissionsUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
        sharedPreferencesUtils = new SharedPreferencesUtils(instance);
        permissionsUtils = new PermissionsUtils(instance);
        openGalleryAndCameraUtils = new OpenGalleryAndCameraUtils(instance);
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

    public SharedPreferencesUtils getSharedPreferencesUtils() {
        return sharedPreferencesUtils;
    }

    public PermissionsUtils getPermissionsUtils() {
        return permissionsUtils;
    }

    public OpenGalleryAndCameraUtils getOpenGalleryAndCameraUtils() {
        return openGalleryAndCameraUtils;
    }
}
