package com.example.diplomska;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button loginButton, signUpButton;
    ImageView showPass, showConfPass;
    EditText username, password, passwordConf;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.loginButton);
        signUpButton = (Button) findViewById(R.id.singUpButton);
        showPass = (ImageView) findViewById(R.id.showPassword);
        showConfPass = (ImageView) findViewById(R.id.showConfirmPassword);
        username = (EditText) findViewById(R.id.usernameEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        passwordConf = (EditText) findViewById(R.id.passwordConfirmEditText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSingUp();
            }
        });
        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doShowPass(1);
            }
        });
        showConfPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doShowPass(2);
            }
        });
    }

    private void doLogin()
    {

    }
    private void doSingUp()
    {

    }
    private void doShowPass(int num)
    {
        if (num == 1)
        {
            //show pass
        }else{
            //show confirmation pass
        }
    }
}
