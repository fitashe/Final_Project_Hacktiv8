package com.inayatulmaula.finpro2.product;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.inayatulmaula.finpro2.adapter.ProductsAdapter;
import com.inayatulmaula.finpro2.databinding.ActivityElectronicsProductBinding;
import com.inayatulmaula.finpro2.models.Products;

import java.util.ArrayList;
import java.util.List;

public class ElectronicsProduct extends AppCompatActivity {

    private ActivityElectronicsProductBinding binding;
    private FirebaseFirestore db;
    private RecyclerView recyclerProduct;
    private List<Products> list = new ArrayList<>();
    private ProductsAdapter productsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityElectronicsProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerProduct = binding.recyclerProduct;

        db = FirebaseFirestore.getInstance();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user == null) {
//            finish();
//        }

        recyclerProduct.setHasFixedSize(true);
        recyclerProduct.setLayoutManager(new GridLayoutManager(this, 2));

        getData();
        getSupportActionBar().setTitle("Electronics");

        binding.filterLaptop.setOnClickListener(view -> {
            Intent intent = new Intent(ElectronicsProduct.this, ElectronicsKategoriList.class);
            intent.putExtra(ElectronicsKategoriList.EXTRA_FILTER, "laptop");
            startActivity(intent);
        });

        binding.filterPhone.setOnClickListener(view -> {
            Intent intent = new Intent(ElectronicsProduct.this, ElectronicsKategoriList.class);
            intent.putExtra(ElectronicsKategoriList.EXTRA_FILTER, "handphone");
            startActivity(intent);
        });

    }

    private void getData() {
        db.collection("product").whereEqualTo("kategori", "electronics").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        list.clear();
                        for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                            Products products = documentSnapshot.toObject(Products.class);
                            list.add(products);
                        }
                        Log.d("AdminProduk", String.valueOf(list.size()));
                        productsAdapter = new ProductsAdapter(ElectronicsProduct.this, list);
                        productsAdapter.notifyDataSetChanged();
                        recyclerProduct.setAdapter(productsAdapter);
                    } else {
                        Log.w("AdminProduk", "loadPost:onCancelled", task.getException());
                    }
                });
    }
}