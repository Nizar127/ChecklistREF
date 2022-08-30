package com.CEFS.checklist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.CEFS.checklist.Authentication.LoginActivity;
import com.CEFS.checklist.Authentication.SignUpActivity;

public class AuthenticationActivity extends AppCompatActivity {

    Button signUp,signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        signIn = findViewById(R.id.login_btn);
        signUp = findViewById(R.id.Signup_btn);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthenticationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthenticationActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}