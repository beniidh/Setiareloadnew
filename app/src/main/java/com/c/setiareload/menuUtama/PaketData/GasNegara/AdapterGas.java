package com.c.setiareload.menuUtama.PaketData.GasNegara;

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

public class AdapterGas extends RecyclerView.Adapter<AdapterGas.ViewHolder> implements Filterable {

    Context context;
    ArrayList<ResponGasnegara.mData> modelPajaks;
    ArrayList<ResponGasnegara.mData> modelPajaksfull;
    private int selectedPosition = 0;
    public static   String nameid[][] = new String[1][2];
    private ArrayList<Integer> selectCheck = new ArrayList<>();

    public AdapterGas(Context context, ArrayList<ResponGasnegara.mData> modelPajaks) {
        this.context = context;
        this.modelPajaks = modelPajaks;
        modelPajaksfull = new ArrayList<>(modelPajaks);

        for (int i = 0; i < modelPajaks.size(); i++) {
            selectCheck.add(0);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
        AdapterGas.ViewHolder holder = new AdapterGas.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ResponGasnegara.mData modelPajak = modelPajaks.get(position);
        holder.name.setText(modelPajak.getName());


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
                nameid[0][0] = modelPajak.getName();
                nameid[0][1] = modelPajak.getId();


            }
        });

//        holder.chekP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if(isChecked==true){
//                    modelKabupatens.get(holder.getAdapterPosition()).setPilihan(true);
//                }
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return modelPajaks.size();
    }

    @Override
    public Filter getFilter() {
        return getFilterable;
    }

    private Filter getFilterable = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ResponGasnegara.mData> filterList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filterList.addAll(modelPajaksfull);

            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ResponGasnegara.mData item : modelPajaksfull) {
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
            modelPajaks.clear();
            modelPajaks.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        CheckBox chekP;
        LinearLayout linklik;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameList);
            chekP = itemView.findViewById(R.id.chekProvinsi);
//            linklik = itemView.findViewById(R.id.linklik);

        }
    }

    public static String[][] getNameid() {
        return nameid;
    }
}
