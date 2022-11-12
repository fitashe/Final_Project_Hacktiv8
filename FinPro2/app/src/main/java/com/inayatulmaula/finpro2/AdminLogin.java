package com.inayatulmaula.finpro2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLogin extends AppCompatActivity {

    String username = "admin";
    String password = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        EditText nameAdmin = findViewById(R.id.nameAdmin);
        EditText pwAdmin = findViewById(R.id.pwAdmin);
        Button lgnAdmin = findViewById(R.id.lgnAdmin);

        lgnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameAdmin.getText().toString().equalsIgnoreCase(username) && pwAdmin.getText().toString().equalsIgnoreCase(password)){
                    startActivity(new Intent(AdminLogin.this,HomePageAdmin.class));
                }else{
                    Toast.makeText(AdminLogin.this,"Username/Password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
