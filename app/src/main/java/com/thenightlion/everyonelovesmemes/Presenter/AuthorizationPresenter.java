package com.thenightlion.everyonelovesmemes.Presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thenightlion.everyonelovesmemes.Api.Service;
import com.thenightlion.everyonelovesmemes.AuthRepository.UserStorage;
import com.thenightlion.everyonelovesmemes.Model.AuthInfoDto;
import com.thenightlion.everyonelovesmemes.Model.LoginUserRequestDto;
import com.thenightlion.everyonelovesmemes.R;
import com.thenightlion.everyonelovesmemes.View.Activity.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthorizationPresenter {

    private String login;
    private String password;
    private TextView viewBtn;
    private Context context;
    private RelativeLayout relativeLayout;

    private ProgressBar progressBar;

    public AuthorizationPresenter(String login, String password, TextView viewBtn
            , RelativeLayout relativeLayout, ProgressBar progressBar, Context context) {
        this.login = login;
        this.password = password;
        this.viewBtn = viewBtn;
        this.context = context;
        this.relativeLayout = relativeLayout;
        this.progressBar = progressBar;
    }

    public void checkLoginUser() {

        LoginUserRequestDto body = new LoginUserRequestDto();
        body.setLogin(login);
        body.setPassword(password);

        Service.getInstance()
                .getAuthApi()
                .loginWithCredentials(body)
                .enqueue(new Callback<AuthInfoDto>() {
                    @Override
                    public void onResponse(@NonNull Call<AuthInfoDto> call, @NonNull Response<AuthInfoDto> response) {
                        progressBarDisabled();

                        if (response.isSuccessful()) {

                            AuthInfoDto body = response.body();
                            if (body != null) {

                                UserStorage userStorage = new UserStorage();
                                userStorage.saveUser(body, context);

                                context.startActivity(new Intent(context, MainActivity.class));
                                ((Activity)context).finish();

                            }
                        } else {
                            errorSnackbar(context.getString(R.string.error_login_or_password));
                            progressBarDisabled();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<AuthInfoDto> call, @NonNull Throwable t) {
                        errorSnackbar(context.getString(R.string.error_login_or_password));
                        progressBarDisabled();
                    }
                });
    }

    public void progressBarEnabled() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();
        viewBtn.setText("");
    }

    private void progressBarDisabled() {
        progressBar.setVisibility(View.INVISIBLE);
        viewBtn.setText(context.getString(R.string.btn_authorization));
    }

    private void errorSnackbar(String errorText) {
        Snackbar snackbar = Snackbar.make(relativeLayout, errorText,
                Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed));
        snackbar.show();
    }
}
