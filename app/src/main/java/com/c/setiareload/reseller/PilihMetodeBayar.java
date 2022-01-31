package com.c.setiareload.reseller;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Api.Value;
import com.c.setiareload.Helper.GpsTracker;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Helper.utils;
import com.c.setiareload.R;
import com.c.setiareload.Respon.ResponProfil;
import com.c.setiareload.menuUtama.PaketData.PulsaPrabayar.KonfirmasiPembayaran;
import com.c.setiareload.pinNew.pin_transaksi;
import com.c.setiareload.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PilihMetodeBayar extends AppCompatActivity {

    ImageView icon;
    Button konfirm;
    String saldo;
    int konfirmasi = 0;
    RelativeLayout server, saldoku;
    TextView saldokuket1, saldokuket2, saldoserverket1, saldoserverket2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_metode_bayar);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Metode Approve <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getContentProfil();
        Intent intent = getIntent();
        String totalharga = intent.getStringExtra("hargatotal");
        String iconn = intent.getStringExtra("urll");
        String id = intent.getStringExtra("ID");
        String harga = intent.getStringExtra("Harga");

        saldokuket1 = findViewById(R.id.saldokuket1);
        saldokuket2 = findViewById(R.id.saldokuket2);

        saldoserverket1 = findViewById(R.id.saldoserverket1);
        saldoserverket2 = findViewById(R.id.saldoserverket2);

        TextView pembayarankonfirmasi = findViewById(R.id.pemayarankonfirasi);
        pembayarankonfirmasi.setText(utils.ConvertRP(harga));
        icon = findViewById(R.id.iconkonfirmasi);
        Picasso.get().load(iconn).into(icon);
        konfirm = findViewById(R.id.konfirmbayar);

        server = findViewById(R.id.LinearSaldoServer);
        saldoku = findViewById(R.id.LinearSaldoku);

        server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saldo = "PAYLATTER";
                konfirmasi = 1;
                server.setBackground(getDrawable(R.drawable.bg_search_otppin));
                saldoku.setBackground(getDrawable(R.drawable.bg_edittextlogin));
                saldoserverket1.setTextColor(getColor(R.color.white));
                saldoserverket2.setTextColor(getColor(R.color.white));
                saldokuket1.setTextColor(getColor(R.color.black));
                saldokuket2.setTextColor(getColor(R.color.black));

            }
        });

        saldoku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saldo = "SALDOKU";
                konfirmasi = 1;
                saldoku.setBackground(getDrawable(R.drawable.bg_search_otppin));
                server.setBackground(getDrawable(R.drawable.bg_edittextlogin));
                saldokuket1.setTextColor(getColor(R.color.white));
                saldokuket2.setTextColor(getColor(R.color.white));
                saldoserverket1.setTextColor(getColor(R.color.black));
                saldoserverket2.setTextColor(getColor(R.color.black));
            }
        });


        konfirm.setOnClickListener(v -> {

            if(saldo !=null) {

                GpsTracker gpsTracker = new GpsTracker(getApplicationContext());
                String token = "Bearer " + Preference.getToken(getApplicationContext());
                mSetujuSaldo mSetuju = new mSetujuSaldo(id, "APPROVE", saldo,
                        Value.getMacAddress(getApplicationContext()), Value.getIPaddress(),
                        Value.getUserAgent(getApplicationContext()),
                        gpsTracker.getLongitude(),
                        gpsTracker.getLatitude());

                Api api = RetroClient.getApiServices();
                Call<ResponApproveSaldoR> call = api.ApproveSaldokuReselesser(token, mSetuju);
                call.enqueue(new Callback<ResponApproveSaldoR>() {
                    @Override
                    public void onResponse(Call<ResponApproveSaldoR> call, Response<ResponApproveSaldoR> response) {
                        String code = response.body().getCode();
                        if (code.equals("200")) {
                            Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                        }
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponApproveSaldoR> call, Throwable t) {

                    }
                });
            }else {
                Toast.makeText(getApplicationContext(), "Metode tidak boleh kosong", Toast.LENGTH_SHORT).show();
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



    public void getContentProfil() {

        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(Call<ResponProfil> call, Response<ResponProfil> response) {

                if (response.body().getCode().equals("200")) {
                    saldokuket2.setText(utils.ConvertRP(response.body().getData().getWallet().getSaldoku()));
                    saldoserverket2.setText(utils.ConvertRP(response.body().getData().getWallet().getPaylatter()));

                } else {
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT).show();

                }


            }


            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}