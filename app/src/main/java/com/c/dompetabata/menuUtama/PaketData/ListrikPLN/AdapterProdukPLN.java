package com.c.dompetabata.menuUtama.PaketData.ListrikPLN;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.dompetabata.R;
import com.c.dompetabata.menuUtama.PaketData.PulsaPrabayar.DetailTransaksiPulsaPra;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterProdukPLN extends RecyclerView.Adapter<AdapterProdukPLN.ViewHolder> {

    Context context;
    ArrayList<ModelProdukPln> modelProdukPlns;
    String nomor;

    public AdapterProdukPLN(Context context, ArrayList<ModelProdukPln> modelProdukPlns, String nomor) {
        this.context = context;
        this.modelProdukPlns = modelProdukPlns;
        this.nomor = nomor;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk_plnpra, parent, false);
        AdapterProdukPLN.ViewHolder holder = new AdapterProdukPLN.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ModelProdukPln modelProdukPln = modelProdukPlns.get(position);
        holder.name.setText(modelProdukPln.getName());
        holder.deskripsi.setText(modelProdukPln.getDescription());
        double harga = Double.valueOf(modelProdukPln.getBasic_price());
        Locale localeid = new Locale("in","ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeid);
        holder.harga.setText(formatRupiah.format(harga));

        holder.linearklik.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("deskripsi",modelProdukPln.getDescription());
            bundle.putString("nomorr",nomor);
            bundle.putString("hargga",modelProdukPln.getBasic_price());
            DetailTransaksiPulsaPra fragment = new DetailTransaksiPulsaPra(); // you fragment
            FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
            fragment.setArguments(bundle);
            fragment.show(fragmentManager ,"detail");
        });




    }

    @Override
    public int getItemCount() {
        return modelProdukPlns.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,deskripsi,harga;
        LinearLayout linearklik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameplnprabayar);
            deskripsi = itemView.findViewById(R.id.deskripsiplnprabayar);
            harga = itemView.findViewById(R.id.hargaplnprabayar);
            linearklik = itemView.findViewById(R.id.linearklikpln);



        }
    }
}
