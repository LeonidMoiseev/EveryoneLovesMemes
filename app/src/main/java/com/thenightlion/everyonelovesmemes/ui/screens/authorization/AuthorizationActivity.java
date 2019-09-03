package com.thenightlion.everyonelovesmemes.ui.screens.authorization;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thenightlion.everyonelovesmemes.R;
import com.thenightlion.everyonelovesmemes.ui.screens.main.MainActivity;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class AuthorizationActivity extends AppCompatActivity implements AuthorizationActivityPresenter.View{

    private AuthorizationActivityPresenter authPresenter;

    private ExtendedEditText loginET;
    private ExtendedEditText passwordET;
    private TextFieldBoxes textFieldBoxesLogin;
    private TextFieldBoxes textFieldBoxesPassword;
    private TextView btnAuthorization;
    private ProgressBar progressBar;
    private RelativeLayout relativeLayout;

    private String login;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        initView();
        authPresenter = new AuthorizationActivityPresenter(this);

        btnAuthorization.setOnClickListener(v -> {

            login = loginET.getText().toString().trim();
            password = passwordET.getText().toString().trim();

            authPresenter.validateForm(login, password);

            if (!TextUtils.isEmpty(login) && !TextUtils.isEmpty(password)) {

                authPresenter.checkLoginUser(login, password);

            }
        });

        textFieldBoxesPassword.getEndIconImageButton().setOnClickListener(view -> hideAndShowPassword());

    }

    @Override
    public void progressBarEnabled() {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();
        btnAuthorization.setText("");
    }

    @Override
    public void progressBarDisabled() {
        progressBar.setVisibility(View.INVISIBLE);
        btnAuthorization.setText(getString(R.string.btn_authorization));
    }

    @Override
    public void errorAuthorization() {
        Snackbar snackbar = Snackbar.make(relativeLayout, getString(R.string.error_login_or_password),
                Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed));
        snackbar.show();
    }

    @Override
    public void validateLogin() {
        textFieldBoxesLogin.setError(getString(R.string.error_validate), false);
    }

    @Override
    public void validatePassword() {
        textFieldBoxesPassword.setError(getString(R.string.error_validate), false);
    }

    @Override
    public void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
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

    private void initView() {
        loginET = findViewById(R.id.login_edit_text);
        loginET.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        passwordET = findViewById(R.id.password_edit_text);
        btnAuthorization = findViewById(R.id.button_authorization);
        progressBar = findViewById(R.id.progressBar);
        textFieldBoxesLogin = findViewById(R.id.text_field_boxes_login);
        textFieldBoxesPassword = findViewById(R.id.text_field_boxes_password);
        relativeLayout = findViewById(R.id.layout_login);
    }
}
