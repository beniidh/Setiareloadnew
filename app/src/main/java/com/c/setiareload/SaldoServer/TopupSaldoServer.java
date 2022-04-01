package com.c.setiareload.SaldoServer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Helper.utils;
import com.c.setiareload.Modal.ModalMetodePemayaran;
import com.c.setiareload.R;
import com.c.setiareload.Respon.ResponProfil;
import com.c.setiareload.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TopupSaldoServer extends AppCompatActivity {

    TextView tagihanSaldoServer,tanggalTagihan,TotalSS,PengembalianSS,limitsaldoserverr;
    RelativeLayout LinearBayartagihan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_saldo_server);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Top Up Saldo Server <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        tagihanSaldoServer = findViewById(R.id.tagihanSaldoServer);
        LinearBayartagihan = findViewById(R.id.LinearBayartagihan);
        limitsaldoserverr = findViewById(R.id.limitsaldoserverr);

        TotalSS = findViewById(R.id.TotalSS);
        PengembalianSS = findViewById(R.id.PengembalianSS);
        getContentProfil();

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
                    PengembalianSS.setText(utils.ConvertRP(response.body().getData().getTotal_bill()));
                    TotalSS.setText(utils.ConvertRP(response.body().getData().getTotal_bill()));
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

    public void getContentProfil() {

        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(Call<ResponProfil> call, Response<ResponProfil> response) {
                String limit = response.body().getData().getWallet().getPaylater_limit();
                if(limit == null){
                    limitsaldoserverr.setText(utils.ConvertRP("0"));
                }else {
                    limitsaldoserverr.setText(utils.ConvertRP(response.body().getData().getWallet().getPaylater_limit()));
                }



            }
            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }
}