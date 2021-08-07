package com.c.dompetabata.Fragment.RiwayatTransaksi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.c.dompetabata.menuUtama.PaketData.Paket.MProdukPaketData;
import com.c.dompetabata.menuUtama.PaketData.PulsaPrabayar.DetailTransaksiPulsaPra;
import com.c.dompetabata.menuUtama.PaketData.PulsaPrabayar.TransaksiPending;
import com.c.dompetabata.sharePreference.Preference;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterFragmentSaldoServer extends RecyclerView.Adapter<AdapterFragmentSaldoServer.ViewHolder> {
    Context context;
    ArrayList<ResponTransaksi.DataTransaksi> mData;
    String nomor;
    String url;


    public AdapterFragmentSaldoServer(Context context, ArrayList<ResponTransaksi.DataTransaksi> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_transaksi, parent, false);
        AdapterFragmentSaldoServer.ViewHolder holder = new AdapterFragmentSaldoServer.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponTransaksi.DataTransaksi dataTransaksi = mData.get(position);

        if (dataTransaksi.getStatus().equals("PENDING")) {
            holder.status.setBackgroundColor(Color.rgb(204, 204, 0));
        } else if (dataTransaksi.getStatus().equals("GAGAL")) {
            holder.status.setBackgroundColor(Color.rgb(204, 0, 0));
        }else {
            holder.status.setBackgroundColor(Color.rgb(0, 204, 0));

        }

        holder.status.setText(dataTransaksi.getStatus());
        holder.harga.setText(dataTransaksi.getTotal_price());
        holder.transaksi.setText(dataTransaksi.getId());
        String tanggal = dataTransaksi.getUpdated_at();
        String tahun = tanggal.substring(0, 4);
        String bulan = utils.convertBulan(tanggal.substring(5, 7));
        String hari = tanggal.substring(8, 10);
        holder.tanggal.setText(hari + " " + bulan + " " + tahun);
        holder.produk.setText(dataTransaksi.getProduct_name());

        holder.linearklik.setOnClickListener(v -> {


            Intent intent = new Intent(context, TransaksiPending.class);
            Bundle extras = new Bundle();
            extras.putString("transaksid", dataTransaksi.getId());
            extras.putString("code", "saldo");
            intent.putExtras(extras);
            context.startActivity(intent);


        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView transaksi, tanggal, produk, harga, status;
        LinearLayout linearklik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            transaksi = itemView.findViewById(R.id.TransaksiFSS);
            tanggal = itemView.findViewById(R.id.TanggalFSS);
            harga = itemView.findViewById(R.id.HargaFSS);
            linearklik = itemView.findViewById(R.id.LinearFSS);
            produk = itemView.findViewById(R.id.ProdukFSS);
            status = itemView.findViewById(R.id.StatusFSS);

        }
    }
}
