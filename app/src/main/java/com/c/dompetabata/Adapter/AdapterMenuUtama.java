package com.c.dompetabata.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.dompetabata.Model.ModelMenuUtama;
import com.c.dompetabata.menuUtama.PaketData.AngsuranKredit.ProdukAngsuranKredit;
import com.c.dompetabata.menuUtama.PaketData.BPJS.produkBPJS;
import com.c.dompetabata.menuUtama.PaketData.GasNegara.produkGasnegara;
import com.c.dompetabata.menuUtama.PaketData.Internet.InternetProduk;
import com.c.dompetabata.menuUtama.PaketData.ListrikPLN.Pln_Produk;
import com.c.dompetabata.menuUtama.PaketData.ListrikPLNPasca.Pln_produk_pasca;
import com.c.dompetabata.menuUtama.PaketData.PajakPBB.produkPajakPBB;
import com.c.dompetabata.menuUtama.PaketData.Paket.PaketData_Activity;
import com.c.dompetabata.menuUtama.PaketData.PaketsmsTelpon.produksmstelpon;
import com.c.dompetabata.menuUtama.PaketData.PulsaPascaBayar.PulsaPascaBayar_activity;
import com.c.dompetabata.menuUtama.PaketData.PulsaPrabayar.PulsaPrabayar_activity;
import com.c.dompetabata.R;
import com.c.dompetabata.homelainnya;
import com.c.dompetabata.menuUtama.PaketData.TV.tv_produk;
import com.c.dompetabata.menuUtama.PaketData.UangElektronik.ProdukUangElektronik;
import com.c.dompetabata.menuUtama.PaketData.Voucher.produkVoucher;
import com.c.dompetabata.menuUtama.PaketData.VoucherGame.produkVoucherGame;
import com.c.dompetabata.menuUtama.PaketData.WarungAbata.produkWarungAbata;
import com.c.dompetabata.menuUtama.PaketData.air.produkair;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterMenuUtama extends RecyclerView.Adapter<AdapterMenuUtama.ViewHolder> {

    Context context;
    ArrayList<ModelMenuUtama> menuUtamas;

    public AdapterMenuUtama(Context context, ArrayList<ModelMenuUtama> menuUtamas) {
        this.context = context;
        this.menuUtamas = menuUtamas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listmenu_utama, parent, false);
        AdapterMenuUtama.ViewHolder holder = new ViewHolder(v);
        return holder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelMenuUtama modelMenuUtama = menuUtamas.get(position);
        Picasso.get().load(modelMenuUtama.getIcon()).into(holder.iconmenu);
        holder.titlemenu.setText(modelMenuUtama.getName());
        holder.linlistmenu.setOnClickListener(v -> {

            switch (modelMenuUtama.getUrl()) {
                case "pulsa_prabayar": {
                    Intent intent = new Intent(context, PulsaPrabayar_activity.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    intent.putExtra("type", modelMenuUtama.getType());
                    context.startActivity(intent);
                    break;
                }
                case "lainnya": {
                    Intent intent = new Intent(context, homelainnya.class);
                    context.startActivity(intent);
                    break;
                }
                case "paket_data": {
                    Intent intent = new Intent(context, PaketData_Activity.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    intent.putExtra("type", modelMenuUtama.getType());
                    context.startActivity(intent);
                    break;
                }
                case "pulsa_pascabayar": {
                    Intent intent = new Intent(context, PulsaPascaBayar_activity.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    intent.putExtra("type", modelMenuUtama.getType());
                    context.startActivity(intent);
                    break;
                }
                case "pln_prabayar": {
                    Intent intent = new Intent(context, Pln_Produk.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    intent.putExtra("type", modelMenuUtama.getType());
                    context.startActivity(intent);
                    break;
                }
                case "pln_pascabayar": {
                    Intent intent = new Intent(context, Pln_produk_pasca.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    intent.putExtra("type", modelMenuUtama.getType());
                    context.startActivity(intent);
                    break;
                }
                case "paket_sms_telepon": {
                    Intent intent = new Intent(context, produksmstelpon.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    intent.putExtra("type", modelMenuUtama.getType());
                    context.startActivity(intent);
                    break;
                }
                case "uang_elektronik": {
                    Intent intent = new Intent(context, ProdukUangElektronik.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    intent.putExtra("type", modelMenuUtama.getType());
                    context.startActivity(intent);
                    break;
                }
                case "air_pdam": {
                    Intent intent = new Intent(context, produkair.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    intent.putExtra("type", modelMenuUtama.getType());
                    context.startActivity(intent);
                    break;
                }
                case "voucher_game": {
                    Intent intent = new Intent(context, produkVoucherGame.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    intent.putExtra("type", modelMenuUtama.getType());
                    context.startActivity(intent);
                    break;
                }
                case "internet": {
                    Intent intent = new Intent(context, InternetProduk.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    intent.putExtra("type", modelMenuUtama.getType());
                    context.startActivity(intent);
                    break;
                }
                case "tv": {
                    Intent intent = new Intent(context, tv_produk.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    intent.putExtra("type", modelMenuUtama.getType());
                    context.startActivity(intent);
                    break;
                }
                case "voucher": {
                    Intent intent = new Intent(context, produkVoucher.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    intent.putExtra("type", modelMenuUtama.getType());
                    context.startActivity(intent);
                    break;
                }
                case "bpjs_kesehatan": {
                    Intent intent = new Intent(context, produkBPJS.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    intent.putExtra("type", modelMenuUtama.getType());
                    context.startActivity(intent);
                    break;
                }
                case "angsuran_krefit": {
                    Intent intent = new Intent(context, ProdukAngsuranKredit.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    intent.putExtra("type", modelMenuUtama.getType());
                    context.startActivity(intent);
                    break;
                }
                case "pajak_pbb": {
                    Intent intent = new Intent(context, produkPajakPBB.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    intent.putExtra("type", modelMenuUtama.getType());
                    context.startActivity(intent);
                    break;
                }
                case "gas_negara": {
                    Intent intent = new Intent(context, produkGasnegara.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    intent.putExtra("type", modelMenuUtama.getType());
                    context.startActivity(intent);
                    break;
                } case "https://shopee": {
                    Intent intent = new Intent(context, produkWarungAbata.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    context.startActivity(intent);
                    break;
                }

            }

        });

    }

    @Override
    public int getItemCount() {
        return menuUtamas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iconmenu;
        TextView titlemenu;
        LinearLayout linlistmenu;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iconmenu = itemView.findViewById(R.id.iconmenuutama);
            titlemenu = itemView.findViewById(R.id.titlemenuutama);
            linlistmenu = itemView.findViewById(R.id.Linlistmenu);
        }
    }

}
