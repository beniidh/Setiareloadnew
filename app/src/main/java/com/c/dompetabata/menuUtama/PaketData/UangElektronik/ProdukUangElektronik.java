package com.c.dompetabata.menuUtama.PaketData.UangElektronik;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.c.dompetabata.R;

public class ProdukUangElektronik extends AppCompatActivity implements ModalUangElektronik.BottomSheetListenerProduksms {

    EditText inputprodukuangelektronik,inputnomoruangelektronik;
    TextView tujukarakteruangelektronik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_uang_elektronik);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Uang Elektronik <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        inputprodukuangelektronik = findViewById(R.id.inputprodukuangelektronik);
        inputprodukuangelektronik.setFocusable(false);
        inputnomoruangelektronik = findViewById(R.id.inputnomoruangelektronik);
        tujukarakteruangelektronik = findViewById(R.id.tujukarakteruangelektronik);

        inputnomoruangelektronik.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(inputnomoruangelektronik.length() >= 7){
                    tujukarakteruangelektronik.setVisibility(View.INVISIBLE);
                } else {

                    tujukarakteruangelektronik.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputprodukuangelektronik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ModalUangElektronik modalUangElektronik = new ModalUangElektronik();
                modalUangElektronik.show(getSupportFragmentManager(),"Uang elektronik");
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
        inputprodukuangelektronik.setText(name);
    }
}