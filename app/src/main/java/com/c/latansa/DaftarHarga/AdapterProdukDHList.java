package com.c.latansa.DaftarHarga;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.latansa.Helper.utils;
import com.c.latansa.R;

import java.util.ArrayList;

public class AdapterProdukDHList extends RecyclerView.Adapter<AdapterProdukDHList.ViewHolder> {

    Context context;
    ArrayList<ResponProdukList.mData> mData;

    public AdapterProdukDHList(Context context, ArrayList<ResponProdukList.mData> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_daftarharga, parent, false);
        AdapterProdukDHList.ViewHolder holder = new AdapterProdukDHList.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ResponProdukList.mData mdata = mData.get(position);
        holder.name.setText(mdata.getName());
        holder.harga.setText(utils.ConvertRP(mdata.getTotal_price()));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, harga;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.namaProdukDH);
            harga = itemView.findViewById(R.id.hargaProdukDH);
        }
    }
}
