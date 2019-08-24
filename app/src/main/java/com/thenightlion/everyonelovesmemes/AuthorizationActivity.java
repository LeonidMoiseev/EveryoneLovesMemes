package com.thenightlion.everyonelovesmemes;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import studio.carbonylgroup.textfieldboxes.ExtendedEditText;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class AuthorizationActivity extends AppCompatActivity {

    ExtendedEditText loginET;
    ExtendedEditText passwordET;
    TextFieldBoxes textFieldBoxesLogin;
    TextFieldBoxes textFieldBoxesPassword;
    TextView btnAuthorization;
    ProgressBar progressBar;

    String login;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        loginET = findViewById(R.id.login_edit_text);
        passwordET = findViewById(R.id.password_edit_text);
        btnAuthorization = findViewById(R.id.button_authorization);
        progressBar = findViewById(R.id.progressBar);
        textFieldBoxesLogin = findViewById(R.id.text_field_boxes_login);
        textFieldBoxesPassword = findViewById(R.id.text_field_boxes_password);

        btnAuthorization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login = loginET.getText().toString().trim();
                password = passwordET.getText().toString().trim();
                validateForm();
                if (!TextUtils.isEmpty(login) && !TextUtils.isEmpty(password)) {

                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.bringToFront();
                    btnAuthorization.setText("");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setVisibility(View.INVISIBLE);
                            btnAuthorization.setText("Ok :)");
                        }
                    }, 2000);
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
}
