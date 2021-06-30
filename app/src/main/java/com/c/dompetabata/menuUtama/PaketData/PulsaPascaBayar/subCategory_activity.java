package com.c.dompetabata.menuUtama.PaketData.PulsaPascaBayar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;
import android.widget.ImageView;

import com.c.dompetabata.menuUtama.PaketData.PulsaPrabayar.modelPasca;
import com.c.dompetabata.R;

public class subCategory_activity extends AppCompatActivity {

    EditText nomorbelipulsa;
    private String url;
    ImageView iconproduk;
    modelPasca modelPascaa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category_activity);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Pulsa Pasca Bayar <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        iconproduk = findViewById(R.id.iconprodukPasca);
        nomorbelipulsa = findViewById(R.id.nomorbelipulsaPasca);


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