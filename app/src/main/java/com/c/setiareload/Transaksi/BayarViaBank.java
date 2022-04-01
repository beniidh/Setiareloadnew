package com.c.setiareload.Transaksi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.LinearLayout;

import com.c.setiareload.R;
import com.c.setiareload.TopUpSaldoku.TrasferBank;

public class BayarViaBank extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar_via_bank);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Pilih Bank <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        LinearLayout linearLayoutbankBRI = findViewById(R.id.LinearBankBRI);
        linearLayoutbankBRI.setOnClickListener(v -> {
            Intent intent = new Intent(BayarViaBank.this, TrasferBank.class);
            intent.putExtra("Title","Transfer Bank BRI");
            intent.putExtra("NoRekening","337201026878538");
            startActivity(intent);
        });

        LinearLayout linearLayoutbankBCA = findViewById(R.id.LinearBankBCA);
        linearLayoutbankBCA.setOnClickListener(v -> {
            Intent intent = new Intent(BayarViaBank.this, TrasferBank.class);
            intent.putExtra("Title","Transfer Bank BCA");
            intent.putExtra("NoRekening","2940746143");
            startActivity(intent);
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