package com.c.dompetabata.menuUtama.PaketData.PulsaPrabayar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.c.dompetabata.Adapter.AdapterKelurahan;
import com.c.dompetabata.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static java.security.AccessController.getContext;

public class AdapterPulsaPrabayar extends RecyclerView.Adapter<AdapterPulsaPrabayar.ViewHolder> {
    Context context;
    ArrayList<MPulsaPra> mPulsaPras ;
    String nomor;
    String urlicon;

    public AdapterPulsaPrabayar(Context context, ArrayList<MPulsaPra> mPulsaPras,String nomor,String urlicon) {
        this.context = context;
        this.mPulsaPras = mPulsaPras;
        this.nomor = nomor;
        this.urlicon = urlicon;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk_pulsapraayar, parent, false);
        AdapterPulsaPrabayar.ViewHolder holder = new AdapterPulsaPrabayar.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MPulsaPra mPulsaPra = mPulsaPras.get(position);
        holder.name.setText(mPulsaPra.name);
        holder.deskripsi.setText(mPulsaPra.getDescription());
        double harga = Double.valueOf(mPulsaPra.getBasic_price());
        Locale localeid = new Locale("in","ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeid);
        holder.harga.setText(formatRupiah.format(harga));

        holder.linearklik.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("deskripsi",mPulsaPra.getDescription());
            bundle.putString("nomorr",nomor);
            bundle.putString("urlicon",urlicon);
            bundle.putString("hargga",mPulsaPra.getBasic_price());
            DetailTransaksiPulsaPra fragment = new DetailTransaksiPulsaPra(); // you fragment
            FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
            fragment.setArguments(bundle);
            fragment.show(fragmentManager ,"detail");
        });
    }

    @Override
    public int getItemCount() {
        return mPulsaPras.size();
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
