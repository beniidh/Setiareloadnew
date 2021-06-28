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
import com.c.dompetabata.PulsaPrabayar.PulsaPrabayar_activity;
import com.c.dompetabata.R;
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

                if (modelMenuUtama.getUrl().equals("pulsa_prabayar")) {

                    Intent intent = new Intent(context, PulsaPrabayar_activity.class);
                    intent.putExtra("id", modelMenuUtama.getId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
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
