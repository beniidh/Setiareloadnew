package com.c.dompetabata;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;

import com.c.dompetabata.Modal.ModalKabupaten;
import com.c.dompetabata.Modal.ModalKecamatan;
import com.c.dompetabata.Modal.ModalProvinsi;

public class Register_activity extends AppCompatActivity {
    EditText provinsi,kecamatan,kabupaten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'>Daftar </font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        //ID Provinsi definition
        provinsi = findViewById(R.id.provinsi);
        provinsi.setFocusable(false);
        provinsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProvinsi();
            }
        });

        //ID Kabupaten definition
        kabupaten = findViewById(R.id.kabupaten);
        kabupaten.setFocusable(false);
        kabupaten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKabupaten();
            }
        });

        kecamatan = findViewById(R.id.kecamatan);
        kecamatan.setFocusable(false);
        kecamatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKecamatan();
            }
        });


    }

    private void onClickKecamatan() {
        ModalKecamatan modalKecamatan = new ModalKecamatan();
        modalKecamatan.show(getSupportFragmentManager(),"Modalkecamatan");
    }

    private void onClickKabupaten() {
        ModalKabupaten modalKabupaten = new ModalKabupaten();
        modalKabupaten.show(getSupportFragmentManager(),"Modalkabupaten");
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

    public void onClickProvinsi(){
        ModalProvinsi modalProvinsi = new ModalProvinsi();
        modalProvinsi.show(getSupportFragmentManager(),"Modalprovinsi");
    }
}