package com.c.dompetabata.SaldoServer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Helper.utils;
import com.c.dompetabata.Modal.ModalMetodePemayaran;
import com.c.dompetabata.R;
import com.c.dompetabata.sharePreference.Preference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopupSaldoServer extends AppCompatActivity {

    TextView tagihanSaldoServer,tanggalTagihan;
    RelativeLayout LinearBayartagihan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_saldo_server);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Top Up Saldo Server <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        tagihanSaldoServer = findViewById(R.id.tagihanSaldoServer);
        LinearBayartagihan = findViewById(R.id.LinearBayartagihan);
        tanggalTagihan = findViewById(R.id.tanggalTagihan);
        getTagihan();
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

    public void RiwayatTagihan(View view){
        Intent intent = new Intent(TopupSaldoServer.this, RiwayatTagihan.class);
        startActivity(intent);
    }
    public void BayarTagihan(View view){
        String codebayar ="saldoserver";
        Bundle bundle = new Bundle();
        bundle.putString("saldotipe",codebayar);
        ModalMetodePemayaran modalPembayaran = new ModalMetodePemayaran();
        modalPembayaran.setArguments(bundle);
        modalPembayaran.show(getSupportFragmentManager(), "ModalPebayaran");
    }

    public void getTagihan(){

        String token = "Bearer "+ Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponTagihanPayLatter> call = api.getTagihan(token);
        call.enqueue(new Callback<ResponTagihanPayLatter>() {
            @Override
            public void onResponse(Call<ResponTagihanPayLatter> call, Response<ResponTagihanPayLatter> response) {

                if (response.body().getCode().equals("200")){
                    tagihanSaldoServer.setText(utils.ConvertRP(response.body().getData().getTotal_bill()));
                    Preference.setSaldoServer(getApplicationContext(),response.body().getData().getTotal_bill());
                    String tanggal = response.body().getData().getDue_date();
                    tanggalTagihan.setText(tanggal.substring(0,10));
                    Preference.setIdUPP(getApplicationContext(),response.body().getData().getId());
                }else {

                    LinearBayartagihan.setEnabled(false);
                    Toast.makeText(TopupSaldoServer.this, response.body().getError()+" Belum ada tagihan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponTagihanPayLatter> call, Throwable t) {

            }
        });
    }
}