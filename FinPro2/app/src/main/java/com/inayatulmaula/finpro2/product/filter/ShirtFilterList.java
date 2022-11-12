package com.inayatulmaula.finpro2.product.filter;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.inayatulmaula.finpro2.adapter.ProductsAdapter;
import com.inayatulmaula.finpro2.databinding.ActivityShirtFilterListBinding;
import com.inayatulmaula.finpro2.models.Products;

import java.util.ArrayList;
import java.util.List;

public class ShirtFilterList extends AppCompatActivity {
    private ActivityShirtFilterListBinding binding;
    public static final String EXTRA_FILTER = "extra_filter";
    private FirebaseFirestore db;
    private RecyclerView recyclerProduct;
    private List<Products> list = new ArrayList<>();
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShirtFilterListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerProduct = binding.recyclerProduct;
        String baju = getIntent().getStringExtra(EXTRA_FILTER);

        db = FirebaseFirestore.getInstance();

        recyclerProduct.setHasFixedSize(true);
        recyclerProduct.setLayoutManager(new GridLayoutManager(this, 2));

        if (baju!=null) {
            getData(baju);
        }
    }

    private void getData(String filter) {
        db.collection("product")
                .whereEqualTo("kategori", "clothes")
                .whereEqualTo("baju", filter)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        list.clear();
                        for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                            Products products = documentSnapshot.toObject(Products.class);
                            list.add(products);
                        }
                        Log.d("AdminProduk", String.valueOf(list.size()));
                        productsAdapter = new ProductsAdapter(ShirtFilterList.this, list);
                        productsAdapter.notifyDataSetChanged();
                        recyclerProduct.setAdapter(productsAdapter);
                    } else {
                        Log.w("AdminProduk", "loadPost:onCancelled", task.getException());
                    }
                });
    }
}