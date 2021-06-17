package com.c.dompetabata;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;
import android.widget.ImageView;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Helper.utils;
import com.c.dompetabata.Model.Mlogin;
import com.c.dompetabata.sharePreference.Preference;

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


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                String phone =Preference.getKredentials(getApplicationContext());
                String Pin = Preference.getPIN(getApplicationContext());

                if( phone!= ""){

                    Double longli = Double.valueOf(Preference.getLong(getApplicationContext()));
                    Double latit = Double.valueOf(Preference.getLat(getApplicationContext()));


                    Mlogin mlogin = new Mlogin(phone,Pin,getIPaddress(),getUserAgent(),longli,latit);

                    Api api = RetroClient.getApiServices();
                    Call<Mlogin> call = api.Login(mlogin);
                    call.enqueue(new Callback<Mlogin>() {
                        @Override
                        public void onResponse(Call<Mlogin> call, Response<Mlogin> response) {

                            String code = response.body().getCode();

                            if(code.equals("200")){

                                Intent home = new Intent(splash_activity.this,drawer_activity.class);
                                startActivity(home);
                                finish();
                            }



                        }

                        @Override
                        public void onFailure(Call<Mlogin> call, Throwable t) {

                        }
                    });


                } else{
                    Intent login = new Intent(splash_activity.this, Login_Activity.class);
                    startActivity(login);
                    finish();

                }



            }
        }, 2000);
    }

    public void setLogo() {
        logo.setImageDrawable(getDrawable(R.drawable.csoftware));
    }

    private String getUserAgent() {

        String ua = new WebView(this).getSettings().getUserAgentString();
        return ua;
    }

    private String getIPaddress() {

        String IP = utils.getIPAddress(true);
        return IP;
    }
}

