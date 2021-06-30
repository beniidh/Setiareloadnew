package com.c.dompetabata.menuUtama.PaketData;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.menuUtama.PaketData.PulsaPrabayar.modelPasca;
import com.c.dompetabata.R;
import com.c.dompetabata.Respon.ResponSubCategory;
import com.c.dompetabata.sharePreference.Preference;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaketData_Activity extends AppCompatActivity {

    EditText nomorbelidata;
    private String url;
    ImageView iconproduk;
    modelPasca modelPascaa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket_data_);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Paket Data <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        iconproduk = findViewById(R.id.iconprodukPaketData);
        nomorbelidata = findViewById(R.id.nomorbelipulsaPaketData);

        nomorbelidata.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (nomorbelidata.length() == 4) {
                    String provider = nomorbelidata.getText().toString();
                    Intent intent = getIntent();
                    String id = intent.getStringExtra("id");
                    getSubCategory(provider,id);


                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                Picasso.get().load(getUrl()).into(iconproduk);
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

                } else {

                    setUrl(null);
                }

            }

            @Override
            public void onFailure(Call<ResponSubCategory> call, Throwable t) {

            }
        });


    }
}