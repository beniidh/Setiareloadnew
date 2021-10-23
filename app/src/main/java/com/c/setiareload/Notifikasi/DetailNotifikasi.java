package com.c.setiareload.Notifikasi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.c.setiareload.Helper.utils;
import com.c.setiareload.R;

public class DetailNotifikasi extends AppCompatActivity {
    TextView statusND, tanggalND, waktuND, transaksiND, SaldoND, nominalND, BiayaND, TotalND;
    ImageView iconND;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_notifikasi);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Detail Notifikasi <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        tanggalND = findViewById(R.id.tanggalND);
        waktuND = findViewById(R.id.waktuND);
        transaksiND = findViewById(R.id.transaksiND);
        SaldoND = findViewById(R.id.SaldoND);
        nominalND = findViewById(R.id.nominalND);
        BiayaND = findViewById(R.id.BiayaND);
        TotalND = findViewById(R.id.TotalND);

        statusND = findViewById(R.id.StatusND);
        iconND = findViewById(R.id.iconND);

        tanggalND.setText(getIntent().getStringExtra("tanggal"));
        waktuND.setText(getIntent().getStringExtra("waktu"));
        transaksiND.setText(getIntent().getStringExtra("transaksid"));
        SaldoND.setText(utils.ConvertRP(getIntent().getStringExtra("saldo")));
        nominalND.setText(utils.ConvertRP(getIntent().getStringExtra("nominal")));
        BiayaND.setText(utils.ConvertRP(getIntent().getStringExtra("biaya")));
        TotalND.setText(utils.ConvertRP(getIntent().getStringExtra("saldo")));
        statusND.setText(getIntent().getStringExtra("status"));

        if (getIntent().getStringExtra("status").equals("PENDING")) {
            iconND.setBackground(getDrawable(R.drawable.ic_baseline_access_time_filled_24));
        } else if (getIntent().getStringExtra("status").equals("GAGAL")) {
            iconND.setBackground(getDrawable(R.drawable.failed));
        }

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