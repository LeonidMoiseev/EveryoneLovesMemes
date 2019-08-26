package com.thenightlion.everyonelovesmemes.View.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thenightlion.everyonelovesmemes.Presenter.AuthorizationPresenter;
import com.thenightlion.everyonelovesmemes.R;

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

        btnAuthorization.setOnClickListener(v -> {

            login = loginET.getText().toString().trim();
            password = passwordET.getText().toString().trim();
            validateForm();
            if (!TextUtils.isEmpty(login) && !TextUtils.isEmpty(password)) {

                AuthorizationPresenter authorizationPresenter = new AuthorizationPresenter(login, password, btnAuthorization
                        , relativeLayout, progressBar, AuthorizationActivity.this);

                authorizationPresenter.progressBarEnabled();
                authorizationPresenter.checkLoginUser();
            }
        });

        textFieldBoxesPassword.getEndIconImageButton().setOnClickListener(view -> hideAndShowPassword());

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
