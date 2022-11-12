package com.inayatulmaula.finpro2.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inayatulmaula.finpro2.AdminAddProduct;
import com.inayatulmaula.finpro2.R;
import com.inayatulmaula.finpro2.databinding.RowProductsBinding;
import com.inayatulmaula.finpro2.models.Products;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminProductsAdapter extends RecyclerView.Adapter<AdminProductsAdapter.MyViewHolder> {
    private Context context;
    private List<Products> list;

    public AdminProductsAdapter(Context context, List<Products> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowProductsBinding binding = RowProductsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Products products = list.get(position);
        if (products!=null) {
            holder.bind(products);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final RowProductsBinding binding;

        public MyViewHolder(RowProductsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Products products) {
            binding.rowName.setText(products.getName());
            binding.rowHarga.setText("Rp " + products.getHarga());
            binding.rowStok.setText(products.getStok());

            Picasso.get().load(products.getImages())
                    .placeholder((R.drawable.ic_launcher_foreground))
                    .error(R.drawable.ic_launcher_foreground)
                    .resize(1460, 1460)
                    .centerCrop()
                    .into(binding.rowImage);

            int position = getAdapterPosition();
            itemView.setOnClickListener(view -> {

                Intent intent = new Intent(context, AdminAddProduct.class);
                intent.putExtra(AdminAddProduct.INTENT_KATEGORI, true);
                intent.putExtra(AdminAddProduct.EXTRA_ID, list.get(position).getId());

                intent.putExtra("img", list.get(position).getImages());
                intent.putExtra("nama", list.get(position).getName());
                intent.putExtra("kategori", list.get(position).getKategori());
                intent.putExtra("gender", list.get(position).getGender());
                intent.putExtra("filter", list.get(position).getFilter());
                intent.putExtra("harga",list.get(position).getHarga());
                intent.putExtra("stock",list.get(position).getStok());
                intent.putExtra("desc",list.get(position).getDeskripsi());

                itemView.getContext().startActivity(intent);
            });

        }

    }
}

