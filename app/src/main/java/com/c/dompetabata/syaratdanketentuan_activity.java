package com.c.dompetabata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

public class syaratdanketentuan_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syaratdanketentuan_activity);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Syarat & Ketentuan <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

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

    public void SendOTP(View view){
        Bundle ekstra =getIntent().getExtras();
        Intent intent = new Intent(syaratdanketentuan_activity.this,OTPsend.class);
        intent.putExtra("user_id",ekstra.getString("user_id"));
        intent.putExtra("user_code",ekstra.getString("user_code"));
        intent.putExtra("phone",ekstra.getString("phone"));
        intent.putExtra("otp_id",ekstra.getString("otp_id"));
        startActivity(intent);
    }
}