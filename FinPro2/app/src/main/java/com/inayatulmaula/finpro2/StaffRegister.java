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

public class StaffRegister extends AppCompatActivity {

    EditText Name;
    EditText PhoneNumber;
    EditText Email;
    EditText Alamat;
    EditText Password;
    Button Registerbtn;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_register);

        Name = findViewById(R.id.nameStaff);
        PhoneNumber = findViewById(R.id.noHpstaff);
        Email = findViewById(R.id.emailStaff);
        Alamat = findViewById(R.id.alamatStaff);
        Password = findViewById(R.id.passwordStaff);
        Registerbtn = findViewById(R.id.btn_registerstaff);

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
        String phoneTxt = PhoneNumber.getText().toString().trim();
        String emailTxt = Email.getText().toString().trim();
        String alamat = Alamat.getText().toString().trim();
        String passTxt = Password.getText().toString().trim() ;

        if (username.isEmpty() || emailTxt.isEmpty() || phoneTxt.isEmpty() || passTxt.isEmpty() || alamat.isEmpty()) {
            Toast.makeText(StaffRegister.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }

        mAuth.createUserWithEmailAndPassword(emailTxt, passTxt)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            DataUser dataUser = new DataUser(username, emailTxt, phoneTxt, alamat, passTxt, "staff");
                            String uId = task.getResult().getUser().getUid();
                            FirebaseDatabase.getInstance().getReference("Staff").child(uId).setValue(dataUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(StaffRegister.this, "Staff Registered Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(StaffRegister.this, HomePageAdmin.class));
                                        finish();
                                    } else {
                                        Toast.makeText(StaffRegister.this, "Failed to register", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        }
                    }
                });
    }
}
