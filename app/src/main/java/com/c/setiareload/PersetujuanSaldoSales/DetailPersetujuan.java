package com.c.setiareload.PersetujuanSaldoSales;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;

import com.c.setiareload.Helper.utils;
import com.c.setiareload.R;

public class DetailPersetujuan extends AppCompatActivity {
    Button idSetujuDetail, idTolakDetail;

    public static Activity detailPersetujuan;
    TextView saldopengajuanPersetujuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_persetujuan);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Detail <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        detailPersetujuan = this;

        idTolakDetail = findViewById(R.id.idTolakDetail);
        idSetujuDetail = findViewById(R.id.idSetujuDetail);
        String id = getIntent().getStringExtra("ID");
        String harga = getIntent().getStringExtra("Saldo");
        saldopengajuanPersetujuan = findViewById(R.id.saldopengajuanPersetujuan);

        saldopengajuanPersetujuan.setText(utils.ConvertRP(harga));


        idTolakDetail.setOnClickListener(v -> {

            showModal("REJECT SALES", id, harga);

        });

        idSetujuDetail.setOnClickListener(v -> {

            showModal("PENDING ADMIN", id, harga);
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


    public void showModal(String status, String id, String saldo) {

        ModalPinPersetujuan modalPinPersetujuan = new ModalPinPersetujuan();
        Bundle bundle = new Bundle();
        bundle.putString("ID", id);
        bundle.putString("STATUS", status);
        bundle.putString("SALDO", saldo);
        modalPinPersetujuan.setArguments(bundle);
        modalPinPersetujuan.show(getSupportFragmentManager(), "modalapprove");


    }
}