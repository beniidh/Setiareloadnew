package com.c.latansa.CetakStruk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.latansa.Helper.utils;
import com.c.latansa.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterCetakStruk extends RecyclerView.Adapter<AdapterCetakStruk.ViewHolder> implements Filterable {
    Context context;
    ArrayList<ResponStruk.DataTransaksi> mData;
    ArrayList<ResponStruk.DataTransaksi> mDatafull;

    public AdapterCetakStruk(Context context, ArrayList<ResponStruk.DataTransaksi> mData) {
        this.context = context;
        this.mData = mData;
        mDatafull = new ArrayList<>(mData);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_transaksiku, parent, false);
        AdapterCetakStruk.ViewHolder holder = new AdapterCetakStruk.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ResponStruk.DataTransaksi dataTransaksi = mData.get(position);
        holder.status.setText(dataTransaksi.getStatus());
        holder.harga.setText(dataTransaksi.getTotal_price());
        holder.transaksi.setText(dataTransaksi.getId());
        String tanggal = dataTransaksi.getUpdated_at();
        String tahun = tanggal.substring(0,4);
        String bulan = utils.convertBulan(tanggal.substring(5,7));
        String hari = tanggal.substring(8,10);
        holder.tanggal.setText(hari+" "+bulan+" "+tahun);
        holder.produk.setText(dataTransaksi.getProduct_name());

        holder.linearklik.setOnClickListener(v -> {

            Intent intent = new Intent(context, DetailTransaksiTruk.class);
            Bundle extras = new Bundle();
            extras.putString("transaksid",dataTransaksi.getId());
            extras.putString("nomor",dataTransaksi.getCustomer_no());
            extras.putString("produk",dataTransaksi.getProduct_name());
            extras.putString("harga",dataTransaksi.getTotal_price());
            extras.putString("sn",dataTransaksi.getSn());
            String jam = tanggal.substring(11, 16);
            extras.putString("tanggal",hari + " " + bulan + " " + tahun);
            extras.putString("waktu",jam);
            intent.putExtras(extras);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);


        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        return getFilterKabupaten;
    }
    private Filter getFilterKabupaten = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ResponStruk.DataTransaksi> filterList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filterList.addAll(mDatafull);

            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(ResponStruk.DataTransaksi item : mDatafull){
                    if(item.getCustomer_no().toLowerCase().contains(filterPattern)){
                        filterList.add(item);

                    }
                }

            }
            FilterResults results = new FilterResults();
            results.values = filterList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            mData.clear();
            mData.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };






    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView transaksi, tanggal, produk,harga,status;
        LinearLayout linearklik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            transaksi = itemView.findViewById(R.id.TransaksiFSSS);
            tanggal = itemView.findViewById(R.id.TanggalFSSS);
            harga = itemView.findViewById(R.id.HargaFSSS);
            linearklik = itemView.findViewById(R.id.lintesstS);
            produk = itemView.findViewById(R.id.ProdukFSSS);
            status = itemView.findViewById(R.id.StatusFSSS);

        }
    }
}
