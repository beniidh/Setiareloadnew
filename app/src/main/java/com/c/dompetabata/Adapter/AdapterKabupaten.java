package com.c.dompetabata.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.c.dompetabata.Model.ModelKabupaten;
import com.c.dompetabata.Model.ModelProvinsi;
import com.c.dompetabata.R;
import com.c.dompetabata.sharePreference.Preference;

import java.util.ArrayList;
import java.util.List;

public class AdapterKabupaten extends RecyclerView.Adapter<AdapterKabupaten.ViewHolder> implements Filterable {

    private Context context;
    private List<ModelKabupaten> modelKabupatens;
    private List<ModelKabupaten> modelKabupatensFull;
    private int selectedPosition = 0;
    private ArrayList<Integer> selectCheck = new ArrayList<>();

    public AdapterKabupaten(Context context, List<ModelKabupaten> modelKabupatens) {
        this.context = context;
        this.modelKabupatens = modelKabupatens;
        modelKabupatensFull = new ArrayList<>(modelKabupatens);

        for (int i = 0; i < modelKabupatens.size(); i++) {
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

        ModelKabupaten modelKabupaten = modelKabupatens.get(position);
        holder.name.setText(modelKabupaten.getName());


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
                Preference.getSharedPreference(context);
                Preference.setName(context,modelKabupaten.getName());
                Preference.setID(context,modelKabupaten.getId());
                Preference.setIDKabupaten(context,modelKabupaten.getId());
            }
        });
        holder.chekP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked==true){
                    modelKabupatens.get(holder.getAdapterPosition()).setPilihan(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelKabupatens.size();
    }

    @Override
    public Filter getFilter() {
        return getFilterKabupaten;
    }

    private Filter getFilterKabupaten = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelKabupaten> filterList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filterList.addAll(modelKabupatensFull);

            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(ModelKabupaten item : modelKabupatensFull){
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

            modelKabupatens.clear();
            modelKabupatens.addAll((List)results.values);
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

}
