package com.c.dompetabata.menuUtama.PaketData.UangElektronik;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.dompetabata.R;
import com.c.dompetabata.menuUtama.PaketData.air.AdapterAir;
import com.c.dompetabata.menuUtama.PaketData.air.MAir;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class ModalUangElektronik extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    AdapterUangElektronik adapterUangElektronik;
    ArrayList<MUangElektronik> mUangElektroniks = new ArrayList<>();
    Button pilih,tutup;
    SearchView search;

    private BottomSheetListenerProduksms bottomSheetListenerProduksms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_uangelektronik, container, false);

        mUangElektroniks.add(new MUangElektronik("1","Go PAY"));
        mUangElektroniks.add(new MUangElektronik("2","OVO"));
        mUangElektroniks.add(new MUangElektronik("3","GRAB"));
        mUangElektroniks.add(new MUangElektronik("4","DANA"));

        recyclerView = v.findViewById(R.id.ReyUangElektronik);
        adapterUangElektronik = new AdapterUangElektronik(getContext(), mUangElektroniks);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterUangElektronik);

        pilih = v.findViewById(R.id.pilihUangElektronik);
        tutup = v.findViewById(R.id.tutupUangElektronik);

        pilih.setOnClickListener(v1 -> {


            String nameid[][] = adapterUangElektronik.getNameid();

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


        search = v.findViewById(R.id.search_uangelektronik);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.onActionViewExpanded();
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterUangElektronik.getFilter().filter(newText);
                return false;
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
            bottomSheetListenerProduksms = (ModalUangElektronik.BottomSheetListenerProduksms) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement bottomsheet Listener");
        }
    }
}
