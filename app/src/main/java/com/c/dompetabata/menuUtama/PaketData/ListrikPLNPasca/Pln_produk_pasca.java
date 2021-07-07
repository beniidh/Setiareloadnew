package com.c.dompetabata.menuUtama.PaketData.ListrikPLNPasca;

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
import com.c.dompetabata.Respon.ResponSubP;
import com.c.dompetabata.menuUtama.PaketData.ListrikPLN.AdapterProdukPLN;
import com.c.dompetabata.menuUtama.PaketData.ListrikPLN.ModelProdukPln;
import com.c.dompetabata.menuUtama.PaketData.ListrikPLN.ResponListrikPln;
import com.c.dompetabata.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pln_produk_pasca extends AppCompatActivity {
    EditText inputplnpasca;
    TextView tujukarakterplnpasca;
    RecyclerView recyclerView;
    AdapterProdukPLNPasca adapterProdukPLNPasca;
    ArrayList<ModelProdukPlnPasca> modelProdukPlnPascas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pln_produk_pasca);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Listrik PLN Pasca <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);


        inputplnpasca = findViewById(R.id.nomorinputPLNPasca);
        recyclerView = findViewById(R.id.RecyclePLNPasca);
        tujukarakterplnpasca = findViewById(R.id.tujukarakterplnpasca);

        adapterProdukPLNPasca = new AdapterProdukPLNPasca(getApplicationContext(), modelProdukPlnPascas,inputplnpasca.getText().toString());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterProdukPLNPasca);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        inputplnpasca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(inputplnpasca.length() >= 7){

                    tujukarakterplnpasca.setVisibility(View.INVISIBLE);

                } else{
                    tujukarakterplnpasca.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                getSubProdukID("CATID062502100000005");
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
        Call<ResponListrikPlnPasca> call = api.getProdukPLNListrikPasca(token,id);
        call.enqueue(new Callback<ResponListrikPlnPasca>() {
            @Override
            public void onResponse(Call<ResponListrikPlnPasca> call, Response<ResponListrikPlnPasca> response) {
                String code = response.body().getCode();
                if(code.equals("200")){

                    modelProdukPlnPascas = (ArrayList<ModelProdukPlnPasca>) response.body().getData();
                    adapterProdukPLNPasca = new AdapterProdukPLNPasca(getApplicationContext(), modelProdukPlnPascas,inputplnpasca.getText().toString());
                    recyclerView.setAdapter(adapterProdukPLNPasca);
                }
            }

            @Override
            public void onFailure(Call<ResponListrikPlnPasca> call, Throwable t) {

            }
        });

    }
}