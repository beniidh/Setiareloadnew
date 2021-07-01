package com.c.dompetabata.menuUtama.PaketData.VoucherGame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.c.dompetabata.R;
import com.c.dompetabata.sharePreference.Preference;

import java.util.ArrayList;

public class AdapterVoucherGame extends RecyclerView.Adapter<AdapterVoucherGame.ViewHolder> {

    Context context;
    ArrayList<MVoucherGame> mVoucherGames;
    private int selectedPosition = 0;
    ModalVoucherGame voucher;
    private ArrayList<Integer> selectCheck = new ArrayList<>();
    public AdapterVoucherGame(Context context, ArrayList<MVoucherGame> mVoucherGames,ModalVoucherGame modalVoucherGame) {
        this.context = context;
        this.mVoucherGames = mVoucherGames;
        this.voucher = modalVoucherGame;

        for (int i = 0; i < mVoucherGames.size(); i++) {
            selectCheck.add(0);
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listvouchergame, parent, false);
        AdapterVoucherGame.ViewHolder holder = new AdapterVoucherGame.ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        MVoucherGame modalVoucherGame = mVoucherGames.get(position);
        holder.name.setText(modalVoucherGame.getName());

//        holder.linklik.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                voucher.dismiss();
//            }
//        });


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
//                Preference.setName(context,modelKabupaten.getName());
//                Preference.setID(context,modelKabupaten.getId());
//                Preference.setIDKabupaten(context,modelKabupaten.getId());
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
        return mVoucherGames.size();
    }


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
}
