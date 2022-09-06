package com.example.inventorymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class add_product_btn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_btn);

        getSupportActionBar().setTitle("Product Details");
    }
    public void add_product(View view){
        Intent intent = new Intent(add_product_btn.this, add_product.class);
        startActivity(intent);
    }
}