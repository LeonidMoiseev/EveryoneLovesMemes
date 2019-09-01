package com.thenightlion.everyonelovesmemes.data.model;

import com.google.gson.annotations.SerializedName;

public class ErrorResponseDto {
    @SerializedName("code")
    private String code;
    @SerializedName("errorMessage")
    private String errorMessage;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
