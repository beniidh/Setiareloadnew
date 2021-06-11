package com.c.dompetabata;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

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
                Intent login = new Intent(splash_activity.this, Login_Activity.class);
                startActivity(login);
                finish();


            }
        }, 2000);
    }

    public void setLogo() {
        logo.setImageDrawable(getDrawable(R.drawable.csoftware));
    }
}