package com.c.dompetabata.menuUtama.PaketData.PaketsmsTelpon;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.dompetabata.R;
import com.c.dompetabata.menuUtama.PaketData.VoucherGame.AdapterVoucherGame;
import com.c.dompetabata.menuUtama.PaketData.VoucherGame.ModalVoucherGame;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class ModalProdukSmsTelpon extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    AdapterProdukSmsTelpon adapterProdukSmsTelpon;
    ArrayList<MsmsTelpon> msmsTelpons = new ArrayList<>();
    Button pilih,tutup;
    private BottomSheetListenerProduksms bottomSheetListenerProduksms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_produksmspulsa, container, false);


        msmsTelpons.add(new MsmsTelpon("1","Telkomsel"));
        msmsTelpons.add(new MsmsTelpon("2","Indosat"));
        msmsTelpons.add(new MsmsTelpon("3","Axis"));
        msmsTelpons.add(new MsmsTelpon("4","Smartfren"));

        recyclerView = v.findViewById(R.id.RecycleProduksmsTelpon);
        adapterProdukSmsTelpon = new AdapterProdukSmsTelpon(getContext(), msmsTelpons);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterProdukSmsTelpon);

        pilih = v.findViewById(R.id.pilihProdukSmsTelpon);
        tutup = v.findViewById(R.id.tutupProdukSmsTelpon);

        pilih.setOnClickListener(v1 -> {


            String nameid[][] = adapterProdukSmsTelpon.getNameid();

            String namee = nameid[0][0];
            String id = nameid[0][1];

            bottomSheetListenerProduksms.onButtonClick(namee,id);
            dismiss();
        });

        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }

    public interface BottomSheetListenerProduksms {
        void onButtonClick(String name, String id);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            bottomSheetListenerProduksms = (ModalProdukSmsTelpon.BottomSheetListenerProduksms) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement bottomsheet Listener");
        }
    }
}
