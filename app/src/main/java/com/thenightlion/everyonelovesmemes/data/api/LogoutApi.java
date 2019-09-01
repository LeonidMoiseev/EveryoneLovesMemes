package com.thenightlion.everyonelovesmemes.data.api;

import com.thenightlion.everyonelovesmemes.data.model.ErrorResponseDto;

import retrofit2.Call;
import retrofit2.http.POST;

public interface LogoutApi {
    @POST("/auth/logout")
    Call<ErrorResponseDto> logout();
}
