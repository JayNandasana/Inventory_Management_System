package com.example.inventorymanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    EditText email,password;
    TextInputLayout TFemil,TFpassword;
    Button login;
    TextView register;
    FirebaseAuth fAuth;
    ProgressBar progressbar;
    FirebaseUser muser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TFemil = findViewById(R.id.textField1);
        TFpassword= findViewById(R.id.textField2);

        email = findViewById(R.id.lemail);
        password = findViewById(R.id.lpassword);
        login = findViewById(R.id.button7);
        register = findViewById(R.id.register);
        progressbar = findViewById(R.id.progressBar3);
        fAuth = FirebaseAuth.getInstance();
        muser=fAuth.getCurrentUser();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Temail = email.getText().toString().trim();
                String Tpassword = password.getText().toString().trim();

                if(TextUtils.isEmpty(Temail)){
                    TFemil.setError("Email is Required");
                    return;
                }if(TextUtils.isEmpty(Tpassword)){
                    TFpassword.setError("Password is Required");
                    return;
                }if(Tpassword.length() < 8){
                    TFpassword.setError("Password Must be eight characters");
                    return;
                }
                progressbar.setVisibility(View.VISIBLE);

                fAuth.signInWithEmailAndPassword(Temail,Tpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(login.this, "Logged in Successfully.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                        else{
                            Toast.makeText(login.this, "Error ! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressbar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
    public void register(View view){
        Intent intent = new Intent(login.this, SignUp.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        if(muser !=null){
            Intent i =new Intent(login.this,MainActivity.class);
            startActivity(i);
            finish();
        }else{
            Intent i =new Intent(login.this,login.class);
        }
    }
}