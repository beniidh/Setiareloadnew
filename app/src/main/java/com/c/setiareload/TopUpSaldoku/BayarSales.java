package com.c.setiareload.TopUpSaldoku;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.c.setiareload.Adapter.AdapterSubMenuSide;
import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Helper.utils;
import com.c.setiareload.Model.MSubMenu;
import com.c.setiareload.R;
import com.c.setiareload.Respon.ResponProfil;
import com.c.setiareload.drawer_activity;
import com.c.setiareload.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BayarSales extends AppCompatActivity implements ModalPinTopUpSaldoku.BottomSheetListeneridUpload {
    TextView saldokusales, namasales;
    Button idbayarsaldokuButton;
    public static Activity a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bayar_sales);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Pembayaran Saldoku <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        a = this;
        getContentProfil();
        saldokusales = findViewById(R.id.saldokusales);
        namasales = findViewById(R.id.salesName);
        saldokusales.setText(utils.ConvertRP(Preference.getSaldoku(getApplicationContext())));
        idbayarsaldokuButton = findViewById(R.id.idbayarsaldokuButton);

        idbayarsaldokuButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("kode", "sales");
            ModalPinTopUpSaldoku modalPinTopUpSaldoku = new ModalPinTopUpSaldoku();
            modalPinTopUpSaldoku.setArguments(bundle);
            modalPinTopUpSaldoku.show(getSupportFragmentManager(), "topupsaldoku");
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

    @Override
    public void onButtonClickIdUpload(String id) {

    }

    public void getContentProfil() {

        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(Call<ResponProfil> call, Response<ResponProfil> response) {
                namasales.setText(response.body().getData().getSales().getName());


            }

            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }
}