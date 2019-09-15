package com.thenightlion.everyonelovesmemes.ui.screens.authorization;

import android.text.TextUtils;

import com.thenightlion.everyonelovesmemes.data.api.Service;
import com.thenightlion.everyonelovesmemes.utils.SharedPreferencesUtils;
import com.thenightlion.everyonelovesmemes.data.model.AuthInfoDto;
import com.thenightlion.everyonelovesmemes.data.model.LoginUserRequestDto;
import com.thenightlion.everyonelovesmemes.utils.App;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

        Service.getInstance().getAuthApi().login(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<AuthInfoDto>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(AuthInfoDto authInfoDto) {
                        view.progressBarDisabled();

                        saveUserInfo(authInfoDto);
                        view.startMainActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
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
