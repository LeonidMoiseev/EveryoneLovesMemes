package com.thenightlion.everyonelovesmemes.api;

import com.thenightlion.everyonelovesmemes.model.LoginUserRequestDto;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceApi {
    @POST("/auth/login{login}, login{password}")
    Call<LoginUserRequestDto> loginWithCredentials(@Path("login") String login, @Path("password") String password);
}
