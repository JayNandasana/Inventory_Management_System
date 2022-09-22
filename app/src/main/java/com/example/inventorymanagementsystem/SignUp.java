package com.example.inventorymanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUp extends AppCompatActivity {

    TextView backToLogin;
    TextInputLayout InName, InEmail, InPass, InConPass;
    Button InRegisterBtn;
    String EmailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        backToLogin = findViewById(R.id.backtologintxt);

        InName = findViewById(R.id.usernametxt);
        InEmail = findViewById(R.id.emailtxt);
        InPass = findViewById(R.id.passwordtxt);
        InConPass = findViewById(R.id.confpasswordtxt);
        InRegisterBtn = findViewById(R.id.registerbtn);
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this,login.class));
            }
        });

        InRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerformAuthentication();
            }
        });
    }

    private void PerformAuthentication() {
        String Name = InName.getEditText().getText().toString();
        String Email = InEmail.getEditText().getText().toString();
        String Password = InPass.getEditText().getText().toString();
        String ConPassword = InConPass.getEditText().getText().toString();
        int a=0;

        if(Name.isEmpty()){
            InName.setError("Name is required!");
            InName.requestFocus();
            a=1;
        }else {
            InName.setError(null);
            a=0;
        }

        if(Email.isEmpty()){
            InEmail.setError("Email is required!");
            InEmail.requestFocus();
            a=1;
        }else if (!Email.matches(EmailPattern)) {
            InEmail.setError("Enter Valid Email!");
            InEmail.requestFocus();
            a=1;
        }else {
            InEmail.setError(null);
            a=0;
        }

        if(Password.isEmpty())
        {
            InPass.setError("Password is required!");
            InPass.requestFocus();
            a=1;
        }else if(Password.length()<6){
            InPass.setError("Enter atlas 6 characters!");
            InPass.requestFocus();
            a=1;
        }else {
            InPass.setError(null);
            a=0;
        }

        if(ConPassword.isEmpty()){
            InConPass.setError("Confirm password is required!");
            InConPass.requestFocus();
            a=1;
        }
        else if(!Password.matches(ConPassword))
        {
            InConPass.setError("Password does not match");
            InConPass.requestFocus();
            a=1;
        }else {
            InConPass.setError(null);
            a=0;
        }

        if(a==0) {
            InName.setError(null);
            InEmail.setError(null);
            InPass.setError(null);
            InConPass.setError(null);

            progressDialog.setMessage("Please wait....");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                        DatabaseReference root = db.getReference().child("Users");

                        Users createUser = new Users(Name,Email,Password);

                        root.child(Name).setValue(createUser);

                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(SignUp.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(SignUp.this,"Something went wrong! Please try again!",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(SignUp.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}