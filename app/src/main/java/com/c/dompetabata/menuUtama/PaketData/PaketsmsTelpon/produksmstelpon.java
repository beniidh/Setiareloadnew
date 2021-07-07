package com.c.dompetabata.menuUtama.PaketData.PaketsmsTelpon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.c.dompetabata.R;

public class produksmstelpon extends AppCompatActivity implements ModalProdukSmsTelpon.BottomSheetListenerProduksms {

    EditText inputproduksmspulsa, inputnomorsmspulsa;
    TextView tujukaraktersmspulsa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produksmstelpon);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>SMS & Telpon <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        inputproduksmspulsa = findViewById(R.id.pilihproduksmstelpon);
        inputproduksmspulsa.setFocusable(false);
        inputnomorsmspulsa = findViewById(R.id.inputproduksmstelpon);
        tujukaraktersmspulsa = findViewById(R.id.tujukaraktersmstelpon);

        inputproduksmspulsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModalProdukSmsTelpon produkSmsTelpon = new ModalProdukSmsTelpon();
                produkSmsTelpon.show(getSupportFragmentManager(),"produksmstelpon");
            }
        });

        inputnomorsmspulsa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (inputnomorsmspulsa.length() >= 7) {
                    tujukaraktersmspulsa.setVisibility(View.INVISIBLE);
                } else {
                    tujukaraktersmspulsa.setVisibility(View.VISIBLE);
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

    @Override
    public void onButtonClick(String name, String id) {
        inputproduksmspulsa.setText(name);
    }
}