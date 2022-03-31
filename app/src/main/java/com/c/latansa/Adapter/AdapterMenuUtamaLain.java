package com.c.latansa.Adapter;

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

import com.c.latansa.Model.ModelMenuUtama;
import com.c.latansa.TransferBank.transferBank;
import com.c.latansa.menuUtama.AngsuranKredit.ProdukAngsuranKredit;
import com.c.latansa.menuUtama.BPJS.produkBPJS;
import com.c.latansa.menuUtama.GasNegara.produkGasnegara;
import com.c.latansa.menuUtama.Internet.InternetProduk;
import com.c.latansa.menuUtama.ListrikPLN.Pln_Produk;
import com.c.latansa.menuUtama.ListrikPLNPasca.Pln_produk_pasca;
import com.c.latansa.menuUtama.PajakPBB.produkPajakPBB;
import com.c.latansa.menuUtama.Paket.PaketData_Activity;
import com.c.latansa.menuUtama.PaketsmsTelpon.produksmstelpon;
import com.c.latansa.menuUtama.PulsaPascaBayar.PulsaPascaBayar_activity;
import com.c.latansa.menuUtama.PulsaPrabayar.PulsaPrabayar_activity;
import com.c.latansa.R;
import com.c.latansa.menuUtama.TV.tv_produk;
import com.c.latansa.menuUtama.UangElektronik.ProdukUangElektronik;
import com.c.latansa.menuUtama.Voucher.produkVoucher;
import com.c.latansa.menuUtama.VoucherGame.produkVoucherGame;
import com.c.latansa.menuUtama.WarungAbata.produkWarungAbata;
import com.c.latansa.menuUtama.air.produkair;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterMenuUtamaLain extends RecyclerView.Adapter<AdapterMenuUtamaLain.ViewHolder> {

    Context context;
    ArrayList<ModelMenuUtama> menuUtamas;

    public AdapterMenuUtamaLain(Context context, ArrayList<ModelMenuUtama> menuUtamas) {
        this.context = context;
        this.menuUtamas = menuUtamas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listmenu_utama, parent, false);
        AdapterMenuUtamaLain.ViewHolder holder = new AdapterMenuUtamaLain.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ModelMenuUtama modelMenuUtama = menuUtamas.get(position);
        Picasso.get().load(modelMenuUtama.getIcon()).into(holder.iconmenu);
        holder.titlemenu.setText(modelMenuUtama.getName());

        holder.linlistmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (modelMenuUtama.getUrl()){

                    case "pulsa_prabayar":{

                        Intent intent = new Intent(context, PulsaPrabayar_activity.class);
                        intent.putExtra("id", modelMenuUtama.getId());
                        intent.putExtra("type", modelMenuUtama.getType());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(intent);
                        break;
                    }case "paket_data": {
                        Intent intent = new Intent(context, PaketData_Activity.class);
                        intent.putExtra("id", modelMenuUtama.getId());
                        intent.putExtra("type", modelMenuUtama.getType());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    }
                    case "pulsa_pascabayar": {
                        Intent intent = new Intent(context, PulsaPascaBayar_activity.class);
                        intent.putExtra("id", modelMenuUtama.getId());
                        intent.putExtra("type", modelMenuUtama.getType());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    }
                    case "pln_prabayar": {
                        Intent intent = new Intent(context, Pln_Produk.class);
                        intent.putExtra("id", modelMenuUtama.getId());
                        intent.putExtra("type", modelMenuUtama.getType());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    }
                    case "pln_pascabayar": {
                        Intent intent = new Intent(context, Pln_produk_pasca.class);
                        intent.putExtra("id", modelMenuUtama.getId());
                        intent.putExtra("type", modelMenuUtama.getType());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    }
                    case "paket_sms_telepon": {
                        Intent intent = new Intent(context, produksmstelpon.class);
                        intent.putExtra("id", modelMenuUtama.getId());
                        intent.putExtra("type", modelMenuUtama.getType());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    }
                    case "uang_elektronik": {
                        Intent intent = new Intent(context, ProdukUangElektronik.class);
                        intent.putExtra("id", modelMenuUtama.getId());
                        intent.putExtra("type", modelMenuUtama.getType());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    }
                    case "air_pdam": {
                        Intent intent = new Intent(context, produkair.class);
                        intent.putExtra("id", modelMenuUtama.getId());
                        intent.putExtra("type", modelMenuUtama.getType());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    }
                    case "voucher_game": {
                        Intent intent = new Intent(context, produkVoucherGame.class);
                        intent.putExtra("id", modelMenuUtama.getId());
                        intent.putExtra("type", modelMenuUtama.getType());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    }
                    case "internet": {
                        Intent intent = new Intent(context, InternetProduk.class);
                        intent.putExtra("id", modelMenuUtama.getId());
                        intent.putExtra("type", modelMenuUtama.getType());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    }
                    case "tv": {
                        Intent intent = new Intent(context, tv_produk.class);
                        intent.putExtra("id", modelMenuUtama.getId());
                        intent.putExtra("type", modelMenuUtama.getType());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    }
                    case "voucher": {
                        Intent intent = new Intent(context, produkVoucher.class);
                        intent.putExtra("id", modelMenuUtama.getId());
                        intent.putExtra("type", modelMenuUtama.getType());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    }
                    case "bpjs_kesehatan": {
                        Intent intent = new Intent(context, produkBPJS.class);
                        intent.putExtra("id", modelMenuUtama.getId());
                        intent.putExtra("type", modelMenuUtama.getType());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    }
                    case "angsuran_krefit": {
                        Intent intent = new Intent(context, ProdukAngsuranKredit.class);
                        intent.putExtra("id", modelMenuUtama.getId());
                        intent.putExtra("type", modelMenuUtama.getType());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    }
                    case "pajak_pbb": {
                        Intent intent = new Intent(context, produkPajakPBB.class);
                        intent.putExtra("id", modelMenuUtama.getId());
                        intent.putExtra("type", modelMenuUtama.getType());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    }
                    case "gas_negara": {
                        Intent intent = new Intent(context, produkGasnegara.class);
                        intent.putExtra("id", modelMenuUtama.getId());
                        intent.putExtra("type", modelMenuUtama.getType());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    } case "https://shopee": {
                        Intent intent = new Intent(context, produkWarungAbata.class);
                        intent.putExtra("id", modelMenuUtama.getId());
                        intent.putExtra("type", modelMenuUtama.getType());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    }  case "transfer": {
                        Intent intent = new Intent(context, transferBank.class);
                        intent.putExtra("id", modelMenuUtama.getId());
                        intent.putExtra("type", modelMenuUtama.getType());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
                    }


                }


            }
        });

    }


    @Override
    public int getItemCount() {
        return menuUtamas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
