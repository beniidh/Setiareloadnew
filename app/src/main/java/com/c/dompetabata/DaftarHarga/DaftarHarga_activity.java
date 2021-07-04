package com.c.dompetabata.DaftarHarga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;

import com.c.dompetabata.R;

public class DaftarHarga_activity extends AppCompatActivity {

    EditText idprodukDaftarHarga,idProviderDaftarHarga;
    RecyclerView reyIDDaftarHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_harga_activity);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Daftar Harga <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        idprodukDaftarHarga = findViewById(R.id.idprodukDaftarHarga);
        idProviderDaftarHarga = findViewById(R.id.idProviderDaftarHarga);
        reyIDDaftarHarga = findViewById(R.id.idRecycleDaftarHarga);

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