package com.c.dompetabata.KodeProduk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.c.dompetabata.Helper.utils;
import com.c.dompetabata.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Kodeprodukact extends AppCompatActivity {

    Button linkProduk;
    EditText tokennid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kodeprodukact);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Kode Produk <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        linkProduk = findViewById(R.id.linkProduk);


        linkProduk.setOnClickListener(v -> {


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