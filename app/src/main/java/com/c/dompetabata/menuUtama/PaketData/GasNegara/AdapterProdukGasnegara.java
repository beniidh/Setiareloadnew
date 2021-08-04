package com.c.dompetabata.menuUtama.PaketData.GasNegara;

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
import com.c.dompetabata.R;
import com.c.dompetabata.Transaksi.MInquiry;
import com.c.dompetabata.Transaksi.ResponInquiry;
import com.c.dompetabata.menuUtama.PaketData.PajakPBB.ResponProdukPBB;
import com.c.dompetabata.menuUtama.PaketData.PulsaPrabayar.DetailTransaksiPulsaPra;
import com.c.dompetabata.menuUtama.PaketData.TransaksiHandle.DetailTransaksiPasca;
import com.c.dompetabata.sharePreference.Preference;

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
            MInquiry mInquiry = new MInquiry(mVoucherData.getCode(), nomor, type, Value.getMacAddress(), Value.getIPaddress(), Value.getUserAgent(context), gpsTracker.getLatitude(), gpsTracker.getLongitude());
            String token = "Bearer " + Preference.getToken(context);
            Call<ResponInquiry> call = api.CekInquiry(token, mInquiry);
            call.enqueue(new Callback<ResponInquiry>() {
                @Override
                public void onResponse(Call<ResponInquiry> call, Response<ResponInquiry> response) {

                    String code = response.body().getCode();
                    if (code.equals("200")) {

                        Bundle bundle = new Bundle();
                        bundle.putString("deskripsi", mVoucherData.getDescription());
                        bundle.putString("nomorr", nomor);
                        bundle.putString("namecustomer", response.body().getData().getCustomer_name());
                        bundle.putString("RefID", response.body().getData().getRef_id());
                        bundle.putString("sku_code", response.body().getData().getBuyer_sku_code());
                        bundle.putString("kodeproduk", "pulsapasca");
                        bundle.putString("inquiry", response.body().getData().getInquiry_type());
                        bundle.putString("hargga", response.body().getData().getSelling_price());

                        bundle.putString("status",response.body().getData().getStatus());
                        bundle.putString("tagihan",response.body().getData().getDetail_product().getLembar_tagihan());
                        bundle.putString("deskription", response.body().getData().getDescription());

                        Preference.setUrlIcon(context, "");
                        DetailTransaksiPasca fragment = new DetailTransaksiPasca(); // you fragment
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
