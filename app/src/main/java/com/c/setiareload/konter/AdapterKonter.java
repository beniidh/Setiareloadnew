package com.c.setiareload.konter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.c.setiareload.Adapter.AdapterSubMenuSide;
import com.c.setiareload.R;

import java.util.ArrayList;

public class AdapterKonter extends RecyclerView.Adapter<AdapterKonter.ViewHolder> {


    Context context;
    ArrayList<Mkonter.mData> data;

    public AdapterKonter(Context context, ArrayList<Mkonter.mData> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_konter, parent, false);
        AdapterKonter.ViewHolder holder = new AdapterKonter.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.namakonter.setText( data.get(position).getStore_name());
        holder.code.setText(data.get(position).getCode());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView namakonter, code,KonterProfil;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namakonter = itemView.findViewById(R.id.sales_namakonter);
            code = itemView.findViewById(R.id.sales_codekonter);
            KonterProfil = itemView.findViewById(R.id.KonterProfil);
            Typeface type = ResourcesCompat.getFont(context, R.font.abata);
            KonterProfil.setTypeface(type);


        }
    }
}
