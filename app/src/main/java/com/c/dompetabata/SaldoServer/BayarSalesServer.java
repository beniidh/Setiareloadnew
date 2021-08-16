package com.c.dompetabata.SaldoServer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.c.dompetabata.Helper.utils;
import com.c.dompetabata.R;
import com.c.dompetabata.TopUpSaldoku.ModalPinTopUpSaldoku;
import com.c.dompetabata.sharePreference.Preference;

public class BayarSalesServer extends AppCompatActivity {
    TextView saldokusales;
    Button idbayarsaldokuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar_sales_server);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Pembayaran Saldo Server <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);


        saldokusales = findViewById(R.id.saldokusales);
        saldokusales.setText(utils.ConvertRP( Preference.getSaldoServer(getApplicationContext())));
        idbayarsaldokuButton = findViewById(R.id.idbayarsaldokuButton);

        idbayarsaldokuButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("kode","sales");
            ModalPinUPP modalpinupp = new ModalPinUPP();
            modalpinupp.setArguments(bundle);
            modalpinupp.show(getSupportFragmentManager(), "topupsaldoserver");
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

    public Context getContextt() {
        finish();
        return getApplicationContext();

    }
}