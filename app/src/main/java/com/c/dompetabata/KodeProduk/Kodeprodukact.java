package com.c.dompetabata.KodeProduk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.c.dompetabata.Helper.utils;
import com.c.dompetabata.R;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Kodeprodukact extends AppCompatActivity {

    Button linkProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kodeprodukact);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Kode Produk <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        linkProduk = findViewById(R.id.linkProduk);

        linkProduk.setOnClickListener(v -> {

//            utils.hmacSha("R5cCI6IkpXVCJ9","bismillah","HmacSHA256");
//            Log.d("hsam",utils.hmacSha("R5cCI6IkpXVCJ9","123456","HmacSHA256"));

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