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
import androidx.recyclerview.widget.RecyclerView;

import com.c.dompetabata.Modal.ModalKecamatan;
import com.c.dompetabata.Model.ModelKabupaten;
import com.c.dompetabata.Model.ModelKecamatan;
import com.c.dompetabata.Model.ModelProvinsi;
import com.c.dompetabata.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterKecamatan extends RecyclerView.Adapter<AdapterKecamatan.ViewHolder> implements Filterable {

    private Context context;
    private List<ModelKecamatan> modelKecamatanList;
    private List<ModelKecamatan> modelKecamatanListFull;
    private int selectedPosition = 0;
    private ArrayList<Integer> selectCheck = new ArrayList<>();

    public AdapterKecamatan(Context context, List<ModelKecamatan> modelKecamatanList) {
        this.context = context;
        this.modelKecamatanList = modelKecamatanList;
        modelKecamatanListFull = new ArrayList<>(modelKecamatanList);

        for (int i = 0; i < modelKecamatanList.size(); i++) {
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

        ModelKecamatan modalKecamatan = modelKecamatanList.get(position);
        holder.name.setText(modalKecamatan.getName());

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

            }
        });
        holder.chekP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked==true){
                    modelKecamatanList.get(holder.getAdapterPosition()).setPilihan(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelKecamatanList.size();
    }

    @Override
    public Filter getFilter() {
        return getFilterKecamatan;
    }

    private Filter getFilterKecamatan = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelKecamatan> filterList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filterList.addAll(modelKecamatanListFull);

            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(ModelKecamatan item : modelKecamatanListFull){
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

            modelKecamatanList.clear();
            modelKecamatanList.addAll((List)results.values);
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

    public List<ModelKecamatan> getModelKecamatanList() {
        return modelKecamatanList;
    }
}
