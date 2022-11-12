package com.inayatulmaula.finpro2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserRegister extends AppCompatActivity {

    EditText Name;
    EditText Email;
    EditText PhoneNumber;
    EditText Password;
    Button Registerbtn;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        Name = findViewById(R.id.etName);
        Email = findViewById(R.id.etEmail);
        PhoneNumber = findViewById(R.id.etPhone);
        Password = findViewById(R.id.etPassword);
        Registerbtn = findViewById(R.id.btnRegister);

        mAuth = FirebaseAuth.getInstance();

        Registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }


    private void createUser() {
        String username = Name.getText().toString().trim();
        String emailTxt = Email.getText().toString().trim();
        String phoneTxt = PhoneNumber.getText().toString().trim();
        String passTxt = Password.getText().toString().trim();

        if (username.isEmpty() || emailTxt.isEmpty() || phoneTxt.isEmpty() || passTxt.isEmpty()) {
            Toast.makeText(UserRegister.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }

        mAuth.createUserWithEmailAndPassword(emailTxt, passTxt)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    DataUser dataUser = new DataUser(username, emailTxt, phoneTxt, null, passTxt, "user");
                    String uId = task.getResult().getUser().getUid();
                    FirebaseDatabase.getInstance().getReference("User").child(uId).setValue(dataUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(UserRegister.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(UserRegister.this, UserLogin.class));
                                finish();
                            } else {
                                Toast.makeText(UserRegister.this, "Failed to register", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
            }
        });
    }
}
