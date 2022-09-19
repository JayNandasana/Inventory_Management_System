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

public class SignUp extends AppCompatActivity {
    TextInputLayout TFemil,TFpassword,TFname;

    EditText name,email,password;
    Button register;
    TextView login;
    FirebaseAuth fAuth;
    ProgressBar progressbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TFname = findViewById(R.id.textField1);
        TFemil = findViewById(R.id.textField2);
        TFpassword= findViewById(R.id.textField3);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.button5);
        login = findViewById(R.id.textView2);
        progressbar = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();

//        if(fAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(), login.class));
//            finish();
//        }

        register.setOnClickListener(new View.OnClickListener() {
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

                fAuth.createUserWithEmailAndPassword(Temail,Tpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(SignUp.this, "User Created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), login.class));
                        }
                        else{
                            Toast.makeText(SignUp.this, "Error ! "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressbar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });
    }
    public void login(View view){
        Intent intent = new Intent(SignUp.this, login.class);
        startActivity(intent);
    }
}