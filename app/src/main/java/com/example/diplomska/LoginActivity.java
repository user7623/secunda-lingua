package com.example.diplomska;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    Button loginButton, signUpButton;
    ImageView showPass, showConfPass;
    EditText usernameEditText, passwordEditText, passwordConfEditText;
    TextView passConfTextView, errorTextView;
    boolean confPassHidden = true;
    boolean passHidden = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.loginButton);
        signUpButton = (Button) findViewById(R.id.singUpButton);
        showPass = (ImageView) findViewById(R.id.showPassword);
        showConfPass = (ImageView) findViewById(R.id.showConfirmPassword);
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        passwordConfEditText = (EditText) findViewById(R.id.passwordConfirmEditText);
        passConfTextView = (TextView) findViewById(R.id.passwordConfirmTextView);
        errorTextView = (TextView) findViewById(R.id.errorTextView);

        errorTextView.setVisibility(View.INVISIBLE);
        passConfTextView.setVisibility(View.INVISIBLE);
        passwordConfEditText.setVisibility(View.INVISIBLE);
        showConfPass.setVisibility(View.INVISIBLE);
        passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        passwordConfEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSignUp();
            }
        });
        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("INFO" , "show pass clicked");
                if(passHidden){
                    passwordEditText.setTransformationMethod(null);
                    passHidden = false;
                    showPass.setImageResource(R.drawable.ic_hide);
                } else{
                    passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
                    passHidden = true;
                    showPass.setImageResource(R.drawable.ic_show);
                }
            }
        });
        showConfPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("INFO" , "show passConf clicked");
                if(confPassHidden){
                    passwordConfEditText.setTransformationMethod(null);
                    confPassHidden = false;
                    showPass.setImageResource(R.drawable.ic_hide);
                } else{
                    passwordConfEditText.setTransformationMethod(new PasswordTransformationMethod());
                    confPassHidden = true;
                    showPass.setImageResource(R.drawable.ic_show);
                }
            }
        });
    }

    private void doLogin()
    {
        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(loginIntent);
    }
    private void switchToSignUp()
    {
        showConfPass.setVisibility(View.VISIBLE);
        passwordConfEditText.setVisibility(View.VISIBLE);
        passConfTextView.setVisibility(View.VISIBLE);
        signUpButton.setText("Back to login");
        loginButton.setText("SignUp");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSignUp();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToLogin();
            }
        });
    }
    private void switchToLogin(){
        showConfPass.setVisibility(View.INVISIBLE);
        passwordConfEditText.setVisibility(View.INVISIBLE);
        passConfTextView.setVisibility(View.INVISIBLE);
        signUpButton.setText("switch to sign up");
        loginButton.setText("Login");
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToSignUp();
            }
        });
    }

    private void doSignUp()
    {
        boolean username = usernameEditText.getText().toString().isEmpty();
        boolean password = passwordEditText.getText().toString().isEmpty();
        if (!(username && password)) {
            //TODO: dodadi kod za najava na back4app
            Intent signUpIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(signUpIntent);
        }
        else {
            if (username && password)
            {
                errorTextView.setText("Please enter both username and password");
                errorTextView.setVisibility(View.VISIBLE);
            }else if (username)
            {
                errorTextView.setText("Please enter username");
                errorTextView.setVisibility(View.VISIBLE);
            }else {
                errorTextView.setText("Please enter password");
                errorTextView.setVisibility(View.VISIBLE);
            }
        }
    }
}
