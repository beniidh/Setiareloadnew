package com.c.latansa.menuUtama.HolderPulsa;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.latansa.Api.Api;
import com.c.latansa.Helper.RetroClient;
import com.c.latansa.R;
import com.c.latansa.sharePreference.Preference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterHolder extends RecyclerView.Adapter<AdapterHolder.ViewHolder> {
    Context context;
    ArrayList<ResponSub.mData> datasub;
    holder_pulsa_activity holderPulsaActivity;

    public AdapterHolder(Context context, ArrayList<ResponSub.mData> datasub, holder_pulsa_activity holderPulsaActivity) {
        this.context = context;
        this.datasub = datasub;
        this.holderPulsaActivity = holderPulsaActivity;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_subcategory, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ResponSub.mData data = datasub.get(position);
        holder.namesub.setText(data.getName());

        if (!data.getIcon().isEmpty()) {

            Picasso.get().load(data.getIcon()).into(holder.iconsub);
        } else {

            Picasso.get().load("http://").into(holder.iconsub);
        }


        holder.linierSubCategory.setOnClickListener(v -> {
            if (Preference.getNoType(context).equals("PASCABAYAR")) {
                Intent intent = new Intent(context, produkholderPasca.class);
                intent.putExtra("id", data.getId());
                intent.putExtra("name", data.getName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
                chekGroup(data.getId(), data.getName(),position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return datasub.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linierSubCategory;
        TextView namesub;
        ImageView iconsub;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linierSubCategory = itemView.findViewById(R.id.linierSubCategory);
            namesub = itemView.findViewById(R.id.namesub);
            iconsub = itemView.findViewById(R.id.iconsub);

        }
    }

    public void chekGroup(String id, String nama, int position) {

        String token = "Bearer " + Preference.getToken(context.getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponGroup> call = api.getProdukGroup(token, id);
        call.enqueue(new Callback<ResponGroup>() {
            @Override
            public void onResponse(Call<ResponGroup> call, Response<ResponGroup> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {

               Intent intent = new Intent(context,groupHolder_Activity.class);
               intent.putExtra("id",id);
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(intent);

                } else if (code.equals("400")) {

                    Intent intent = new Intent(context, produkholder.class);
                    intent.putExtra("id", id);
                    intent.putExtra("jenis","nosub");
                    intent.putExtra("name", nama);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            }
            @Override
            public void onFailure(Call<ResponGroup> call, Throwable t) {

            }
        });

    }
}
