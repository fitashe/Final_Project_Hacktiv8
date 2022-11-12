package com.inayatulmaula.finpro2;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.inayatulmaula.finpro2.databinding.ActivityDetailProductsBinding;
import com.squareup.picasso.Picasso;

public class DetailProducts extends AppCompatActivity {
    private ActivityDetailProductsBinding binding;
    String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.nameDisplay.setText(getIntent().getStringExtra("nama"));
        binding.stockDisplay.setText(getIntent().getStringExtra("stock"));
        binding.deskDisplay.setText(getIntent().getStringExtra("desc"));
        binding.priceDisplay.setText("Rp "+getIntent().getStringExtra( "harga"));
        img = getIntent().getStringExtra("img");

        Picasso.Builder builder = new Picasso.Builder(this);

        builder.build().load(img)
                .resize(1460, 1460)
                .centerCrop()
                .placeholder((R.drawable.ic_launcher_foreground))
                .error(R.drawable.ic_launcher_foreground)
                .into(binding.imgDisplay);

//        ActionBar actionBar = getSupportActionBar();
//
//        actionBar.setDisplayHomeAsUpEnabled(true);


    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                this.finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
}
