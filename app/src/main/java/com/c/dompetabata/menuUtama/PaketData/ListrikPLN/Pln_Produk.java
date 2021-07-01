package com.c.dompetabata.menuUtama.PaketData.ListrikPLN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.R;
import com.c.dompetabata.Respon.Respon;
import com.c.dompetabata.Respon.ResponSubCategoryPln;
import com.c.dompetabata.Respon.ResponSubP;
import com.c.dompetabata.menuUtama.PaketData.PulsaPrabayar.AdapterPulsaPrabayar;
import com.c.dompetabata.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pln_Produk extends AppCompatActivity {
    EditText nomorinputpln;
    TextView keterangan;
    AdapterProdukPLN adapterProdukPLN;
    ArrayList<ModelProdukPln> modelProdukPlns = new ArrayList<>();
    RecyclerView recyclerPLN;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pln__produk);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Listrik PLN <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        recyclerPLN = findViewById(R.id.ReyProdukPlnPra);
        nomorinputpln = findViewById(R.id.nomorinputPLN);
        adapterProdukPLN = new AdapterProdukPLN(getApplicationContext(), modelProdukPlns,nomorinputpln.getText().toString());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerPLN.setLayoutManager(mLayoutManager);
        recyclerPLN.setAdapter(adapterProdukPLN);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        keterangan = findViewById(R.id.tujukarakterpln);

        nomorinputpln.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(nomorinputpln.length() >= 7){
                    keterangan.setVisibility(View.INVISIBLE);
                } else {
                    keterangan.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

//                nomor = nomorinputpln.getText().toString();
                getSubProdukID(id);
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

    private void getSubProdukID(String id){

        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponSubP> call = api.getSubCategoryPLN(token,id);
        call.enqueue(new Callback<ResponSubP>() {
            @Override
            public void onResponse(Call<ResponSubP> call, Response<ResponSubP> response) {
                String code= response.body().getCode();
                if(code.equals("200")){
                    String idProduct = response.body().getData().get(0).getId();
                    getProdukPln(idProduct);
                }else {
                    Toast.makeText(getApplicationContext(),"gagal code",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponSubP> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"gagal",Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getProdukPln(String id){
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponListrikPln> call = api.getProdukPLNListrik(token,id);
        call.enqueue(new Callback<ResponListrikPln>() {
            @Override
            public void onResponse(Call<ResponListrikPln> call, Response<ResponListrikPln> response) {
                String code = response.body().getCode();
                if(code.equals("200")){

                    modelProdukPlns = (ArrayList<ModelProdukPln>) response.body().getData();
                    adapterProdukPLN = new AdapterProdukPLN(getApplicationContext(), modelProdukPlns,nomorinputpln.getText().toString());
                    recyclerPLN.setAdapter(adapterProdukPLN);
                }
            }

            @Override
            public void onFailure(Call<ResponListrikPln> call, Throwable t) {

            }
        });

    }
}