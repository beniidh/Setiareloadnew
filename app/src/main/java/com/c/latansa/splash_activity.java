package com.c.latansa;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.c.latansa.Api.Api;
import com.c.latansa.Helper.RetroClient;
import com.c.latansa.Model.Mlogin;
import com.c.latansa.pinNew.pinnew;
import com.c.latansa.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class splash_activity extends AppCompatActivity {
    ImageView logo;
    TextView versionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activity);
//        Sentry.captureMessage("Great this is setting");

        logo = findViewById(R.id.logosplash);
        getSupportActionBar().hide();
        int delay = 2000;
        versionCode = findViewById(R.id.versionCode);

        int versionCodein = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;

        versionCode.setText(new StringBuilder().append("versi ").append(versionCodein).append(".").append(versionName).toString());
        String coder = Preference.getTrackRegister(getApplicationContext());

        Handler handler = new Handler();
        handler.postDelayed(() -> {

            String token = Preference.getToken(getApplicationContext());

            if(coder.equals("")) {

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

                                Intent login = new Intent(splash_activity.this, pinnew.class);
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
            }else {

                Intent intent = new Intent(splash_activity.this,Pending_Activity.class);
                startActivity(intent);
                finish();
            }


        }, delay);
    }


}

