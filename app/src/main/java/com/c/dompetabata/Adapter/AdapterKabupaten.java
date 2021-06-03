package com.c.dompetabata.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c.dompetabata.Model.ModelProvinsi;
import com.c.dompetabata.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterKabupaten extends RecyclerView.Adapter<AdapterKabupaten.ViewHolder> {

    private Context context;
    private List<ModelProvinsi> modelProvinsiList;
    private int selectedPosition = 0;
    private ArrayList<Integer> selectCheck = new ArrayList<>();

    public AdapterKabupaten(Context context, List<ModelProvinsi> modelProvinsiList) {
        this.context = context;
        this.modelProvinsiList = modelProvinsiList;

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

        ModelProvinsi modelProvinsi = modelProvinsiList.get(position);
        holder.name.setText(modelProvinsi.getName());

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
                   modelProvinsi.setaBoolean(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelProvinsiList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
     TextView name;
     CheckBox chekP;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.nameList);
            chekP = itemView.findViewById(R.id.chekProvinsi);

        }
    }

    public  List<ModelProvinsi> getModelProvinsiList() {
        return modelProvinsiList;
    }
}
