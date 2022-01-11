package com.c.setiareload.TransferBank;

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

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.R;
import com.c.setiareload.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModalNamaBank extends BottomSheetDialogFragment {

    RecyclerView recyclerViewP;
    AdapterGetBank adapterGetBank;
    ArrayList<ModelNamaBank.mNama> modelnamabank = new ArrayList<>();
    Button tutup, pilih;
    SearchView searchbank;
    private BottomSheetListener bottomSheetListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_pilihbank, container, false);
        recyclerViewP = v.findViewById(R.id.ReyBank);

        adapterGetBank = new AdapterGetBank(getContext(), modelnamabank);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewP.setLayoutManager(mLayoutManager);
        recyclerViewP.setAdapter(adapterGetBank);

        getNamaBank();

        tutup = v.findViewById(R.id.tutupP);
        pilih = v.findViewById(R.id.pilihP);

        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        pilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String nameid[][] = adapterGetBank.getNameid();

                String namee = nameid[0][0];
                String id = nameid[0][1];

                bottomSheetListener.onButtonClick(namee, id);
                Preference.setName(getContext(), "");

                dismiss();
            }
        });

        searchbank = v.findViewById(R.id.search_bank);
        searchbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchbank.onActionViewExpanded();
            }
        });

        searchbank.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterGetBank.getFilter().filter(newText);
                return false;
            }
        });


        return v;


    }

    private void getNamaBank() {

        Api api = RetroClient.getApiServices();
        Call<ModelNamaBank> call = api.getNamaBank("Bearer "+Preference.getToken(getContext()));
        call.enqueue(new Callback<ModelNamaBank>() {
            @Override
            public void onResponse(Call<ModelNamaBank> call, Response<ModelNamaBank> response) {
                String code = response.body().getCode();

                modelnamabank = response.body().getData();
                adapterGetBank = new AdapterGetBank(getContext(), modelnamabank);
                recyclerViewP.setAdapter(adapterGetBank);
            }

            @Override
            public void onFailure(Call<ModelNamaBank> call, Throwable t) {

            }
        });


    }

    public interface BottomSheetListener {
        void onButtonClick(String name, String id);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            bottomSheetListener = (BottomSheetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement bottomsheet Listener");
        }
    }
}
