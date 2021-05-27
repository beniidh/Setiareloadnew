package com.c.dompetabata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;

import com.c.dompetabata.Modal.OTPinsert;

public class OTPsend extends AppCompatActivity {

Button sendEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_psend);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'>Pilih metode OTP </font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        sendEmail = findViewById(R.id.sendEmailOtp);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentOTP();

            }
        });
    }

    public void intentOTP(){
        Intent otpInsert = new Intent(OTPsend.this, OTPinsert.class);
        startActivity(otpInsert);

    }



}