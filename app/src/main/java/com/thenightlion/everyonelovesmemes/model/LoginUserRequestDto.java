package com.thenightlion.everyonelovesmemes.model;

import com.google.gson.annotations.SerializedName;

public class LoginUserRequestDto {
    @SerializedName("login")
    private String login;
    @SerializedName("password")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
