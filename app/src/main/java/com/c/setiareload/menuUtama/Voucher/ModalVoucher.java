package com.c.setiareload.menuUtama.Voucher;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

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

public class ModalVoucher extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    AdapterVoucher adapterVoucher;
    ArrayList<ModelVoucher> modelVouchers = new ArrayList<>();
    Button pilih, tutup;
    SearchView search;

    protected BottomSheetListenerProduksms bottomSheetListenerProduksms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_voucher, container, false);


        recyclerView = v.findViewById(R.id.ReyVoucher);
        adapterVoucher = new AdapterVoucher(ModalVoucher.this,getContext(), modelVouchers);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterVoucher);
        String idd = getArguments().getString("id");
        getProduk(idd);

        pilih = v.findViewById(R.id.pilihVoucher);
        tutup = v.findViewById(R.id.tutupVoucher);

        pilih.setOnClickListener(v1 -> {


            String nameid[][] = adapterVoucher.getNameid();

            String namee = nameid[0][0];
            String id = nameid[0][1];
            String icon = nameid[0][2];

            bottomSheetListenerProduksms.onButtonClick(namee, id,icon);
            dismiss();
        });

        tutup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        search = v.findViewById(R.id.search_voucher);
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

                adapterVoucher.getFilter().filter(newText);
                return false;
            }
        });


        return v;
    }

    public interface BottomSheetListenerProduksms {
        void onButtonClick(String name, String id,String icon);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            bottomSheetListenerProduksms = (ModalVoucher.BottomSheetListenerProduksms) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement bottomsheet Listener");
        }
    }

    public void getProduk(String id) {
        String token = "Bearer " + Preference.getToken(getContext());

        Api api = RetroClient.getApiServices();
        Call<ResponVoucher> call = api.getProdukVoucher(token, id);
        call.enqueue(new Callback<ResponVoucher>() {
            @Override
            public void onResponse(Call<ResponVoucher> call, Response<ResponVoucher> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    modelVouchers = response.body().getData();
                    adapterVoucher = new AdapterVoucher(ModalVoucher.this,getContext(), modelVouchers);
                    recyclerView.setAdapter(adapterVoucher);
                } else {

                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponVoucher> call, Throwable t) {

            }
        });

    }
}
