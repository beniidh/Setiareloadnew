package com.c.setiareload.konter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.PengajuanLimit.AdapterPengajuanLimit;
import com.c.setiareload.R;
import com.c.setiareload.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class konter_activity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Mkonter.mData> data = new ArrayList<>();
    AdapterKonter adapterKonter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konter);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Konter <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getKonter();

        recyclerView = findViewById(R.id.ReyListKonter);

        adapterKonter = new AdapterKonter(getApplicationContext(), data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterKonter);


    }

    public void getKonter(){

        String token = "Bearer "+ Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<Mkonter> call = api.getKonterSales(token);
        call.enqueue(new Callback<Mkonter>() {
            @Override
            public void onResponse(Call<Mkonter> call, Response<Mkonter> response) {
                String code = response.body().getCode();
                if(code.equals("200")){

                    data = response.body().getData();
                    if(data == null){
                        Toast.makeText(getApplicationContext(),"Data tidak ditemukan",Toast.LENGTH_SHORT).show();
                    } else{
                        adapterKonter = new AdapterKonter(getApplicationContext(), data);
                        recyclerView.setAdapter(adapterKonter);

                    }

                }
            }

            @Override
            public void onFailure(Call<Mkonter> call, Throwable t) {

            }
        });


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
}