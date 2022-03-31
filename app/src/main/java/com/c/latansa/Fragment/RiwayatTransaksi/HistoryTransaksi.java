package com.c.latansa.Fragment.RiwayatTransaksi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;

import com.c.latansa.R;

public class HistoryTransaksi extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_transaksi);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Histori Transaksi <b></font>"));
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
}