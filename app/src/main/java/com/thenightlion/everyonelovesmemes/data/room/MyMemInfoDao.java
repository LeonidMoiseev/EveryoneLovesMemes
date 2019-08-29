package com.thenightlion.everyonelovesmemes.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MyMemInfoDao {

    @Query("SELECT * FROM myMemes")
    List<MyMemInfo> getAll();

    @Query("SELECT * FROM myMemes WHERE id = :id")
    MyMemInfo getById(long id);

    @Insert
    void insert(MyMemInfo myMemInfo);
}
