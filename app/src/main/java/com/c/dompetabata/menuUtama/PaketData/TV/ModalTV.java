package com.c.dompetabata.menuUtama.PaketData.TV;

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
import com.c.dompetabata.menuUtama.PaketData.Internet.AdapterInternet;
import com.c.dompetabata.menuUtama.PaketData.Internet.ModelInternet;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class ModalTV extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    AdapterTV adapterTV;
    ArrayList<ModelTV> modelTVS = new ArrayList<>();
    Button pilih,tutup;
    SearchView search;

    private BottomSheetListenerProduksms bottomSheetListenerProduksms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_tv, container, false);

        modelTVS.add(new ModelTV("1","INDOVISION"));
        modelTVS.add(new ModelTV("2","TELKOMVISION"));


        recyclerView = v.findViewById(R.id.ReyTV);
        adapterTV = new AdapterTV(getContext(), modelTVS);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterTV);

        pilih = v.findViewById(R.id.pilihTV);
        tutup = v.findViewById(R.id.tutupTV);

        pilih.setOnClickListener(v1 -> {


            String nameid[][] = adapterTV.getNameid();

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


        search = v.findViewById(R.id.search_tv);
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

                adapterTV.getFilter().filter(newText);
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
            bottomSheetListenerProduksms = (ModalTV.BottomSheetListenerProduksms) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement bottomsheet Listener");
        }
    }
}
