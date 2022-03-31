package com.c.latansa.TarikKomisi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.latansa.Adapter.AdapterSubMenuSide;
import com.c.latansa.Api.Api;
import com.c.latansa.Helper.RetroClient;
import com.c.latansa.Helper.utils;
import com.c.latansa.Login_Activity;
import com.c.latansa.Model.MSubMenu;
import com.c.latansa.R;
import com.c.latansa.Respon.ResponProfil;
import com.c.latansa.TopUpSaldoku.NumberChageLive;
import com.c.latansa.drawer_activity;
import com.c.latansa.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TarikKomisiSales_Activity extends AppCompatActivity implements ModalTarikKomisi.callback {

    EditText jumlahWd;
    TextView jumlahKomisi;
    Button ButtonWd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarik_komisi_sales);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Tarik Komisi<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        jumlahWd = findViewById(R.id.jumlahWd);
        jumlahKomisi = findViewById(R.id.jumlahKomisi);
        ButtonWd = findViewById(R.id.ButtonWd);
        getContentProfil();
        jumlahWd.addTextChangedListener(new NumberChageLive(jumlahWd));

        ButtonWd.setOnClickListener(v -> {

            if(jumlahWd.getText().toString().isEmpty()){

                Toast.makeText(getApplicationContext(),"Jumlah tidak boleh kosong",Toast.LENGTH_SHORT).show();

            }else {

                ModalTarikKomisi modalTarikKomisi = new ModalTarikKomisi();
                Bundle bundle = new Bundle();
                bundle.putString("jumlah",jumlahWd.getText().toString().replaceAll(",",""));
                modalTarikKomisi.setArguments(bundle);
                modalTarikKomisi.show(getSupportFragmentManager(),"PIN");


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

                if(response.body().getCode().equals("200")){

                    jumlahKomisi.setText(utils.ConvertRP(response.body().getData().getWallet().getKomisi()));
                }else {
                    Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }

    @Override
    public void onClick(String hasil) {
        getContentProfil();
        jumlahWd.setText("");

    }
}