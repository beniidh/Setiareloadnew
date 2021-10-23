package com.c.setiareload.menuUtama.PaketData.Paket;

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

import com.c.setiareload.Api.Api;
import com.c.setiareload.Api.Value;
import com.c.setiareload.Helper.GpsTracker;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Helper.utils;
import com.c.setiareload.R;
import com.c.setiareload.Transaksi.MInquiry;
import com.c.setiareload.Transaksi.ResponInquiry;
import com.c.setiareload.menuUtama.PaketData.PulsaPrabayar.DetailTransaksiPulsaPra;
import com.c.setiareload.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterPaketData extends RecyclerView.Adapter<AdapterPaketData.ViewHolder> {
    Context context;
    ArrayList<MProdukPaketData> mPaketData;
    String nomor;
    String url;


    public AdapterPaketData(Context context, ArrayList<MProdukPaketData> mPaketData, String nomor, String url) {
        this.context = context;
        this.mPaketData = mPaketData;
        this.nomor = nomor;
        this.url = url;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk_pulsapraayar, parent, false);
        AdapterPaketData.ViewHolder holder = new AdapterPaketData.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MProdukPaketData mPaketDatas = mPaketData.get(position);
        holder.name.setText(mPaketDatas.name);
        holder.deskripsi.setText(mPaketDatas.getDescription());
        holder.harga.setText(utils.ConvertRP(mPaketDatas.getTotal_price()));

        holder.linearklik.setOnClickListener(v -> {

            GpsTracker gpsTracker = new GpsTracker(context);
            Api api = RetroClient.getApiServices();
            MInquiry mInquiry = new MInquiry(mPaketDatas.getCode(), nomor, "PRABAYAR", Value.getMacAddress(context), Value.getIPaddress(), Value.getUserAgent(context), gpsTracker.getLatitude(), gpsTracker.getLongitude());
            String token = "Bearer " + Preference.getToken(context);

            Call<ResponInquiry> call = api.CekInquiry(token, mInquiry);

            call.enqueue(new Callback<ResponInquiry>() {
                @Override
                public void onResponse(Call<ResponInquiry> call, Response<ResponInquiry> response) {

                    String code = response.body().getCode();

                    if (code.equals("200")) {

                        Bundle bundle = new Bundle();
                        bundle.putString("deskripsi", response.body().getData().getDescription());
                        bundle.putString("nomorr", nomor);
                        bundle.putString("urlicon", url);
                        bundle.putString("kodeproduk", "pulsapra");
                        //transaksi
                        bundle.putString("RefID", response.body().getData().getRef_id());
                        bundle.putString("sku_code", response.body().getData().getBuyer_sku_code());
                        bundle.putString("inquiry", response.body().getData().getInquiry_type());
                        bundle.putString("hargga", response.body().getData().getSelling_price());
                        DetailTransaksiPulsaPra fragment = new DetailTransaksiPulsaPra(); // you fragment
                        FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                        fragment.setArguments(bundle);
                        fragment.show(fragmentManager, "detail");
                    } else {

                        Toast.makeText(context, response.body().getError(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponInquiry> call, Throwable t) {

                }
            });


        });
    }

    @Override
    public int getItemCount() {
        return mPaketData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, deskripsi, harga;
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
