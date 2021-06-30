package com.c.dompetabata.menuUtama.PaketData.ListrikPLNPasca;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.c.dompetabata.R;

public class Pln_produk_pasca extends AppCompatActivity {
    EditText inputplnpasca;
    TextView tujukarakterplnpasca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pln_produk_pasca);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Listrik PLN Pasca <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        inputplnpasca = findViewById(R.id.nomorinputPLNPasca);
        tujukarakterplnpasca = findViewById(R.id.tujukarakterplnpasca);

        inputplnpasca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(inputplnpasca.length() >= 7){

                    tujukarakterplnpasca.setVisibility(View.INVISIBLE);
                } else{
                    tujukarakterplnpasca.setVisibility(View.VISIBLE);

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