package com.inayatulmaula.finpro2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HalamanDepan extends AppCompatActivity {
    Button register, login;
    TextView AdminLogin, StaffLogin, About;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_depan);

        register = (Button) findViewById(R.id.btn_register);
        login =  (Button) findViewById(R.id.btn_Login);
        AdminLogin = (TextView) findViewById(R.id.tvAdminLogin);
        StaffLogin = (TextView) findViewById(R.id.tv_stafLogin);
        About = (TextView) findViewById(R.id.tv_about);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HalamanDepan.this,UserLogin.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HalamanDepan.this,UserRegister.class);
                startActivity(intent);
            }
        });

        AdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HalamanDepan.this,AdminLogin.class));
            }
        });

        StaffLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HalamanDepan.this,StaffLogin.class));
            }
        });

        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HalamanDepan.this,About.class));
            }
        });
    }
    public void onBackPressed(){
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
