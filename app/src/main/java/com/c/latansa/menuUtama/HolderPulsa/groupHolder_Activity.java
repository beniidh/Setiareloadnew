package com.c.latansa.menuUtama.HolderPulsa;

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

public class groupHolder_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterGroupHolder adapterHolder;
    ArrayList<ResponGroup.Data> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_holder);

        //get judul
        String judul = "Pilihan Paket";
        recyclerView = findViewById(R.id.reyGroupHolder);

        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 + "'><b>" + judul + "<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        adapterHolder = new AdapterGroupHolder(groupHolder_Activity.this, data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterHolder);

        getProdukSub();

    }

    public void getProdukSub() {
        String id = getIntent().getStringExtra("id");
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponGroup> call = api.getProdukGroup(token, id);
        call.enqueue(new Callback<ResponGroup>() {
            @Override
            public void onResponse(Call<ResponGroup> call, Response<ResponGroup> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    data = response.body().getData();
                    adapterHolder = new AdapterGroupHolder(getApplicationContext(), data);
                    recyclerView.setAdapter(adapterHolder);

                } else {
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG);

                }
            }

            @Override
            public void onFailure(Call<ResponGroup> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi tidak stabil,silahkan ulangi", Toast.LENGTH_LONG);
            }
        });
    }
}