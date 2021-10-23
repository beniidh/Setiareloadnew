package com.c.setiareload.KodeProduk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.c.setiareload.Api.Value;
import com.c.setiareload.R;

public class Kodeprodukact extends AppCompatActivity {

    Button linkProduk;
    EditText tokennid;
    LocalBroadcastManager localBroadcastManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kodeprodukact);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Kode Produk <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        linkProduk = findViewById(R.id.linkProduk);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);


        linkProduk.setOnClickListener(v -> {

            Toast.makeText(getApplicationContext(), Value.getMacAddress(getApplicationContext()),Toast.LENGTH_LONG).show();
            localBroadcastManager.sendBroadcast(new Intent("kirim"));

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