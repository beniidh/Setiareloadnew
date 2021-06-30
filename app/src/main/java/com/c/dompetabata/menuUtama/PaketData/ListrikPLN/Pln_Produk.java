package com.c.dompetabata.menuUtama.PaketData.ListrikPLN;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.c.dompetabata.R;

public class Pln_Produk extends AppCompatActivity {
    EditText nomorinputpln;
    TextView keterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pln__produk);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Listrik PLN <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        nomorinputpln = findViewById(R.id.nomorinputPLN);
        keterangan = findViewById(R.id.tujukarakterpln);

        nomorinputpln.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(nomorinputpln.length() >= 7){
                    keterangan.setVisibility(View.INVISIBLE);
                } else {
                    keterangan.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

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