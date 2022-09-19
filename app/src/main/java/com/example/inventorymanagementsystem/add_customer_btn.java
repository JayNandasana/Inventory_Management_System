package com.example.inventorymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class add_customer_btn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer_btn);

        getSupportActionBar().setTitle("Customer Details");
    }
    public void add_customer(View view){
        Intent intent = new Intent(add_customer_btn.this, add_customer.class);
        startActivity(intent);
    }


}