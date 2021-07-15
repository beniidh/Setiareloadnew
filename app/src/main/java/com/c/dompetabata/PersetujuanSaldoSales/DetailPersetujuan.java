package com.c.dompetabata.PersetujuanSaldoSales;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Api.Value;
import com.c.dompetabata.Helper.GpsTracker;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Helper.utils;
import com.c.dompetabata.R;
import com.c.dompetabata.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPersetujuan extends AppCompatActivity {
    Button idSetujuDetail, idTolakDetail;

    public static Activity detailPersetujuan;
    TextView saldopengajuanPersetujuan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_persetujuan);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Detail <b></font>"));
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

            showModal("Di Tolak", id, harga);

        });

        idSetujuDetail.setOnClickListener(v -> {

            showModal("Di Setujui", id, harga);
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