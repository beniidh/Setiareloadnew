package com.c.latansa.reseller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

import com.c.latansa.Api.Api;
import com.c.latansa.Helper.RetroClient;
import com.c.latansa.R;
import com.c.latansa.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersetujuanSaldokuReseller extends AppCompatActivity implements ModalApprove.BottomSheetListener{

    RecyclerView recyclerView;
    AdapterSaldoReseller adapterSaldoReseller;
    ArrayList<ResponSaldoReseller.Data> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persetujuan_saldoku_reseller);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Saldoku Reseller <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        recyclerView = findViewById(R.id.ReySaldoReseller);

        adapterSaldoReseller = new AdapterSaldoReseller(getApplicationContext(), data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterSaldoReseller);

        getData();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void getData() {
        String token = "Bearer "+Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponSaldoReseller> call = api.getSaldoReseller(token);
        call.enqueue(new Callback<ResponSaldoReseller>() {
            @Override
            public void onResponse(Call<ResponSaldoReseller> call, Response<ResponSaldoReseller> response) {
                if (response.body().getCode().equals("200")) {
                    data = response.body().getData();
                    if (!data.isEmpty()){
                        adapterSaldoReseller = new AdapterSaldoReseller(getApplicationContext(), data);
                        recyclerView.setAdapter(adapterSaldoReseller);

                    }else {
                        Toast.makeText(getApplicationContext(), "No data", Toast.LENGTH_SHORT).show();
                    }



                } else {
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponSaldoReseller> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        getData();
    }

    @Override
    public void onButtonClick() {
        getData();
    }
}