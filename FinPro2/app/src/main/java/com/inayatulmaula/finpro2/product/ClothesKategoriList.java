package com.inayatulmaula.finpro2.product;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.inayatulmaula.finpro2.adapter.ProductsAdapter;
import com.inayatulmaula.finpro2.databinding.ActivityClothesKategoriListBinding;
import com.inayatulmaula.finpro2.models.Products;

import java.util.ArrayList;
import java.util.List;

public class ClothesKategoriList extends AppCompatActivity {
    private ActivityClothesKategoriListBinding binding;
    public static final String EXTRA_GENDER = "extra_gender";
    public static final String EXTRA_FILTER = "extra_filter";
    private FirebaseFirestore db;
    private RecyclerView recyclerProduct;
    private List<Products> list = new ArrayList<>();
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClothesKategoriListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerProduct = binding.recyclerProduct;
        String gender = getIntent().getStringExtra(EXTRA_GENDER);
        String filter = getIntent().getStringExtra(EXTRA_FILTER);

        db = FirebaseFirestore.getInstance();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user == null) {
//            finish();
//        }

        recyclerProduct.setHasFixedSize(true);
        recyclerProduct.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerProduct.setAdapter(productsAdapter);

        if (filter!=null) {
            getData(gender, filter);
        }
    }

    private void getData(String gender, String filter) {
        db.collection("product")
                .whereEqualTo("kategori", "clothes")
                .whereEqualTo("gender", gender)
                .whereEqualTo("filter", filter)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        list.clear();
                        for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                            Products products = documentSnapshot.toObject(Products.class);
                            list.add(products);
                        }
                        Log.d("AdminProduk", String.valueOf(list.size()));
                        productsAdapter = new ProductsAdapter(ClothesKategoriList.this, list);
                        productsAdapter.notifyDataSetChanged();
                        recyclerProduct.setAdapter(productsAdapter);
                    } else {
                        Log.w("AdminProduk", "loadPost:onCancelled", task.getException());
                    }
                });
    }

}
