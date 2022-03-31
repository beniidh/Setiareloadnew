package com.c.latansa.SaldoServer;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.c.latansa.Api.Api;
import com.c.latansa.Helper.RetroClient;
import com.c.latansa.Helper.utils;
import com.c.latansa.R;
import com.c.latansa.Respon.ResponProfil;
import com.c.latansa.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BayarSalesServer extends AppCompatActivity {
    TextView saldokusales,namasalesserver;
    Button idbayarsaldokuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar_sales_server);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Pembayaran Saldo Server <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
getContentProfil();

        saldokusales = findViewById(R.id.saldokusales);
        namasalesserver = findViewById(R.id.namasalesserver);
        saldokusales.setText(utils.ConvertRP( Preference.getSaldoServer(getApplicationContext())));
        idbayarsaldokuButton = findViewById(R.id.idbayarsaldokuButton);

        idbayarsaldokuButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("kode","sales");
            ModalPinUPP modalpinupp = new ModalPinUPP();
            modalpinupp.setArguments(bundle);
            modalpinupp.show(getSupportFragmentManager(), "topupsaldoserver");
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

    public Context getContextt() {
        finish();
        return getApplicationContext();

    }


    public void getContentProfil() {

        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(Call<ResponProfil> call, Response<ResponProfil> response) {
                namasalesserver.setText(response.body().getData().getSales().getName());


            }

            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }
}