package com.c.setiareload.TransferBank;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.setiareload.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterGetBank extends RecyclerView.Adapter<AdapterGetBank.ViewHolder> implements Filterable {

    private Context context;
    private List<ModelNamaBank.mNama> modelNamaBanks;
    public static String nameid[][] = new String[1][2];
    private List<ModelNamaBank.mNama> modelNamaBanksfull;
    private int selectedPosition = 0;
    private ArrayList<Integer> selectCheck = new ArrayList<>();

    public AdapterGetBank(Context context, List<ModelNamaBank.mNama> modelNamaBanks) {
        this.context = context;
        this.modelNamaBanks = modelNamaBanks;
        modelNamaBanksfull = new ArrayList<>(modelNamaBanks);

        for (int i = 0; i < modelNamaBanks.size(); i++) {
            selectCheck.add(0);
        }
    }

    public AdapterGetBank(Context context) {
        this.context = context;
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
        if (selectCheck.get(position) == 1) {
            holder.chekP.setChecked(true);
        } else {
            holder.chekP.setChecked(false);
        }

        ModelNamaBank.mNama modelnamabank = modelNamaBanks.get(position);
        holder.name.setText(modelnamabank.getName());
        holder.klik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for (int k = 0; k < selectCheck.size(); k++) {
                    if (k == position) {
                        selectCheck.set(k, 1);
                    } else {
                        selectCheck.set(k, 0);
                    }
                }
                notifyDataSetChanged();

                nameid[0][0] = modelnamabank.getName();
                nameid[0][1] = modelnamabank.getCode();


            }

        });

    }

    @Override
    public int getItemCount() {
        return modelNamaBanks.size();
    }

    @Override
    public Filter getFilter() {
        return getFilterable;
    }

    private Filter getFilterable = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelNamaBank.mNama> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(modelNamaBanksfull);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ModelNamaBank.mNama item : modelNamaBanksfull) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
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
            modelNamaBanks.clear();
            modelNamaBanks.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CheckBox chekP;
        LinearLayout klik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameList);
            chekP = itemView.findViewById(R.id.chekProvinsi);
            klik = itemView.findViewById(R.id.linearKlikk);


        }
    }


    public static String[][] getNameid() {
        return nameid;
    }
}
