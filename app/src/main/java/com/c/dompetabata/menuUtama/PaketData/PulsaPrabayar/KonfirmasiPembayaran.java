package com.c.dompetabata.menuUtama.PaketData.PulsaPrabayar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.c.dompetabata.R;
import com.squareup.picasso.Picasso;

public class KonfirmasiPembayaran extends AppCompatActivity {

    ImageView icon;
    Button konfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_pembayaran);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Metode Bayar <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        Intent intent = getIntent();
        String totalharga = intent.getStringExtra("hargatotal");
        String iconn = intent.getStringExtra("urll");

        TextView pembayarankonfirmasi = findViewById(R.id.pemayarankonfirasi);
        pembayarankonfirmasi.setText(totalharga);
        icon = findViewById(R.id.iconkonfirmasi);
        Picasso.get().load(iconn).into(icon);
        konfirm = findViewById(R.id.konfirmbayar);

        konfirm.setOnClickListener(v -> {
            Intent intent1 = new Intent(KonfirmasiPembayaran.this,TransaksiPending.class);
            startActivity(intent1);
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