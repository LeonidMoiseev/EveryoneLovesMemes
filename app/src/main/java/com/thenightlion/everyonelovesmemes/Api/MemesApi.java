package com.thenightlion.everyonelovesmemes.Api;

import com.thenightlion.everyonelovesmemes.Model.MemDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MemesApi {
    @GET("/memes")
    Call<List<MemDto>> getMeme();
}
