package com.c.dompetabata.TopUpSaldoku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.c.dompetabata.R;
import com.c.dompetabata.sharePreference.Preference;

public class TrasferBank extends AppCompatActivity {
    TextView saldokubank;
    Button oktransaksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trasfer_bank);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Pembayaran Saldoku <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        saldokubank = findViewById(R.id.saldokubank);
        saldokubank.setText(Preference.getSaldoku(getApplicationContext()));
        oktransaksi = findViewById(R.id.oktransaksi);
        oktransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModalPinTopUpSaldoku modalPinTopUpSaldoku = new ModalPinTopUpSaldoku();
                modalPinTopUpSaldoku.show(getSupportFragmentManager(),"topupsaldoku");
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