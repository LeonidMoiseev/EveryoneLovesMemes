package com.thenightlion.everyonelovesmemes.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MyMemInfoDao {

    @Query("SELECT * FROM myMemInfo")
    List<MyMemInfo> getAll();

    @Query("SELECT * FROM myMemInfo WHERE id = :id")
    MyMemInfo getById(long id);

    @Insert
    void insert(MyMemInfo myMemInfo);
}
