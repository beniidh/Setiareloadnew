package com.c.dompetabata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;

import com.c.dompetabata.Modal.ModalKelurahan;
import com.c.dompetabata.Modal.ModalMetodePemayaran;

public class topup_saldoku_activity extends AppCompatActivity {

    Button bayarsaldoku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_saldoku_activity);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Top UP Saldoku <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        bayarsaldoku = findViewById(R.id.bayarsaldoku);
        bayarsaldoku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModalMetodePemayaran modalPembayaran = new ModalMetodePemayaran();
                modalPembayaran.show(getSupportFragmentManager(), "ModalPebayaran");
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