package com.c.dompetabata.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.dompetabata.Model.ModelProvinsi;
import com.c.dompetabata.R;
import com.c.dompetabata.sharePreference.Preference;

import java.util.ArrayList;
import java.util.List;

public class AdapterProvinsi extends RecyclerView.Adapter<AdapterProvinsi.ViewHolder> implements Filterable {

    private Context context;
    private List<ModelProvinsi> modelProvinsiList;
    private List<ModelProvinsi> modelProvinsisfull;
    private int selectedPosition = 0;
    private ArrayList<Integer> selectCheck = new ArrayList<>();
    public AdapterProvinsi(Context context, List<ModelProvinsi> modelProvinsiList) {
        this.context = context;
        this.modelProvinsiList = modelProvinsiList;
        modelProvinsisfull = new ArrayList<>(modelProvinsiList);

        for (int i = 0; i < modelProvinsiList.size(); i++) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (selectCheck.get(position) == 1) {
            holder.chekP.setChecked(true);
        } else {
            holder.chekP.setChecked(false);
        }

        ModelProvinsi modelProvinsi = modelProvinsiList.get(position);
        holder.name.setText(modelProvinsi.getName());
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
                Preference.getSharedPreference(context);
                Preference.setName(context,modelProvinsi.getName());
                Preference.setID(context,modelProvinsi.getId());
                Preference.setIDProvinsi(context,modelProvinsi.getId());
            }

        });

    }

    @Override
    public int getItemCount() {
        return modelProvinsiList.size();
    }

    @Override
    public Filter getFilter() {
        return getFilterable;
    }

    private Filter getFilterable = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelProvinsi> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(modelProvinsisfull);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ModelProvinsi item : modelProvinsisfull) {
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
            modelProvinsiList.clear();
            modelProvinsiList.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CheckBox chekP;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameList);
            chekP = itemView.findViewById(R.id.chekProvinsi);

        }
    }

}
