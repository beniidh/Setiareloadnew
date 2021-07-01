package com.c.dompetabata.menuUtama.PaketData.VoucherGame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.dompetabata.Adapter.AdapterKecamatan;
import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.R;
import com.c.dompetabata.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModalVoucherGame extends BottomSheetDialogFragment {

    RecyclerView recyclerView;
    AdapterVoucherGame adapterVoucherGame;
    ArrayList<MVoucherGame> mVoucherGames = new ArrayList<>();
    String id;

    public ModalVoucherGame(String id) {
        this.id = id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_layout_vouchergame, container, false);
        getProdukGame(id);
        recyclerView = v.findViewById(R.id.ReyProdukGame);

        adapterVoucherGame = new AdapterVoucherGame(getContext(), mVoucherGames,ModalVoucherGame.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterVoucherGame);

        return  v;

    }

    private void getProdukGame(String id){

        String token = "Bearer "+ Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        Call<ResponVoucherGame> call = api.getProdukVoucherGame(token,id);
        call.enqueue(new Callback<ResponVoucherGame>() {
            @Override
            public void onResponse(Call<ResponVoucherGame> call, Response<ResponVoucherGame> response) {

                mVoucherGames = response.body().getData();
                adapterVoucherGame = new AdapterVoucherGame(getContext(), mVoucherGames,ModalVoucherGame.this);
                recyclerView.setAdapter(adapterVoucherGame);

            }

            @Override
            public void onFailure(Call<ResponVoucherGame> call, Throwable t) {
                Toast.makeText(getContext(),"Gagal",Toast.LENGTH_SHORT).show();

            }
        });

    }
}
