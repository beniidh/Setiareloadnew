package com.c.dompetabata.menuUtama.PaketData.AngsuranKredit;

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
import com.c.dompetabata.menuUtama.PaketData.Voucher.AdapterVoucher;
import com.c.dompetabata.menuUtama.PaketData.Voucher.ModelVoucher;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class ModalAngsuran extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    AdapterAngsuran adapterAngsuran;
    ArrayList<ModelAngsuran> modelAngsurans = new ArrayList<>();
    Button pilih,tutup;
    SearchView search;

    private BottomSheetListenerProduksms bottomSheetListenerProduksms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_angsuran, container, false);

        modelAngsurans.add(new ModelAngsuran("1","INDOVISION"));
        modelAngsurans.add(new ModelAngsuran("2","TELKOMVISION"));


        recyclerView = v.findViewById(R.id.ReyAngsuran);
        adapterAngsuran = new AdapterAngsuran(getContext(), modelAngsurans);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterAngsuran);

        pilih = v.findViewById(R.id.pilihAngsuran);
        tutup = v.findViewById(R.id.tutupAngsuran);

        pilih.setOnClickListener(v1 -> {


            String nameid[][] = adapterAngsuran.getNameid();

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


        search = v.findViewById(R.id.search_angsuran);
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

                adapterAngsuran.getFilter().filter(newText);
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
            bottomSheetListenerProduksms = (ModalAngsuran.BottomSheetListenerProduksms) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement bottomsheet Listener");
        }
    }
}
