package com.c.dompetabata.menuUtama.PaketData.Internet;

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
import com.c.dompetabata.menuUtama.PaketData.UangElektronik.AdapterUangElektronik;
import com.c.dompetabata.menuUtama.PaketData.UangElektronik.MUangElektronik;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class ModalInternet extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    AdapterInternet adapterInternet;
    ArrayList<ModelInternet> modelInternets = new ArrayList<>();
    Button pilih,tutup;
    SearchView search;

    private BottomSheetListenerProduksms bottomSheetListenerProduksms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_internet, container, false);

        modelInternets.add(new ModelInternet("1","CVN"));
        modelInternets.add(new ModelInternet("2","INDIHOME"));
        modelInternets.add(new ModelInternet("3","SPEEDY"));
        modelInternets.add(new ModelInternet("4","BIZNET"));

        recyclerView = v.findViewById(R.id.ReyInternet);
        adapterInternet = new AdapterInternet(getContext(), modelInternets);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterInternet);

        pilih = v.findViewById(R.id.pilihInternet);
        tutup = v.findViewById(R.id.tutupInternet);

        pilih.setOnClickListener(v1 -> {


            String nameid[][] = adapterInternet.getNameid();

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


        search = v.findViewById(R.id.search_internet);
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

                adapterInternet.getFilter().filter(newText);
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
            bottomSheetListenerProduksms = (ModalInternet.BottomSheetListenerProduksms) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement bottomsheet Listener");
        }
    }
}
