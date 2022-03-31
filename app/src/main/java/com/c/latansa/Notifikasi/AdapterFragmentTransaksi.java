package com.c.latansa.Notifikasi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.latansa.Helper.utils;
import com.c.latansa.R;

import java.util.ArrayList;

public class AdapterFragmentTransaksi extends RecyclerView.Adapter<AdapterFragmentTransaksi.ViewHolder> {
    Context context;
    ArrayList<ResponTransaksiN.DataTransaksi> mData;


    public AdapterFragmentTransaksi(Context context, ArrayList<ResponTransaksiN.DataTransaksi> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_notifikasi_transaksi, parent, false);
        AdapterFragmentTransaksi.ViewHolder holder = new AdapterFragmentTransaksi.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponTransaksiN.DataTransaksi dataTransaksi = mData.get(position);

        holder.keterangan.setText(dataTransaksi.getStatus());
        holder.keteranganSub.setText(dataTransaksi.getId());
        String tanggal = dataTransaksi.getUpdated_at();
        String tahun = tanggal.substring(0, 4);
        String bulan = utils.convertBulan(tanggal.substring(5, 7));
        String hari = tanggal.substring(8, 10);
        String waktu = tanggal.substring(11, 16);
        holder.tanggal.setText(hari + " " + bulan + " " + tahun);
        holder.waktu.setText(waktu);

        holder.linearklik.setOnClickListener(v -> {

            Intent intent = new Intent(context, DetailNotifikasi.class);
            Bundle extras = new Bundle();
            extras.putString("transaksid", dataTransaksi.getId());
            extras.putString("tanggal",hari + " " + bulan + " " + tahun);
            extras.putString("waktu", waktu);
            extras.putString("saldo", dataTransaksi.getTotal_price());
            extras.putString("nominal", dataTransaksi.getBasic_price());
            extras.putString("biaya", dataTransaksi.getUser_markup());
            extras.putString("status", dataTransaksi.getStatus());

            intent.putExtras(extras);
            context.startActivity(intent);


        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView keterangan, keteranganSub, tanggal, waktu;
        LinearLayout linearklik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            keterangan = itemView.findViewById(R.id.NTketerangantransaksi);
            tanggal = itemView.findViewById(R.id.NTtanggal);
            keteranganSub = itemView.findViewById(R.id.NTketeranganisi);
            linearklik = itemView.findViewById(R.id.linearnotifikasi);
            waktu = itemView.findViewById(R.id.NTwaktu);


        }
    }
}
