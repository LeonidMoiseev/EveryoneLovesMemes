package com.thenightlion.everyonelovesmemes.data.api;

import com.thenightlion.everyonelovesmemes.data.model.MemDto;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface MemesApi {
    @GET("/memes")
    Single<List<MemDto>> getMeme();
}
