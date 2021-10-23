package com.c.setiareload.menuUtama.PaketData.PaketsmsTelpon;

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
import com.c.setiareload.menuUtama.PaketData.VoucherGame.ModalVoucherGame;
import com.c.setiareload.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterProdukST extends RecyclerView.Adapter<AdapterProdukST.ViewHolder> {

    Context context;
    ArrayList<MProdukSmsTelpon> msmsTelpons;
    private int selectedPosition = 0;
    public static   String nameid[][] = new String[1][2];
    ModalVoucherGame voucher;
    String nomor,urll;
    private ArrayList<Integer> selectCheck = new ArrayList<>();
    public AdapterProdukST(Context context, ArrayList<MProdukSmsTelpon> msmsTelpons,String nomor,String urll) {
        this.context = context;
        this.msmsTelpons = msmsTelpons;
        this.nomor = nomor;
        this.urll = urll;


    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_produk_pulsapraayar, parent, false);
        AdapterProdukST.ViewHolder holder = new AdapterProdukST.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MProdukSmsTelpon msmsTelpon = msmsTelpons.get(position);
        holder.name.setText(msmsTelpon.getName());
        holder.deskripsi.setText(msmsTelpon.getDescription());
        holder.harga.setText(utils.ConvertRP(msmsTelpon.getTotal_price()));
        holder.linearklik.setOnClickListener(v -> {

            GpsTracker gpsTracker = new GpsTracker(context);
            Api api = RetroClient.getApiServices();
            MInquiry mInquiry = new MInquiry(msmsTelpon.getCode(), nomor, "PRABAYAR", Value.getMacAddress(context), Value.getIPaddress(), Value.getUserAgent(context), gpsTracker.getLatitude(), gpsTracker.getLongitude());
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
                        bundle.putString("urlicon", urll);
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
        return msmsTelpons.size();
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

    public static String[][] getNameid() {
        return nameid;
    }
}
