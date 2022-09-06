package com.example.inventorymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void register(View view){
        Intent intent = new Intent(login.this, SignUp.class);
        startActivity(intent);
    }

    public void login_done(View view){
        Intent intent = new Intent(login.this, MainActivity.class);
        startActivity(intent);
    }
}