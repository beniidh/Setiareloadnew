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

import com.c.dompetabata.Modal.ModalKelurahan;
import com.c.dompetabata.Model.ModelKelurahan;
import com.c.dompetabata.Model.ModelProvinsi;
import com.c.dompetabata.R;
import com.c.dompetabata.sharePreference.Preference;

import java.util.ArrayList;
import java.util.List;

public class AdapterKelurahan extends RecyclerView.Adapter<AdapterKelurahan.ViewHolder> implements Filterable {

    private Context context;
    private List<ModelKelurahan> modelKelurahans;
    private List<ModelKelurahan> modelKelurahansfull;
    private int selectedPosition = 0;
    private ArrayList<Integer> selectCheck = new ArrayList<>();



    public AdapterKelurahan(Context context, List<ModelKelurahan> modelKelurahans) {
        this.context = context;
        this.modelKelurahans = modelKelurahans;
        modelKelurahansfull = new ArrayList<>(modelKelurahans);

        for (int i = 0; i < modelKelurahans.size(); i++) {
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


        ModelKelurahan modelKelurahan = modelKelurahans.get(position);
        holder.name.setText(modelKelurahan.getName());


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
                Preference.setName(context,modelKelurahan.getName());
                Preference.setID(context,modelKelurahan.getId());
                Preference.setIDKelurahan(context,modelKelurahan.getId());


            }

        });




    }

    @Override
    public int getItemCount() {
        return modelKelurahans.size();
    }

    @Override
    public Filter getFilter() {
        return getFilterable;
    }

    private Filter getFilterable = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ModelKelurahan> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(modelKelurahansfull);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ModelKelurahan item : modelKelurahansfull) {
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
            modelKelurahans.clear();
            modelKelurahans.addAll((List) results.values);
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
