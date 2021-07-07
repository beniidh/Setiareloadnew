package com.c.dompetabata.Transaksi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;

import com.c.dompetabata.R;
import com.c.dompetabata.TopUpSaldoku.TrasferBank;

public class BayarViaBank extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar_via_bank);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Pilih Bank <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        LinearLayout linearLayoutbankBRI = findViewById(R.id.LinearBankBRI);
        linearLayoutbankBRI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BayarViaBank.this, TrasferBank.class);
                startActivity(intent);
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
}