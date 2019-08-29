package com.thenightlion.everyonelovesmemes.data.api;

import com.thenightlion.everyonelovesmemes.data.model.MemDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MemesApi {
    @GET("/memes")
    Call<List<MemDto>> getMeme();
}
