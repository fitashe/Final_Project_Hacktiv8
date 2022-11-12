package com.inayatulmaula.finpro2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.inayatulmaula.finpro2.adapter.ProductsAdapter;
import com.inayatulmaula.finpro2.databinding.ActivityHomePagesBinding;
import com.inayatulmaula.finpro2.models.Products;
import com.inayatulmaula.finpro2.product.ElectronicsProduct;
import com.inayatulmaula.finpro2.product.KategoriActivity;
import com.inayatulmaula.finpro2.product.ListProduct;

import java.util.ArrayList;
import java.util.List;

public class Homepage extends AppCompatActivity implements View.OnClickListener {
    private ActivityHomePagesBinding binding;
    private ImageView clothes, books, electronics, others;
    private TextView calluser;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    FirebaseUser user;
    String uid;

    private FirebaseFirestore db;
    private RecyclerView recyclerProduct;
    private List<Products> list = new ArrayList<>();
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        checkUser();
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        DatabaseReference reference = database.getReference();
        calluser = binding.username;

        db = FirebaseFirestore.getInstance();

        books = binding.kategoriBooks;
        books.setOnClickListener(this);

        clothes = binding.kategoriClothes;
        clothes.setOnClickListener(this);

        electronics = binding.kategoriElectronic;
        electronics.setOnClickListener(this);

        others = binding.kategoriOther;
        others.setOnClickListener(this);

        getData();
        recyclerProduct = binding.recyclerProduct;
        RecyclerView.LayoutManager gridManager = new GridLayoutManager(this, 2);
        recyclerProduct.setLayoutManager(gridManager);
        recyclerProduct.setHasFixedSize(true);

        reference.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String username = snapshot.child(uid).child("name").getValue(String.class);
                calluser.setText(username);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.logoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                checkUser();
            }
        });

    }

    private void checkUser() {
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser == null){
            startActivity(new Intent(this, HalamanDepan.class));
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.kategoriClothes:
                Intent intentClothes = new Intent(Homepage.this, KategoriActivity.class);
                startActivity(intentClothes);
                break;
            case R.id.kategoriBooks:
                Intent intent = new Intent(Homepage.this, ListProduct.class);
                intent.putExtra(ListProduct.EXTRA_KATEGORI, "books");
                startActivity(intent);
                break;
            case R.id.kategoriElectronic:
                Intent intentElectronics = new Intent(Homepage.this, ElectronicsProduct.class);
                startActivity(intentElectronics);
                break;
            case R.id.kategoriOther:
                Intent intentOther = new Intent(Homepage.this, ListProduct.class);
                intentOther.putExtra(ListProduct.EXTRA_KATEGORI, "other");
                startActivity(intentOther);
                break;
        }
    }

    private void getData() {
        db.collection("product").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        list.clear();
                        for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                            Products products = documentSnapshot.toObject(Products.class);
                            list.add(products);
                        }
                        Log.d("AdminProduk", String.valueOf(list.size()));
                        productsAdapter = new ProductsAdapter(Homepage.this, list);
                        productsAdapter.notifyDataSetChanged();
                        recyclerProduct.setAdapter(productsAdapter);
                    } else {
                        Log.w("AdminProduk", "loadPost:onCancelled", task.getException());
                    }
                });
    }
}