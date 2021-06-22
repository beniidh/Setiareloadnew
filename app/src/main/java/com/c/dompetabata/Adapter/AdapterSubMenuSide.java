package com.c.dompetabata.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.dompetabata.Model.MSubMenu;
import com.c.dompetabata.Model.ModelKelurahan;
import com.c.dompetabata.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterSubMenuSide extends RecyclerView.Adapter<AdapterSubMenuSide.ViewHolder> {

Context context;
    private List<MSubMenu> subMenus;

    public AdapterSubMenuSide(Context context, ArrayList<MSubMenu> subMenus) {
        this.context = context;
        this.subMenus = subMenus;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listmenu_sidebar, parent, false);
        AdapterSubMenuSide.ViewHolder holder = new AdapterSubMenuSide.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MSubMenu mSubMenu = subMenus.get(position);
        holder.namesub.setText(mSubMenu.getName());
        Picasso.get().load(mSubMenu.getIcon()).into(holder.iconsub);


    }

    @Override
    public int getItemCount() {
        return subMenus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namesub;
        ImageView iconsub;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            namesub = itemView.findViewById(R.id.name_list_sub);
            iconsub = itemView.findViewById(R.id.icon_list_sub);
        }
    }
}


