package com.thenightlion.everyonelovesmemes.data.api;

import com.thenightlion.everyonelovesmemes.data.model.AuthInfoDto;
import com.thenightlion.everyonelovesmemes.data.model.LoginUserRequestDto;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("/auth/login")
    Single<AuthInfoDto> login(@Body LoginUserRequestDto loginUserRequestDto);
}
