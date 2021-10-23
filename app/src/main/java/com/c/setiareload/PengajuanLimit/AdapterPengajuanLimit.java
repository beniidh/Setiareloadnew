package com.c.setiareload.PengajuanLimit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.setiareload.Helper.utils;
import com.c.setiareload.R;

import java.util.ArrayList;

public class AdapterPengajuanLimit extends RecyclerView.Adapter<AdapterPengajuanLimit.ViewHolder>{

    Context context;
    ArrayList<MPengajuanLimit> mPengajuanLimits;

    public AdapterPengajuanLimit(Context context, ArrayList<MPengajuanLimit> mPengajuanLimits) {
        this.context = context;
        this.mPengajuanLimits = mPengajuanLimits;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pengajuandompet, parent, false);
        AdapterPengajuanLimit.ViewHolder holder = new AdapterPengajuanLimit.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MPengajuanLimit mPengajuanLimit = mPengajuanLimits.get(position);
        holder.status.setText(mPengajuanLimit.getStatus());
        holder.nominal.setText(utils.ConvertRP(mPengajuanLimit.getAmount()));
        String tanggal = mPengajuanLimit.getUpdated_at();
        String tahun = tanggal.substring(0,4);
        String bulan = utils.convertBulan(tanggal.substring(5,7));
        String hari = tanggal.substring(8,10);
        holder.tanggal.setText(hari+" "+bulan+" "+tahun);


    }

    @Override
    public int getItemCount() {
        return mPengajuanLimits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nominal,tanggal,status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nominal = itemView.findViewById(R.id.nominalPengajuanlimit);
            tanggal = itemView.findViewById(R.id.tanggalpengajuanLimit);
            status = itemView.findViewById(R.id.statuspengajuan);
        }
    }
}
