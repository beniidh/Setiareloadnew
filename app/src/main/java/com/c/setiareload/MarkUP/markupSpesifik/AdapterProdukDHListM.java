package com.c.setiareload.MarkUP.markupSpesifik;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Helper.utils;
import com.c.setiareload.R;
import com.c.setiareload.Respon.ResponProfil;
import com.c.setiareload.SaldoServer.AjukanLimit;
import com.c.setiareload.SaldoServer.TopupSaldoServer;
import com.c.setiareload.menuUtama.PaketData.PulsaPrabayar.DetailTransaksiPulsaPra;
import com.c.setiareload.sharePreference.Preference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterProdukDHListM extends RecyclerView.Adapter<AdapterProdukDHListM.ViewHolder> {
String sales_code;
    Context context;
    ArrayList<ResponProdukListM.mData> mData;

    public AdapterProdukDHListM(Context context, ArrayList<ResponProdukListM.mData> mData) {
        this.context = context;
        this.mData = mData;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_daftarhargamarkup, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        getContentProfil();

        ResponProdukListM.mData mdata = mData.get(position);
        holder.name.setText(mdata.getName());
        holder.harga.setText(utils.ConvertRP(mdata.getBasic_price()));

        holder.ButtonMarkup.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            ModalInputSpesifikMarkup fragment = new ModalInputSpesifikMarkup(); // you fragment
            bundle.putString("user_id", mdata.getUser_id());
            bundle.putString("id", mdata.getId());
            bundle.putString("sales_code", getSales_code());
            bundle.putString("name", mdata.getName());
            bundle.putString("product_id", mdata.getProduct_id());
            bundle.putString("status", mdata.getStatus());
            bundle.putString("server_code", mdata.getServer_code());
            FragmentManager fragmentManager = ((FragmentActivity) view.getContext()).getSupportFragmentManager();
            fragment.setArguments(bundle);
            fragment.show(fragmentManager, "detail");

        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, harga;
        Button ButtonMarkup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.namaProdukDH);
            harga = itemView.findViewById(R.id.hargaProdukDH);
            ButtonMarkup = itemView.findViewById(R.id.ButtonMarkup);
        }
    }

    public void getContentProfil() {

        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(context));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(Call<ResponProfil> call, Response<ResponProfil> response) {

                String sales_code = response.body().getData().getReferal_code();
                setSales_code(sales_code);

            }

            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {

            }
        });
    }

    public String getSales_code() {
        return sales_code;
    }

    public void setSales_code(String sales_code) {
        this.sales_code = sales_code;
    }
}
