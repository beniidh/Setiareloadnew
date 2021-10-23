package com.c.setiareload.menuUtama.PaketData.PaketsmsTelpon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.R;
import com.c.setiareload.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class produksmstelpon extends AppCompatActivity implements ModalProdukSmsTelpon.BottomSheetListenerProduksms {

    EditText inputproduksmspulsa, inputnomorsmspulsa;
    TextView tujukaraktersmspulsa;
    AdapterProdukST adapterProdukST;
    ArrayList<MProdukSmsTelpon> mProdukSmsTelpons = new ArrayList<>();
    RecyclerView recyclerView;
    String url;
    String idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produksmstelpon);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>SMS & Telpon <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        inputproduksmspulsa = findViewById(R.id.pilihproduksmstelpon);
        inputproduksmspulsa.setFocusable(false);
        inputnomorsmspulsa = findViewById(R.id.inputproduksmstelpon);
        tujukaraktersmspulsa = findViewById(R.id.tujukaraktersmstelpon);
        recyclerView = findViewById(R.id.ReyProdukSMST);


        adapterProdukST = new AdapterProdukST(getApplicationContext(), mProdukSmsTelpons,inputnomorsmspulsa.getText().toString(),getUrl());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterProdukST);


        inputproduksmspulsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModalProdukSmsTelpon produkSmsTelpon = new ModalProdukSmsTelpon();
                produkSmsTelpon.show(getSupportFragmentManager(),"produksmstelpon");
            }
        });

        inputnomorsmspulsa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (inputnomorsmspulsa.length() >= 7) {
                    tujukaraktersmspulsa.setVisibility(View.INVISIBLE);
                } else {
                    tujukaraktersmspulsa.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                getProdukSmsTelpon(getIdd(),inputnomorsmspulsa.getText().toString());

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

    @Override
    public void onButtonClick(String name, String id,String icon) {
        inputproduksmspulsa.setText(name);
        setIdd(id);
        getProdukSmsTelpon(id,inputnomorsmspulsa.getText().toString());
        setUrl(icon);
    }

    public void getProdukSmsTelpon(String id,String nomor){

        String token = "Bearer "+ Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponSmsTelpon> call = api.getProdukSMST(token,id);
        call.enqueue(new Callback<ResponSmsTelpon>() {
            @Override
            public void onResponse(Call<ResponSmsTelpon> call, Response<ResponSmsTelpon> response) {

                String code = response.body().getCode();
                if (code.equals("200")){
                    mProdukSmsTelpons = response.body().getData();
                }else {

                    Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_SHORT).show();
                    mProdukSmsTelpons.clear();
                }

                adapterProdukST = new AdapterProdukST(getApplicationContext(),mProdukSmsTelpons,nomor,getUrl());
                recyclerView.setAdapter(adapterProdukST);

            }

            @Override
            public void onFailure(Call<ResponSmsTelpon> call, Throwable t) {

            }
        });


    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }
}