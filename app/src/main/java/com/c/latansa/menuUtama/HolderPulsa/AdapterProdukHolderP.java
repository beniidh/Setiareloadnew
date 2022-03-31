package com.c.latansa.menuUtama.HolderPulsa;

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

import com.c.latansa.Api.Api;
import com.c.latansa.Api.Value;
import com.c.latansa.Helper.GpsTracker;
import com.c.latansa.Helper.LoadingPrimer;
import com.c.latansa.Helper.RetroClient;
import com.c.latansa.Helper.utils;
import com.c.latansa.R;
import com.c.latansa.Transaksi.MInquiry;
import com.c.latansa.Transaksi.ResponInquiry;
import com.c.latansa.menuUtama.TransaksiHandle.DetailTransaksiPasca;
import com.c.latansa.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterProdukHolderP extends RecyclerView.Adapter<AdapterProdukHolderP.ViewHolder> {
    Context context;
    ArrayList<ResponProdukHolder.Mdata> produk;
    String nomor;
    produkholderPasca ProdukholderPasca;


    public AdapterProdukHolderP(Context context, ArrayList<ResponProdukHolder.Mdata> produk, String nomor,  produkholderPasca ProdukholderPasca) {
        this.context = context;
        this.produk = produk;
        this.nomor = nomor;
        this.ProdukholderPasca = ProdukholderPasca;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk_pulsapraayar, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponProdukHolder.Mdata Produk = produk.get(position);
        holder.name.setText(Produk.name);
        holder.deskripsi.setText(Produk.getDescription());
       holder.harga.setVisibility(View.GONE);


        if (Produk.isGangguan()) {
            holder.gangguan.setVisibility(View.VISIBLE);
            holder.linearklik.setEnabled(false);
        }else {
            holder.gangguan.setVisibility(View.GONE);
            holder.linearklik.setEnabled(true);
        }


        holder.linearklik.setOnClickListener(v -> {
            LoadingPrimer loadingPrimer = new LoadingPrimer(ProdukholderPasca);
            loadingPrimer.startDialogLoading();

            if (!nomor.isEmpty()) {

                GpsTracker gpsTracker = new GpsTracker(context);
                Api api = RetroClient.getApiServices();
                MInquiry mInquiry = new MInquiry(Produk.getCode(), Preference.getNo(context), "PASCABAYAR", Value.getMacAddress(context), Value.getIPaddress(), Value.getUserAgent(context),Double.valueOf(Preference.getLang(v.getContext())), Double.valueOf(Preference.getLong(v.getContext())));
                String token = "Bearer " + Preference.getToken(context);

                Call<ResponInquiry> call = api.CekInquiry(token, mInquiry);

                call.enqueue(new Callback<ResponInquiry>() {
                    @Override
                    public void onResponse(Call<ResponInquiry> call, Response<ResponInquiry> response) {

                        String code = response.body().getCode();
                        if (code.equals("200")) {

                            if (response.body().getData().getStatus().equals("Sukses")) {
                                Bundle bundle = new Bundle();
                                bundle.putString("nomorr", Preference.getNo(context));
                                bundle.putString("namecustomer", response.body().getData().getCustomer_name());
                                bundle.putString("RefID", response.body().getData().getRef_id());
                                bundle.putString("sku_code", response.body().getData().getBuyer_sku_code());
                                bundle.putString("kodeproduk", "pulsapasca");
                                bundle.putString("inquiry", response.body().getData().getInquiry_type());
                                bundle.putString("hargga", response.body().getData().getSelling_price());

                                bundle.putString("status", response.body().getData().getStatus());
                                bundle.putString("tagihan", response.body().getData().getBasic_price());
                                bundle.putString("deskription", response.body().getData().getDescription());
                                bundle.putString("admin", response.body().getData().getAdmin_fee());

                                Preference.setUrlIcon(context, "");
                                DetailTransaksiPasca fragment = new DetailTransaksiPasca(); // you fragment
                                FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                                fragment.setArguments(bundle);
                                loadingPrimer.dismissDialog();
                                fragment.show(fragmentManager, "detail");
                            } else {
                                loadingPrimer.dismissDialog();
                                Toast.makeText(context, response.body().getData().getStatus() + " " + response.body().getData().getDescription(), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            loadingPrimer.dismissDialog();
                            Toast.makeText(context, response.body().getError(), Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponInquiry> call, Throwable t) {

                    }
                });

            }else {
                Toast.makeText(v.getContext(), "Nomor tidak boleh kosong",Toast.LENGTH_LONG).show();
           loadingPrimer.dismissDialog();
            }
        });


    }

    @Override
    public int getItemCount() {
        return produk.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, deskripsi, harga,gangguan;
        LinearLayout linearklik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.namepulsaprabayar);
            deskripsi = itemView.findViewById(R.id.deskripsiprabayar);
            harga = itemView.findViewById(R.id.hargapulsaprabayar);
            linearklik = itemView.findViewById(R.id.linearklik);
            gangguan = itemView.findViewById(R.id.gangguan);
        }
    }

    public void chekPasca ( ResponProdukHolder.Mdata Produk, View v){

//        LoadingPrimer loadingPrimer = new LoadingPrimer(Produkair);
//        loadingPrimer.startDialogLoading();

        GpsTracker gpsTracker = new GpsTracker(context);

        Api api = RetroClient.getApiServices();
        MInquiry mInquiry = new MInquiry(Produk.getCode(), nomor, "PASCABAYAR", Value.getMacAddress(context), Value.getIPaddress(), Value.getUserAgent(context), gpsTracker.getLatitude(), gpsTracker.getLongitude());
        String token = "Bearer " + Preference.getToken(context);
        Call<ResponInquiry> call = api.CekInquiry(token, mInquiry);
        call.enqueue(new Callback<ResponInquiry>() {
            @Override
            public void onResponse(Call<ResponInquiry> call, Response<ResponInquiry> response) {

                String code = response.body().getCode();
                if (code.equals("200")) {

                    if (response.body().getData().getStatus().equals("Sukses")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("nomorr", nomor);
                        bundle.putString("namecustomer", response.body().getData().getCustomer_name());
                        bundle.putString("RefID", response.body().getData().getRef_id());
                        bundle.putString("sku_code", response.body().getData().getBuyer_sku_code());
                        bundle.putString("kodeproduk", "pulsapasca");
                        bundle.putString("inquiry", response.body().getData().getInquiry_type());
                        bundle.putString("hargga", response.body().getData().getSelling_price());

                        bundle.putString("status", response.body().getData().getStatus());
                        bundle.putString("tagihan", utils.ConvertRP(response.body().getData().getDetail_product().getDetail().get(0).getNilai_tagihan()));
                        bundle.putString("deskription", response.body().getData().getDescription());
                        bundle.putString("admin", utils.ConvertRP(response.body().getData().getDetail_product().getDetail().get(0).getAdmin()));

                        Preference.setUrlIcon(context, "");
                        DetailTransaksiPasca fragment = new DetailTransaksiPasca(); // you fragment
                        FragmentManager fragmentManager = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                        fragment.setArguments(bundle);
//                        loadingPrimer.dismissDialog();
                        fragment.show(fragmentManager, "detail");
                    } else {
//                        loadingPrimer.dismissDialog();
                        Toast.makeText(context, response.body().getData().getStatus() + " " + response.body().getData().getDescription(), Toast.LENGTH_LONG).show();
                    }
                } else {
//                    loadingPrimer.dismissDialog();
                    Toast.makeText(context, response.body().getError(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponInquiry> call, Throwable t) {
                Toast.makeText(context,"Koneksi Tidak stabil,silahkan ulangi",Toast.LENGTH_SHORT).show();
//                loadingPrimer.dismissDialog();
            }
        });



}
}
