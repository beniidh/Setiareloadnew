package com.c.setiareload.DaftarHarga;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.setiareload.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterProdukDH extends RecyclerView.Adapter<AdapterProdukDH.ViewHolder> implements Filterable {

    private Context context;
    private List<ResponProdukDH.mData> mProduk;
    private List<ResponProdukDH.mData> mProdukFull;
    private int selectedPosition = 0;
    public static   String nameid[][] = new String[1][2];
    private ArrayList<Integer> selectCheck = new ArrayList<>();

    public AdapterProdukDH(Context context, List<ResponProdukDH.mData> mProduk) {
        this.context = context;
        this.mProduk = mProduk;
        mProdukFull = new ArrayList<>(mProduk);

        for (int i = 0; i < mProduk.size(); i++) {
            selectCheck.add(0);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ResponProdukDH.mData mproduk = mProduk.get(position);
        holder.name.setText(mproduk.getName());


        if (selectCheck.get(position) == 1) {
            holder.chekP.setChecked(true);
        } else {
            holder.chekP.setChecked(false);
        }

        holder.chekP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int k=0; k<selectCheck.size(); k++) {
                    if(k==position) {
                        selectCheck.set(k,1);
                    } else {
                        selectCheck.set(k,0);
                    }
                }
                notifyDataSetChanged();
                nameid[0][0] = mproduk.getName();
                nameid[0][1] = mproduk.getId();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mProduk.size();
    }

    @Override
    public Filter getFilter() {
        return getFilterKabupaten;
    }

    private Filter getFilterKabupaten = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ResponProdukDH.mData> filterList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filterList.addAll(mProdukFull);

            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(ResponProdukDH.mData item : mProdukFull){
                    if(item.getName().toLowerCase().contains(filterPattern)){
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

            mProduk.clear();
            mProduk.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder{
     TextView name;
     CheckBox chekP;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameList);
            chekP = itemView.findViewById(R.id.chekProvinsi);

        }
    }

    public static String[][] getNameid() {
        return nameid;
    }
}
