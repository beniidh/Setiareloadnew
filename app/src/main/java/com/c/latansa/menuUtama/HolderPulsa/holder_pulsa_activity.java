package com.c.latansa.menuUtama.HolderPulsa;

import android.os.Bundle;
import android.text.Html;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.latansa.Api.Api;
import com.c.latansa.Helper.RetroClient;
import com.c.latansa.R;
import com.c.latansa.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class holder_pulsa_activity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterHolder adapterHolder;
    ArrayList<ResponSub.mData> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.holder_pulsa_activity);
        //get judul
        String judul = getIntent().getStringExtra("name");
        recyclerView = findViewById(R.id.reyHolderPulsa);

        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 + "'><b>" + judul + "<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);


        // inflate data

        adapterHolder = new AdapterHolder(holder_pulsa_activity.this, data, holder_pulsa_activity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterHolder);

        getProdukSub();

    }

    public void getProdukSub() {
        String id = getIntent().getStringExtra("id");
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponSub> call = api.getSubHolder(token, id);
        call.enqueue(new Callback<ResponSub>() {
            @Override
            public void onResponse(Call<ResponSub> call, Response<ResponSub> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    data = response.body().getData();
                    adapterHolder = new AdapterHolder(getApplicationContext(), data, holder_pulsa_activity.this);
                    recyclerView.setAdapter(adapterHolder);

                } else {
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG);

                }
            }

            @Override
            public void onFailure(Call<ResponSub> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi tidak stabil,silahkan ulangi", Toast.LENGTH_LONG);
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