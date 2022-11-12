package com.inayatulmaula.finpro2.product;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.inayatulmaula.finpro2.adapter.ProductsAdapter;
import com.inayatulmaula.finpro2.databinding.ActivityListProductBinding;
import com.inayatulmaula.finpro2.models.Products;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListProduct extends AppCompatActivity {
    private ActivityListProductBinding binding;
    private FirebaseFirestore db;
    public static final String EXTRA_KATEGORI = "extra_kategori";
    private RecyclerView recyclerProduct;
    private List<Products> list = new ArrayList<>();
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerProduct = binding.recyclerProduct;

        db = FirebaseFirestore.getInstance();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user == null) {
//            finish();
//        }

        recyclerProduct.setHasFixedSize(true);
        recyclerProduct.setLayoutManager(new GridLayoutManager(this, 2));

        String kategori = getIntent().getStringExtra(EXTRA_KATEGORI);

        if (kategori!=null) {
            if (Objects.equals(kategori, "Clothes")) {
                getData(kategori);
                getSupportActionBar().setTitle("clothes");
            }else if (Objects.equals(kategori, "books")) {
                getData(kategori);
                getSupportActionBar().setTitle("Books");
            } else if (Objects.equals(kategori, "electronics")) {
                getData(kategori);
                getSupportActionBar().setTitle("Electronics");
            }
            else {
                getData(kategori);
                getSupportActionBar().setTitle("Other");
            }
        }
    }

    private void getData(String kategori) {
        db.collection("product").whereEqualTo("kategori", kategori).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        list.clear();
                        for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                            Products products = documentSnapshot.toObject(Products.class);
                            list.add(products);
                        }
                        Log.d("AdminProduk", String.valueOf(list.size()));
                        productsAdapter = new ProductsAdapter(ListProduct.this, list);
                        productsAdapter.notifyDataSetChanged();
                        recyclerProduct.setAdapter(productsAdapter);
                    } else {
                        Log.w("AdminProduk", "loadPost:onCancelled", task.getException());
                    }
                });
    }
}
