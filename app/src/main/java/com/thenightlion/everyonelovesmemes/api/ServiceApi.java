package com.thenightlion.everyonelovesmemes.api;

import com.thenightlion.everyonelovesmemes.model.AuthInfoDto;
import com.thenightlion.everyonelovesmemes.model.LoginUserRequestDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceApi {
    @POST("/auth/login")
    Call<AuthInfoDto> loginWithCredentials(@Body LoginUserRequestDto loginUserRequestDto);
}
