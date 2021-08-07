package com.c.dompetabata.DaftarHarga;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Api.Value;
import com.c.dompetabata.Helper.GpsTracker;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Helper.utils;
import com.c.dompetabata.R;
import com.c.dompetabata.Transaksi.MInquiry;
import com.c.dompetabata.Transaksi.ResponInquiry;
import com.c.dompetabata.menuUtama.PaketData.TransaksiHandle.DetailTransaksiPasca;
import com.c.dompetabata.menuUtama.PaketData.air.ResponProdukAir;
import com.c.dompetabata.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        holder.harga.setText(utils.ConvertRP(mdata.getBasic_price()));

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
