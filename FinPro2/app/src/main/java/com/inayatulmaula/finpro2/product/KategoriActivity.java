package com.inayatulmaula.finpro2.product;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.inayatulmaula.finpro2.databinding.ActivityKategoriBinding;

public class KategoriActivity extends AppCompatActivity {
    private ActivityKategoriBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKategoriBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.linearMan.setOnClickListener(v -> {
            Intent intent = new Intent(KategoriActivity.this, ClothesProduct.class);
            intent.putExtra(ClothesProduct.GENDER_KATEGORI, "man");
            startActivity(intent);
        });

        binding.linearWoman.setOnClickListener(v -> {
            Intent intent = new Intent(KategoriActivity.this, ClothesProduct.class);
            intent.putExtra(ClothesProduct.GENDER_KATEGORI, "woman");
            startActivity(intent);
        });

        getSupportActionBar().setTitle("Clothes");

    }

}
