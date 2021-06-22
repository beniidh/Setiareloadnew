package com.c.dompetabata.Transaksi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.c.dompetabata.Modal.ModalMetodePemayaran;
import com.c.dompetabata.R;

public class TopupSaldoServer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_saldo_server);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Top Up Saldo Server <b></font>"));
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

    public void RiwayatTagihan(View view){
        Intent intent = new Intent(TopupSaldoServer.this,RiwayatTagihan.class);
        startActivity(intent);
    }
    public void BayarTagihan(View view){
        ModalMetodePemayaran modalPembayaran = new ModalMetodePemayaran();
        modalPembayaran.show(getSupportFragmentManager(), "ModalPebayaran");
    }
}