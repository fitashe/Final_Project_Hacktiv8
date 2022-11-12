package com.inayatulmaula.finpro2;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.inayatulmaula.finpro2.databinding.ActivityAdminAddProductBinding;
import com.inayatulmaula.finpro2.models.Products;

import java.util.Objects;

public class AdminAddProduct extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private ActivityAdminAddProductBinding binding;
    private FirebaseFirestore db;
    public static final String INTENT_KATEGORI = "intent_kategori";
    public static final String EXTRA_ID = "extra_id";
    ArrayAdapter<CharSequence> adapterFilter;
    private int lastId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();

        boolean isEdit = getIntent().getBooleanExtra(INTENT_KATEGORI, false);
        lastId = getIntent().getIntExtra(EXTRA_ID, 0);

        Spinner spinnerKategori = binding.dropdownKategori;
        Spinner spinnerFilter = binding.dropdownFilter;
        Spinner spinnerGender = binding.dropdownGender;

        ArrayAdapter<CharSequence> adapterGender = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> adapterKategori = ArrayAdapter.createFromResource(this,
                R.array.kategori_array, android.R.layout.simple_spinner_item);

        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapterGender);
        spinnerGender.setOnItemSelectedListener(this);

        adapterKategori.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(adapterKategori);

        spinnerKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tempKategori = spinnerKategori.getSelectedItem().toString();

                switch (tempKategori) {
                    case "clothes":
                        adapterFilter = ArrayAdapter.createFromResource(AdminAddProduct.this,
                                R.array.filter_clothes_array, android.R.layout.simple_spinner_item);
                        break;
                    case "electronics":
                        adapterFilter = ArrayAdapter.createFromResource(AdminAddProduct.this,
                                R.array.filter_electronics_array, android.R.layout.simple_spinner_item);
                        break;
                    case "books":
                        adapterFilter = ArrayAdapter.createFromResource(AdminAddProduct.this,
                                R.array.filter_books_array, android.R.layout.simple_spinner_item);
                        break;
                    default:
                        adapterFilter = ArrayAdapter.createFromResource(AdminAddProduct.this,
                                R.array.filter_other_array, android.R.layout.simple_spinner_item);
                        break;
                }

                adapterFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerFilter.setAdapter(adapterFilter);
                spinnerFilter.setOnItemSelectedListener(this);
                if (isEdit) {
                    if (adapterFilter != null) {
                        spinnerFilter.setSelection(adapterFilter.getPosition(getIntent().getStringExtra("filter")));
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

//        binding.imageProduct.setOnClickListener(this);

        if (isEdit) {
            binding.inputId.setText(String.valueOf(lastId));
            binding.btnAddProduct.setText("Edit");

            spinnerGender.setSelection(adapterGender.getPosition(setGender(getIntent().getStringExtra("gender"))));
            spinnerKategori.setSelection(adapterKategori.getPosition(getIntent().getStringExtra("kategori")));
            binding.inputName.setText(getIntent().getStringExtra("nama"));
            binding.inputHarga.setText(getIntent().getStringExtra("harga"));
            binding.inputStok.setText(getIntent().getStringExtra("stock"));
            binding.inputDeskripsi.setText(getIntent().getStringExtra("desc"));
        } else {
            binding.inputId.setText(String.valueOf(lastId+1));
            binding.btnAddProduct.setText("Tambah");
        }

        binding.btnAddProduct.setOnClickListener(view -> {
            String id = binding.inputId.getText().toString().trim();
            String nama = binding.inputName.getText().toString().trim();
            String harga = binding.inputHarga.getText().toString().trim();
            String stok = binding.inputStok.getText().toString().trim();
            String desc = binding.inputDeskripsi.getText().toString().trim();
//            String img = binding.inputLinkImage.getText().toString().trim();
            String img = "https://firebasestorage.googleapis.com/v0/b/finpro2kelompok4.appspot.com/o/ic_barang.jpg?alt=media&token=ec22087e-7cbf-4f8f-9f84-ce42718b0add";
            String gender = getGender(spinnerGender.getSelectedItem().toString());
            String kategori = spinnerKategori.getSelectedItem().toString();

            Products newProduct = new Products();
            newProduct.setKategori(kategori);
            newProduct.setGender(gender);
            newProduct.setFilter(spinnerFilter.getSelectedItem().toString());
            newProduct.setId(Integer.parseInt(id));
            newProduct.setName(nama);
            newProduct.setHarga(harga);
            newProduct.setStok(stok);
            newProduct.setDeskripsi(desc);
            newProduct.setImages(img);

            inputData(newProduct);
        });
    }

    private String getGender(String gender) {
        if (Objects.equals(gender, "pria")) {
            return "man";
        } else if (Objects.equals(gender, "wanita")) {
            return "woman";
        } else {
            return "unisex";
        }
    }

    private String setGender(String gender) {
        if (Objects.equals(gender, "man")) {
            return "pria";
        } else if (Objects.equals(gender, "woman")) {
            return "wanita";
        } else {
            return "unisex";
        }
    }

    private void inputData(Products products) {
        db.collection("product").document(String.valueOf(products.getId()))
            .set(products)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(AdminAddProduct.this, "Produk Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                    finish();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminAddProduct.this, "Gagal Menambahkan Produk", Toast.LENGTH_SHORT).show();
                }
            });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}