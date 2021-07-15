package com.c.dompetabata;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Helper.utils;
import com.c.dompetabata.Model.Mlogin;
import com.c.dompetabata.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.Observable;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class splash_activity extends AppCompatActivity {
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);
        logo = findViewById(R.id.logosplash);
        getSupportActionBar().hide();
        setLogo();
        int delay = 2000;

        Handler handler = new Handler();
        handler.postDelayed(() -> {

            String token = Preference.getToken(getApplicationContext());

            if (!token.equals("")) {

                Api api = RetroClient.getApiServices();
                Call<Mlogin> call = api.getProfile("Bearer " + token);
                call.enqueue(new Callback<Mlogin>() {
                    @Override
                    public void onResponse(Call<Mlogin> call, Response<Mlogin> response) {
                        String code = response.body().getCode();
                        if (code.equals("200")) {
                            Intent home = new Intent(splash_activity.this, drawer_activity.class);
                            startActivity(home);
                            finish();

                        } else {

                            Intent login = new Intent(splash_activity.this, pin_activity.class);
                            startActivity(login);
                            finish();
                            StyleableToast.makeText(getApplicationContext(), "Token sudah berakhir,Silahkan Masukan PIN", Toast.LENGTH_LONG, R.style.mytoast2).show();

                        }


                    }

                    @Override
                    public void onFailure(Call<Mlogin> call, Throwable t) {
                        StyleableToast.makeText(getApplicationContext(), " Internet Belum dinyalakan", Toast.LENGTH_LONG, R.style.mytoast2).show();

                    }
                });

            } else {

                Intent login = new Intent(splash_activity.this, Login_Activity.class);
                startActivity(login);
                finish();

            }


        }, delay);
    }

    public void setLogo() {
        logo.setImageDrawable(getDrawable(R.drawable.csoftware));
    }

}

