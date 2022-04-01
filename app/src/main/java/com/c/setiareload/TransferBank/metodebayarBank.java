package com.c.setiareload.TransferBank;

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

import androidx.appcompat.app.AppCompatActivity;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Helper.utils;
import com.c.setiareload.R;
import com.c.setiareload.Respon.ResponProfil;
import com.c.setiareload.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class metodebayarBank extends AppCompatActivity {

    ImageView icon;
    Button konfirm;
    String saldo;
    int konfirmasi = 0;
    RelativeLayout server, saldoku;
    TextView saldokuket1, saldokuket2, saldoserverket1, saldoserverket2;
    static Activity metodebayar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metodebayar_bank);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Metode Bayar <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getContentProfil();
        saldokuket1 = findViewById(R.id.saldokuket1);
        saldokuket2 = findViewById(R.id.saldokuket2);
        metodebayar=this;

        saldoserverket1 = findViewById(R.id.saldoserverket1);
        saldoserverket2 = findViewById(R.id.saldoserverket2);

        TextView pembayarankonfirmasi = findViewById(R.id.pemayarankonfirasi);
        pembayarankonfirmasi.setText(utils.ConvertRP(getIntent().getStringExtra("Amount")));
        icon = findViewById(R.id.iconkonfirmasi);
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

                Intent intent = getIntent();
                String scu = intent.getStringExtra("sku_code");
                String nomor = intent.getStringExtra("customer_no");
                String jumlah = intent.getStringExtra("Amount");
                String refid = intent.getStringExtra("RefID");

                ModalPinTransferBank modalPinTransferBank = new ModalPinTransferBank();
                Bundle bundle = new Bundle();
                bundle.putString("wallettype", saldo);
                bundle.putString("sku_code",scu);
                bundle.putString("customer_no",nomor);
                bundle.putString("Amount",jumlah);
                bundle.putString("RefID",refid);
                modalPinTransferBank.setArguments(bundle);
                modalPinTransferBank.show(getSupportFragmentManager(), "Transaksi");
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
                Toast.makeText(getApplicationContext(), "Periksa sambungan internet", Toast.LENGTH_SHORT).show();


            }
        });
    }


}