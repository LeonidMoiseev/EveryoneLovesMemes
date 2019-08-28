package com.thenightlion.everyonelovesmemes.AuthRepository;

import com.thenightlion.everyonelovesmemes.Model.AuthInfoDto;
import com.thenightlion.everyonelovesmemes.Model.LoginUserRequestDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApi {
    @POST("/auth/login")
    Call<AuthInfoDto> login(@Body LoginUserRequestDto loginUserRequestDto);
}
