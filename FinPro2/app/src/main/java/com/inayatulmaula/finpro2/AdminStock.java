package com.inayatulmaula.finpro2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.inayatulmaula.finpro2.adapter.AdminProductsAdapter;
import com.inayatulmaula.finpro2.databinding.ActivityAdminStockBinding;
import com.inayatulmaula.finpro2.models.Products;

import java.util.ArrayList;
import java.util.List;

public class AdminStock extends AppCompatActivity {
    private RecyclerView recyclerProduct;
    private ActivityAdminStockBinding binding;

    private FirebaseFirestore db;
    private List<Products> list = new ArrayList<>();
    private AdminProductsAdapter productsAdapter;
    private int lastId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminStockBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        recyclerProduct = binding.rvStock;

        getData();
        RecyclerView.LayoutManager gridManager = new GridLayoutManager(this, 2);
        recyclerProduct.setLayoutManager(gridManager);
        recyclerProduct.setHasFixedSize(true);

        binding.fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(AdminStock.this, AdminAddProduct.class);
            intent.putExtra(AdminAddProduct.INTENT_KATEGORI, false);
            intent.putExtra(AdminAddProduct.EXTRA_ID, lastId);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        db.collection("product")
                .orderBy("id", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        list.clear();
                        for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                            Products product = documentSnapshot.toObject(Products.class);
                            list.add(product);
                        }
                        Log.d("AdminProduk", String.valueOf(list.size()));
                        lastId = list.get(list.size()-1).getId();
                        Log.d("AdminProduk", String.valueOf(lastId));
                        productsAdapter = new AdminProductsAdapter(AdminStock.this, list);
                        productsAdapter.notifyDataSetChanged();
                        recyclerProduct.setAdapter(productsAdapter);
                    } else {
                        Log.w("AdminProduk", "loadPost:onCancelled", task.getException());
                    }
                });
    }


}


