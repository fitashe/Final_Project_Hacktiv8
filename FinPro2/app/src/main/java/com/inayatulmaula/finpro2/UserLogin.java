package com.inayatulmaula.finpro2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserLogin extends AppCompatActivity {

    EditText InputEmail, InputPass;
    Button loginUser;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        InputEmail = findViewById(R.id.etEmail);
        InputPass = findViewById(R.id.etPw);
        loginUser = findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();

        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String EmailAddress = InputEmail.getText().toString().trim();
                String passwordTxt = InputPass.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(EmailAddress).matches()){
                    InputEmail.setError("Invalid Email");
                    InputEmail.requestFocus();
                    return;
                }

                if (EmailAddress.isEmpty() || passwordTxt.isEmpty()) {
                    Toast.makeText(UserLogin.this, "Please enter your Email or password", Toast.LENGTH_SHORT).show();
                }

                System.out.println(EmailAddress + "\n" + passwordTxt);
                mAuth.signInWithEmailAndPassword(EmailAddress, passwordTxt).addOnCompleteListener(UserLogin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            String uId = firebaseUser.getUid();
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
                            databaseReference.child(uId).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    boolean isExcist = snapshot.exists();

                                    if (isExcist){
                                        DataUser dataUser = snapshot.getValue(DataUser.class);
                                        String statusUser = dataUser.getRole().toString();

                                        if (statusUser.equals("user")){
                                            Toast.makeText(UserLogin.this, "Succesfully logged in", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(UserLogin.this, Homepage.class));
                                            finish();
                                        } else {
                                            Toast.makeText(UserLogin.this, "Not User", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(UserLogin.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });
            }
        });
    }
}