package com.c.latansa.menuUtama.GasNegara;

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

public class AdapterProdukGasnegara extends RecyclerView.Adapter<AdapterProdukGasnegara.ViewHolder> {

    Context context;
    ArrayList<ResponProdukGasnegara.VoucherData> mVoucherProduk;
    String nomor, type;

    public AdapterProdukGasnegara(Context context, ArrayList<ResponProdukGasnegara.VoucherData> mVoucherProduk, String nomor, String type) {
        this.context = context;
        this.mVoucherProduk = mVoucherProduk;
        this.nomor = nomor;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk_air, parent, false);
        AdapterProdukGasnegara.ViewHolder holder = new AdapterProdukGasnegara.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ResponProdukGasnegara.VoucherData mVoucherData = mVoucherProduk.get(position);
        holder.name.setText(mVoucherData.getName());
        holder.deskripsi.setText(mVoucherData.getDescription());


        holder.linearklik.setOnClickListener(v -> {

            GpsTracker gpsTracker = new GpsTracker(context);

            Api api = RetroClient.getApiServices();
            MInquiry mInquiry = new MInquiry(mVoucherData.getCode(), nomor, type, Value.getMacAddress(context), Value.getIPaddress(), Value.getUserAgent(context), gpsTracker.getLatitude(), gpsTracker.getLongitude());
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
                            fragment.show(fragmentManager, "detail");
                        } else {

                            Toast.makeText(context, response.body().getData().getStatus() + " " + response.body().getData().getDescription(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, response.body().getError(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<ResponInquiry> call, Throwable t) {
                    Toast.makeText(context,t.toString(),Toast.LENGTH_SHORT).show();

                }
            });

        });


    }

    @Override
    public int getItemCount() {
        return mVoucherProduk.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, deskripsi;
        LinearLayout linearklik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameair);
            deskripsi = itemView.findViewById(R.id.deskriair);
            linearklik = itemView.findViewById(R.id.linearklikair);

        }
    }
}
