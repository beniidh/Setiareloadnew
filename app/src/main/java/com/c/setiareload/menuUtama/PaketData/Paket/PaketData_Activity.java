package com.c.setiareload.menuUtama.PaketData.Paket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.menuUtama.PaketData.PulsaPrabayar.modelPasca;
import com.c.setiareload.R;
import com.c.setiareload.Respon.ResponSubCategory;
import com.c.setiareload.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaketData_Activity extends AppCompatActivity {

    EditText nomorbelidata;
    private String url;
    ImageView iconproduk;
    modelPasca modelPascaa;
    String idproduk;



    RecyclerView recyclerView;
    AdapterPaketData adapterPaketData;
    String nomor;
    ArrayList<MProdukPaketData> mProdukPaketData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket_data_);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Paket Data <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        iconproduk = findViewById(R.id.iconprodukPaketData);
        nomorbelidata = findViewById(R.id.nomorbelipulsaPaketDat);
        recyclerView = findViewById(R.id.ReyPaketData);

        adapterPaketData = new AdapterPaketData(getApplicationContext(), mProdukPaketData,nomor,getUrl());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterPaketData);


        nomorbelidata.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (nomorbelidata.length() >= 4) {
                    String provider = nomorbelidata.getText().toString().substring(0,4);
                    Intent intent = getIntent();
                    String id = intent.getStringExtra("id");
                    getSubCategory(provider,id);

                }else{
                    ArrayList<MProdukPaketData> mProdukPaketDataa = new ArrayList<>();
                    adapterPaketData = new AdapterPaketData(getApplicationContext(),mProdukPaketDataa,nomorbelidata.getText().toString(),getUrl());
                    setUrl("http//");


                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                Picasso.get().load(getUrl()).into(iconproduk);
                if(nomorbelidata.length()>= 4) {

                  getPaketDatabyID(getIdproduk(),nomorbelidata.getText().toString());
                }
            }
        });




    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void getSubCategory(String prefix,String id) {

        String token = "Bearer "+ Preference.getToken(getApplicationContext());

        Api api = RetroClient.getApiServices();
        Call<ResponSubCategory> call = api.getSubPrdoductByPrefix(token,prefix,id);
        call.enqueue(new Callback<ResponSubCategory>() {
            @Override
            public void onResponse(Call<ResponSubCategory> call, Response<ResponSubCategory> response) {
                String code = response.body().getCode();
                String message = response.body().getMessage();
                if (code.equals("200")) {

                    setUrl(response.body().getData().getIcon());
                    setIdproduk(response.body().getData().getId());

                } else {

                    setUrl(null);
                }

            }

            @Override
            public void onFailure(Call<ResponSubCategory> call, Throwable t) {

            }
        });


    }

    public void getPaketDatabyID(String id,String nomorr){

        String token = "Bearer " +Preference.getToken(getApplicationContext());

        Api api = RetroClient.getApiServices();
        Call<ResponPaketData> call = api.getPaketDataProduk(token,id);
        call.enqueue(new Callback<ResponPaketData>() {
            @Override
            public void onResponse(Call<ResponPaketData> call, Response<ResponPaketData> response) {
                String code = response.body().getCode();
                if(code.equals("200")){
                    mProdukPaketData = response.body().getData();
                    adapterPaketData = new AdapterPaketData(getApplicationContext(),mProdukPaketData,nomorr,getUrl());
                    recyclerView.setAdapter(adapterPaketData);

                }else {
                    ArrayList<MProdukPaketData> mProdukPaketDataa = new ArrayList<>();
                    adapterPaketData = new AdapterPaketData(getApplicationContext(),mProdukPaketDataa,nomorr,getUrl());
                    recyclerView.setAdapter(adapterPaketData);

                    StyleableToast.makeText(getApplicationContext(),"Produk Tidak ditemukan",Toast.LENGTH_LONG,R.style.mytoast2).show();
                }
            }

            @Override
            public void onFailure(Call<ResponPaketData> call, Throwable t) {

            }
        });

    }

    public String getIdproduk() {
        return idproduk;
    }
    public void setIdproduk(String idproduk) {
        this.idproduk = idproduk;
    }
}