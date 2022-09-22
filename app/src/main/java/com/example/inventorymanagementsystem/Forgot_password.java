package com.example.inventorymanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;


public class Forgot_password extends AppCompatActivity {

    TextView backtologin;
    TextInputLayout emailEditText;
    Button resetPasswordButton;
    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        backtologin = findViewById(R.id.login);

        emailEditText = findViewById(R.id.email);
        resetPasswordButton = findViewById(R.id.sendemail);
        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();

        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Forgot_password.this,login.class));
            }
        });

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                resetPassword();
            }
        });
    }


    private void resetPassword() {
        String email = emailEditText.getEditText().getText().toString();

        if(email.isEmpty()){
            emailEditText.setError("Email is Required");
            emailEditText.requestFocus();
        }else if (!email.matches(emailpattern))
        {
            emailEditText.setError("Enter Valid Email!");
        }else {
            progressDialog.setMessage("Please wait....");
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Forgot_password.this,"Check your email to reset your password",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Forgot_password.this,"Somthing went wrong! Please try again!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}