package com.c.setiareload.TagihanKonterSales;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.PengajuanLimit.AdapterPengajuanLimit;
import com.c.setiareload.R;
import com.c.setiareload.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagihanKonterbySales extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterKonterTagihan adapterKonterTagihan;
    ArrayList<ResponTagihanKonterSales.mData> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_user_pay_later);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Tagihan Konter <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        recyclerView = findViewById(R.id.ReyTagihanKonterSales);
        adapterKonterTagihan = new AdapterKonterTagihan(getApplicationContext(), mData);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterKonterTagihan);
        getTagihankonter();


    }

    public void getTagihankonter(){

        Api api = RetroClient.getApiServices();
        Call<ResponTagihanKonterSales> call = api.getTagihanSalesKonter("Bearer "+ Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponTagihanKonterSales>() {
            @Override
            public void onResponse(Call<ResponTagihanKonterSales> call, Response<ResponTagihanKonterSales> response) {
                String code = response.body().getCode();

                if(code.equals("200")){

                    mData = response.body().getData();
                    adapterKonterTagihan = new AdapterKonterTagihan(getApplicationContext(), mData);
                    recyclerView.setAdapter(adapterKonterTagihan);

                }else {

                }
            }

            @Override
            public void onFailure(Call<ResponTagihanKonterSales> call, Throwable t) {

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