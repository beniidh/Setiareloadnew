package com.c.dompetabata.menuUtama.PaketData.UangElektronik;

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

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.R;
import com.c.dompetabata.menuUtama.PaketData.VoucherGame.AdapterProdukVoucher;
import com.c.dompetabata.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukUangElektronik extends AppCompatActivity implements ModalUangElektronik.BottomSheetListenerProduksms {

    EditText inputprodukuangelektronik,inputnomoruangelektronik;
    TextView tujukarakteruangelektronik;
    RecyclerView recyclerView;
    AdapterProdukUE adapterProdukUE;
    ArrayList<ResponProdukUE.VoucherData> vdata = new ArrayList<>();
    String type = "PRABAYAR";
    String idd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_uang_elektronik);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Uang Elektronik <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        inputprodukuangelektronik = findViewById(R.id.inputprodukuangelektronik);
        inputprodukuangelektronik.setFocusable(false);
        inputnomoruangelektronik = findViewById(R.id.inputnomoruangelektronik);
        tujukarakteruangelektronik = findViewById(R.id.tujukarakteruangelektronik);

        recyclerView = findViewById(R.id.ReyUangElektronik);
        adapterProdukUE = new AdapterProdukUE(getApplicationContext(), vdata, inputnomoruangelektronik.getText().toString(), type);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterProdukUE);

        String id = getIntent().getStringExtra("id");


        inputnomoruangelektronik.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(inputnomoruangelektronik.length() >= 7){
                    tujukarakteruangelektronik.setVisibility(View.INVISIBLE);
                } else {

                    tujukarakteruangelektronik.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                getProduk(getIdd(),inputnomoruangelektronik.getText().toString());

            }
        });

        inputprodukuangelektronik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ModalUangElektronik modalUangElektronik = new ModalUangElektronik();
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                modalUangElektronik.setArguments(bundle);
                modalUangElektronik.show(getSupportFragmentManager(),"Uang elektronik");
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
    public void onButtonClick(String name, String id) {
        inputprodukuangelektronik.setText(name);
        setIdd(id);
    }

    public void getProduk(String id,String nomor){
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukUE> call = api.getProdukUE(token,id);
        call.enqueue(new Callback<ResponProdukUE>() {
            @Override
            public void onResponse(Call<ResponProdukUE> call, Response<ResponProdukUE> response) {
                String code = response.body().getCode();
                if (code.equals("200")){

                    vdata = response.body().getData();
                    adapterProdukUE = new AdapterProdukUE(getApplicationContext(), vdata, nomor, type);
                    recyclerView.setAdapter(adapterProdukUE);

                }
            }

            @Override
            public void onFailure(Call<ResponProdukUE> call, Throwable t) {

            }
        });


    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }
}