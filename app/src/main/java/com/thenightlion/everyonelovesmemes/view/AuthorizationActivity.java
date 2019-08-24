package com.thenightlion.everyonelovesmemes.view;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thenightlion.everyonelovesmemes.R;
import com.thenightlion.everyonelovesmemes.model.LoginUserRequestDto;
import com.thenightlion.everyonelovesmemes.api.Service;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class AuthorizationActivity extends AppCompatActivity {

    ExtendedEditText loginET;
    ExtendedEditText passwordET;
    TextFieldBoxes textFieldBoxesLogin;
    TextFieldBoxes textFieldBoxesPassword;
    TextView btnAuthorization;
    ProgressBar progressBar;
    RelativeLayout relativeLayout;

    String login;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        initView();

        btnAuthorization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = loginET.getText().toString().trim();
                password = passwordET.getText().toString().trim();
                validateForm();
                if (!TextUtils.isEmpty(login) && !TextUtils.isEmpty(password)) {
                    checkLoginUser();
                }
            }
        });

        textFieldBoxesPassword.getEndIconImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideAndShowPassword();
            }
        });
    }

    private void hideAndShowPassword() {
        if (textFieldBoxesPassword.getEndIconResourceId() == R.drawable.eye_true) {
            textFieldBoxesPassword.setEndIcon(R.drawable.eye_false);
            passwordET.setTransformationMethod(new PasswordTransformationMethod());
            passwordET.setSelection(passwordET.getText().length());
        } else if (textFieldBoxesPassword.getEndIconResourceId() == R.drawable.eye_false) {
            textFieldBoxesPassword.setEndIcon(R.drawable.eye_true);
            passwordET.setTransformationMethod(null);
            passwordET.setSelection(passwordET.getText().length());
        }
    }

    private void validateForm() {
        if (TextUtils.isEmpty(login)) {
            textFieldBoxesLogin.setError(getString(R.string.error_validate), false);
        }

        if (TextUtils.isEmpty(password)) {
            textFieldBoxesPassword.setError(getString(R.string.error_validate), false);
        }
    }

    public void checkLoginUser() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();
        btnAuthorization.setText("");

        Service.getInstance()
                .getServiceApi()
                .loginWithCredentials(login, password)
                .enqueue(new Callback<LoginUserRequestDto>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginUserRequestDto> call, @NonNull Response<LoginUserRequestDto> response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        btnAuthorization.setText("Войти");
                    }

                    @Override
                    public void onFailure(@NonNull Call<LoginUserRequestDto> call, @NonNull Throwable t) {
                        Snackbar snackbar = Snackbar.make(relativeLayout,
                                getString(R.string.error_login_or_password),
                                Snackbar.LENGTH_LONG).setAction("Action", null);
                        View sbView = snackbar.getView();
                        sbView.setBackgroundColor(ContextCompat.getColor(AuthorizationActivity.this,
                                R.color.colorRed));
                        snackbar.show();

                        progressBar.setVisibility(View.INVISIBLE);
                        btnAuthorization.setText("Войти");
                    }
                });
    }

    private void initView() {
        loginET = findViewById(R.id.login_edit_text);
        passwordET = findViewById(R.id.password_edit_text);
        btnAuthorization = findViewById(R.id.button_authorization);
        progressBar = findViewById(R.id.progressBar);
        textFieldBoxesLogin = findViewById(R.id.text_field_boxes_login);
        textFieldBoxesPassword = findViewById(R.id.text_field_boxes_password);
        relativeLayout = findViewById(R.id.layout_login);
    }
}