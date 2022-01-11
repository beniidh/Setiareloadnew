package com.c.setiareload.menuUtama.PaketData.PulsaPrabayar;

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
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Helper.utils;
import com.c.setiareload.R;
import com.c.setiareload.Respon.ResponProfil;
import com.c.setiareload.pinNew.pin_transaksi;
import com.c.setiareload.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KonfirmasiPembayaran extends AppCompatActivity {

    ImageView icon;
    Button konfirm;
    String saldo;
    int konfirmasi = 0;
    RelativeLayout server, saldoku;
    TextView saldokuket1, saldokuket2, saldoserverket1, saldoserverket2;
    @SuppressLint("StaticFieldLeak")
 public static Activity konifirmpembayaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfirmasi_pembayaran);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Metode Bayar <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getContentProfil();
        Intent intent = getIntent();
        String totalharga = intent.getStringExtra("hargatotal");
        String iconn = intent.getStringExtra("urll");
        konifirmpembayaran = this;

        saldokuket1 = findViewById(R.id.saldokuket1);
        saldokuket2 = findViewById(R.id.saldokuket2);

        saldoserverket1 = findViewById(R.id.saldoserverket1);
        saldoserverket2 = findViewById(R.id.saldoserverket2);

        TextView pembayarankonfirmasi = findViewById(R.id.pemayarankonfirasi);
        pembayarankonfirmasi.setText(totalharga);
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

            if (konfirmasi == 0) {
                StyleableToast.makeText(getApplicationContext(), "Pilih Metode Pembayaran", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            } else {
                Intent intent1 = new Intent(KonfirmasiPembayaran.this,pin_transaksi.class);
                Bundle bundle = new Bundle();
                bundle.putString("RefID", getIntent().getStringExtra("RefID"));
                bundle.putString("sku_code", getIntent().getStringExtra("sku_code"));
                bundle.putString("inquiry", getIntent().getStringExtra("inquiry"));
                bundle.putString("iconn", getIntent().getStringExtra(iconn));
                bundle.putString("nomorr", getIntent().getStringExtra("nomorr"));
                bundle.putString("wallettype", saldo);
                intent1.putExtras(bundle);
                startActivity(intent1);
//
//                modalPinTransaksiPra.show(getSupportFragmentManager(), "Transaksi");
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