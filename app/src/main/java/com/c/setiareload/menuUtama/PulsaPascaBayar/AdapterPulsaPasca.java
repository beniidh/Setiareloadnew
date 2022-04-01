package com.c.setiareload.menuUtama.PulsaPascaBayar;

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

import com.c.setiareload.R;
import com.c.setiareload.menuUtama.Paket.MPaketData;
import com.c.setiareload.menuUtama.PulsaPrabayar.DetailTransaksiPulsaPra;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class AdapterPulsaPasca extends RecyclerView.Adapter<AdapterPulsaPasca.ViewHolder> {
    Context context;
    ArrayList<MPaketData> mPaketData ;
    String nomor;
    String urlicon;

    public AdapterPulsaPasca(Context context, ArrayList<MPaketData> mPaketData, String nomor, String urlicon) {
        this.context = context;
        this.mPaketData = mPaketData;
        this.nomor = nomor;
        this.urlicon = urlicon;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk_pulsapraayar, parent, false);
        AdapterPulsaPasca.ViewHolder holder = new AdapterPulsaPasca.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MPaketData mPaketDatas = mPaketData.get(position);
//        holder.name.setText(mPaketDatas.name);
        holder.deskripsi.setText(mPaketDatas.getDescription());
        double harga = Double.valueOf(mPaketDatas.getBasic_price());
        Locale localeid = new Locale("in","ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeid);
        holder.harga.setText(formatRupiah.format(harga));

        holder.linearklik.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("deskripsi",mPaketDatas.getDescription());
            bundle.putString("nomorr",nomor);
            bundle.putString("urlicon",urlicon);
            bundle.putString("hargga",mPaketDatas.getBasic_price());
            DetailTransaksiPulsaPra fragment = new DetailTransaksiPulsaPra(); // you fragment
            FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
            fragment.setArguments(bundle);
            fragment.show(fragmentManager ,"detail");
        });
    }

    @Override
    public int getItemCount() {
        return mPaketData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,deskripsi,harga;
        LinearLayout linearklik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.namepulsaprabayar);
            deskripsi = itemView.findViewById(R.id.deskripsiprabayar);
            harga = itemView.findViewById(R.id.hargapulsaprabayar);
            linearklik = itemView.findViewById(R.id.linearklik);

        }
    }
}
