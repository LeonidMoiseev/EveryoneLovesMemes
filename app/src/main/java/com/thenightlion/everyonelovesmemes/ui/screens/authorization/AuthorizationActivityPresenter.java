package com.thenightlion.everyonelovesmemes.ui.screens.authorization;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.thenightlion.everyonelovesmemes.data.api.Service;
import com.thenightlion.everyonelovesmemes.utils.SharedPreferencesUtils;
import com.thenightlion.everyonelovesmemes.data.model.AuthInfoDto;
import com.thenightlion.everyonelovesmemes.data.model.LoginUserRequestDto;
import com.thenightlion.everyonelovesmemes.utils.App;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorizationActivityPresenter {


    private View view;

    AuthorizationActivityPresenter(View view) {
        this.view = view;
    }

    void checkLoginUser(String login, String password) {

        view.progressBarEnabled();

        LoginUserRequestDto body = new LoginUserRequestDto();
        body.setLogin(login);
        body.setPassword(password);

        Service.getInstance()
                .getAuthApi()
                .login(body)
                .enqueue(new Callback<AuthInfoDto>() {
                    @Override
                    public void onResponse(@NonNull Call<AuthInfoDto> call, @NonNull Response<AuthInfoDto> response) {
                        if (response.isSuccessful()) {

                            AuthInfoDto body = response.body();
                            if (body != null) {

                                saveUserInfo(body);
                                view.startMainActivity();

                            }
                        } else {
                            view.errorAuthorization();
                        }

                        view.progressBarDisabled();
                    }

                    @Override
                    public void onFailure(@NonNull Call<AuthInfoDto> call, @NonNull Throwable t) {
                        view.errorAuthorization();
                        view.progressBarDisabled();
                    }
                });
    }

    private void saveUserInfo(AuthInfoDto body) {
        SharedPreferencesUtils sharedPreferencesUtils = App.getInstance().getSharedPreferencesUtils();
        sharedPreferencesUtils.putString("token", body.getAccessToken());
        sharedPreferencesUtils.putInt("id");
        sharedPreferencesUtils.putString("username", body.userInfo.getUserName());
        sharedPreferencesUtils.putString("firstName", body.userInfo.getFirstName());
        sharedPreferencesUtils.putString("lastName", body.userInfo.getLastName());
        sharedPreferencesUtils.putString("userDescription", body.userInfo.getUserDescription());
    }

    void validateForm(String login, String password) {
        if (TextUtils.isEmpty(login)) {
            view.validateLogin();
        }

        if (TextUtils.isEmpty(password)) {
            view.validatePassword();
        }
    }

    public interface View {
        void progressBarEnabled();
        void progressBarDisabled();
        void errorAuthorization();
        void validateLogin();
        void validatePassword();
        void startMainActivity();
    }
}
