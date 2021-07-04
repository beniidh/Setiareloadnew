package com.c.dompetabata.CetakStruk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;

import com.c.dompetabata.R;

public class Cetakstruk extends AppCompatActivity {

    EditText idCetakStrukEditText;
    RecyclerView idReyCetakProduk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cetakstruk);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Cetak Struk <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        idCetakStrukEditText = findViewById(R.id.idCetakStrukEditText);
        idReyCetakProduk = findViewById(R.id.idRecycleCetakStruk);

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

    public void getCetakDetail(View view){
        Intent intent = new Intent(Cetakstruk.this,DetailTransaksiTruk.class);
        startActivity(intent);

    }
}