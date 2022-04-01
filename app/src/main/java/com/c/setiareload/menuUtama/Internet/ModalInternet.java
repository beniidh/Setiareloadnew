package com.c.setiareload.menuUtama.Internet;

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

public class ModalInternet extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    AdapterInternet adapterInternet;
    ArrayList<ModelInternet> modelInternets = new ArrayList<>();
    Button pilih,tutup;
    SearchView search;

    protected BottomSheetListenerProduksms bottomSheetListenerProduksms;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_internet, container, false);

        recyclerView = v.findViewById(R.id.ReyInternet);
        adapterInternet = new AdapterInternet(ModalInternet.this,getContext(), modelInternets);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterInternet);

        pilih = v.findViewById(R.id.pilihInternet);
        tutup = v.findViewById(R.id.tutupInternet);

        String idd = getArguments().getString("id");
        getProdukAir(idd);

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

    private void getProdukAir(String id){

        String token = "Bearer "+ Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        Call<ResponIntenet> call = api.getProdukInternet(token,id);
        call.enqueue(new Callback<ResponIntenet>() {
            @Override
            public void onResponse(Call<ResponIntenet> call, Response<ResponIntenet> response) {

                modelInternets = response.body().getData();
                adapterInternet = new AdapterInternet(ModalInternet.this,getContext(), modelInternets);
                recyclerView.setAdapter(adapterInternet);

            }

            @Override
            public void onFailure(Call<ResponIntenet> call, Throwable t) {
                Toast.makeText(getContext(),"Gagal",Toast.LENGTH_SHORT).show();

            }
        });

    }

}
