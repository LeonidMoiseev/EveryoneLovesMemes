package com.thenightlion.everyonelovesmemes.Room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {MyMemInfo.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MyMemInfoDao myMemInfoDao();
}
