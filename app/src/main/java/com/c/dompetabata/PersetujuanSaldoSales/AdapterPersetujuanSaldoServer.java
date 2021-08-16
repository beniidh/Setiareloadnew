package com.c.dompetabata.PersetujuanSaldoSales;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.dompetabata.Helper.utils;
import com.c.dompetabata.PengajuanLimit.MPengajuanLimit;
import com.c.dompetabata.R;

import java.util.ArrayList;

public class AdapterPersetujuanSaldoServer extends RecyclerView.Adapter<AdapterPersetujuanSaldoServer.ViewHolder>{

    Context context;
    ArrayList<ModelPersetujuanSaldo> modelPersetujuanSaldos;

    public AdapterPersetujuanSaldoServer(Context context, ArrayList<ModelPersetujuanSaldo> modelPersetujuanSaldos) {
        this.context = context;
        this.modelPersetujuanSaldos = modelPersetujuanSaldos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_persetujuandompet, parent, false);
        AdapterPersetujuanSaldoServer.ViewHolder holder = new AdapterPersetujuanSaldoServer.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelPersetujuanSaldo modelPersetujuanSaldo = modelPersetujuanSaldos.get(position);
        holder.status.setText(modelPersetujuanSaldo.getStatus());
        holder.nominal.setText(utils.ConvertRP(modelPersetujuanSaldo.getAmount()));
        String tanggal = modelPersetujuanSaldo.getUpdated_at();
        String tahun = tanggal.substring(0,4);
        String bulan = utils.convertBulan(tanggal.substring(5,7));
        String hari = tanggal.substring(8,10);
        holder.tanggal.setText(hari+" "+bulan+" "+tahun);
        holder.namakonter.setText(modelPersetujuanSaldo.getUser().getStore_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,DetailPersetujuan.class);
                intent.putExtra("ID",modelPersetujuanSaldo.getId());
                intent.putExtra("Saldo",modelPersetujuanSaldo.getAmount());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return modelPersetujuanSaldos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nominal,tanggal,status,namakonter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nominal = itemView.findViewById(R.id.nominalPersetujuan);
            namakonter = itemView.findViewById(R.id.NamaKonterPersetujuan);
            tanggal = itemView.findViewById(R.id.tanggalPersetujuan);
            status = itemView.findViewById(R.id.statusPersetujuan);
        }
    }

}
