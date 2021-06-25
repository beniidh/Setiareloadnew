package com.c.dompetabata.PulsaPrabayar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.RetroClient;

import com.c.dompetabata.R;
import com.c.dompetabata.Respon.ResponSubCategory;
import com.c.dompetabata.sharePreference.Preference;
import com.squareup.picasso.Picasso;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PulsaPrabayar_activity extends AppCompatActivity {

    EditText nomorbelipulsa;
    Drawable d;
    private String url;
    ImageView iconproduk;
    modelPasca modelPascaa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulsa_prabayar_activity);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Pulsa Prabayar <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        iconproduk = findViewById(R.id.iconproduk);
        modelPascaa = new modelPasca();

        nomorbelipulsa = findViewById(R.id.nomorbelipulsa);
        nomorbelipulsa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (nomorbelipulsa.length() == 4) {
                    String provider = nomorbelipulsa.getText().toString();
                    Intent intent = getIntent();
                    String id = intent.getStringExtra("id");
                    getSubCategory(provider,id);




            }

//                    getSubCategory(provider);
//                    Bitmap x = null;
//                        try {
//                            if (getUrl() == null){
//                                getSubCategory(provider);
//
//                                x = drawable_from_url(getUrl());
//                            }else {
//                                x = drawable_from_url(getUrl());
//                            }
//
//
//
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                        Drawable d = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(x, 100, 100, true));
//                        nomorbelipulsa.setCompoundDrawablesWithIntrinsicBounds(null, null, d, null);



            }

            @Override
            public void afterTextChanged(Editable s) {


                String iconurl = getUrl();

                if(iconurl == null){
                    iconproduk.setImageDrawable(getDrawable(R.drawable.ic_baseline_assignment_ind_24));
                }else {

                    Picasso.get().load(getUrl()).into(iconproduk);
                }


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

    Bitmap drawable_from_url(String url) throws java.net.MalformedURLException, java.io.IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("User-agent", "Mozilla/4.0");

        connection.connect();
        InputStream input = connection.getInputStream();

        return BitmapFactory.decodeStream(input);
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private static Activity unwrap(Context context) {
        while (!(context instanceof Activity) && context instanceof ContextWrapper) {
            context = ((ContextWrapper) context).getBaseContext();
        }

        return (Activity) context;
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