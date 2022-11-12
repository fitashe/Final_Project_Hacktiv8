package com.inayatulmaula.finpro2.product;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.inayatulmaula.finpro2.R;
import com.inayatulmaula.finpro2.adapter.ProductsAdapter;
import com.inayatulmaula.finpro2.databinding.ActivityClothesProductBinding;
import com.inayatulmaula.finpro2.models.Products;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClothesProduct extends AppCompatActivity {
    private ActivityClothesProductBinding binding;
    public static final String GENDER_KATEGORI = "gender_kategori";
    public static final String EXTRA_KATEGORI = "extra_kategori";
    public static final String EXTRA_FILTER = "extra_filter";
    private FirebaseFirestore db;
    private RecyclerView recyclerProduct;
    private List<Products> list = new ArrayList<>();
    private ProductsAdapter productsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClothesProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerProduct = binding.recyclerProduct;
        String gender = getIntent().getStringExtra(GENDER_KATEGORI);
//        String kategori = getIntent().getStringExtra(EXTRA_KATEGORI);
        String filter = getIntent().getStringExtra(EXTRA_FILTER);

        db = FirebaseFirestore.getInstance();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user == null) {
//            finish();
//        }

        getSupportActionBar().setTitle("Clothes");

        recyclerProduct.setHasFixedSize(true);
        recyclerProduct.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerProduct.setAdapter(productsAdapter);

        if (gender!=null) {
            if (Objects.equals(gender,"man")) {
                getData(gender);
                binding.filter.setText(R.string.pria);
            } else {
                getData(gender);
                binding.filter.setText(R.string.wanita);
            }
        }

//        if (kategori!=null) {
//            if (Objects.equals(filter,"baju")) {
//                getData(gender, filter);
//                binding.filter.setText(R.string.baju);
//            } else if(Objects.equals(filter,"formal")){
//                getData(gender, filter);
//                binding.filter.setText(R.string.formal);
//            }else if(Objects.equals(filter,"celana")){
//                getData(gender, filter);
//                binding.filter.setText(R.string.celana);
//            }else if(Objects.equals(filter,"sepatu")){
//                getData(gender, filter);
//                binding.filter.setText(R.string.sepatu);
//            }
//        }

        binding.filterShirt.setOnClickListener(v -> {
            Intent intent = new Intent(ClothesProduct.this, ClothesKategoriList.class);
            intent.putExtra(ClothesKategoriList.EXTRA_GENDER, gender);
            intent.putExtra(ClothesKategoriList.EXTRA_FILTER, "baju");
            startActivity(intent);
        });

        binding.filterFormals.setOnClickListener(v -> {
            Intent intent = new Intent(ClothesProduct.this, ClothesKategoriList.class);
            intent.putExtra(ClothesKategoriList.EXTRA_GENDER, gender);
            intent.putExtra(ClothesKategoriList.EXTRA_FILTER, "formal");
            startActivity(intent);
        });

        binding.filterCelana.setOnClickListener(v -> {
            Intent intent = new Intent(ClothesProduct.this, ClothesKategoriList.class);
            intent.putExtra(ClothesKategoriList.EXTRA_GENDER, gender);
            intent.putExtra(ClothesKategoriList.EXTRA_FILTER, "celana");
            startActivity(intent);
        });

        binding.filterShoes.setOnClickListener(v -> {
            Intent intent = new Intent(ClothesProduct.this, ClothesKategoriList.class);
            intent.putExtra(ClothesKategoriList.EXTRA_GENDER, gender);
            intent.putExtra(ClothesKategoriList.EXTRA_FILTER, "sepatu");
            startActivity(intent);
        });

    }

    private void getData(String gender) {
        db.collection("product").whereEqualTo("kategori", "clothes")
                .whereEqualTo("gender", gender)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        list.clear();
                        for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                            Products products = documentSnapshot.toObject(Products.class);
                            list.add(products);
                        }
                        Log.d("AdminProduk", String.valueOf(list.size()));
                        productsAdapter = new ProductsAdapter(ClothesProduct.this, list);
                        productsAdapter.notifyDataSetChanged();
                        recyclerProduct.setAdapter(productsAdapter);
                    } else {
                        Log.w("AdminProduk", "loadPost:onCancelled", task.getException());
                    }
                });
    }
}