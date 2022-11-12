package com.inayatulmaula.finpro2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePageAdmin extends AppCompatActivity {
    Button addStaff, addStock, logout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        addStaff = (Button)findViewById(R.id.btn_addstaf);
        addStock = (Button) findViewById(R.id.btn_addstock);
        logout = (Button) findViewById(R.id.btnLogoutAdmin);

        addStaff.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), StaffRegister.class));
        });

        addStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AdminStock.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HalamanDepan.class));
            }
        });
    }
}
