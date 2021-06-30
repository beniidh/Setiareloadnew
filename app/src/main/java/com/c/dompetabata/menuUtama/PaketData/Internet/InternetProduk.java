package com.c.dompetabata.menuUtama.PaketData.Internet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.c.dompetabata.R;

public class InternetProduk extends AppCompatActivity {
    EditText inputprodukinternet,inputnomorinternet;
    TextView tujukarakterinternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_produk);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Internet <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        inputnomorinternet = findViewById(R.id.inputnomorinternet);
        inputprodukinternet = findViewById(R.id.inputprodukinternet);
        inputprodukinternet.setFocusable(false);
        tujukarakterinternet = findViewById(R.id.tujukarakterinternet);

        inputprodukinternet.setOnClickListener(v -> {
            ModalInternet modalInternet = new ModalInternet();
            modalInternet.show(getSupportFragmentManager(),"Modal Internet");

        });

        inputnomorinternet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(inputnomorinternet.length()>= 7){
                    tujukarakterinternet.setVisibility(View.INVISIBLE);
                } else {
                    tujukarakterinternet.setVisibility(View.VISIBLE);
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