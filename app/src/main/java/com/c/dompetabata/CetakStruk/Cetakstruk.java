package com.c.dompetabata.CetakStruk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.LoadingPrimer;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.R;
import com.c.dompetabata.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Cetakstruk extends AppCompatActivity {

    EditText idCetakStrukEditText;
    RecyclerView recyclerView;
    AdapterCetakStruk adapterCetakStruk;
    SearchView idSearchStruk;
    ArrayList<ResponStruk.DataTransaksi> mdata = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cetakstruk);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Cetak Struk <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        idSearchStruk = findViewById(R.id.idSearchStruk);
        recyclerView = findViewById(R.id.idRecycleCetakStruk);

        adapterCetakStruk = new AdapterCetakStruk(getApplicationContext(), mdata);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterCetakStruk);
        getDataHistory();

        idSearchStruk.setOnClickListener(v -> idSearchStruk.onActionViewExpanded());
        idSearchStruk.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterCetakStruk.getFilter().filter(newText);
                return false;
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

    public void getCetakDetail(View view) {
        Intent intent = new Intent(Cetakstruk.this, DetailTransaksiTruk.class);
        startActivity(intent);

    }

    public void getDataHistory() {
        LoadingPrimer loadingPrimer = new LoadingPrimer(Cetakstruk.this);
        loadingPrimer.startDialogLoading();
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponStruk> call = api.getHistoriStruk(token);
        call.enqueue(new Callback<ResponStruk>() {
            @Override
            public void onResponse(Call<ResponStruk> call, Response<ResponStruk> response) {

                ArrayList<ResponStruk.DataTransaksi> mdataa = new ArrayList<>();
                String code = response.body().getCode();
                if (code.equals("200")) {
                    mdata = response.body().getData();

                    for (ResponStruk.DataTransaksi data : mdata) {

                        if (data.getStatus().equals("SUKSES")) {

                            mdataa.add(data);
                        }


                    }
                    adapterCetakStruk = new AdapterCetakStruk(getApplicationContext(), mdataa);
                    recyclerView.setAdapter(adapterCetakStruk);
                    loadingPrimer.dismissDialog();
                }else {
                    Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_SHORT).show();
                    loadingPrimer.dismissDialog();
                }

            }

            @Override
            public void onFailure(Call<ResponStruk> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_SHORT).show();

            }
        });

    }

}