package com.c.dompetabata.Profil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Respon.ResponProfil;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.R;
import com.c.dompetabata.sharePreference.Preference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profil extends AppCompatActivity {
    TextView namaprofil, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Profil <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        namaprofil = findViewById(R.id.namaprofil);
        phone = findViewById(R.id.nomorprofil);
        getContentProfil();

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
                namaprofil.setText(response.body().getData().getName());
                phone.setText(response.body().getData().getPhone());
            }

            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {

            }
        });
    }

    public void UbahProfil(View view){

        Intent intent = new Intent(Profil.this,ubah_profil.class);
        startActivity(intent);
    }
    public void UbahPin(View view){

        Intent intent = new Intent(Profil.this,ubah_pin.class);
        startActivity(intent);
    }
    public void point(View view){

        Intent intent = new Intent(Profil.this,Point.class);
        startActivity(intent);
    }
    public void Device(View view){

        Intent intent = new Intent(Profil.this,Device.class);
        startActivity(intent);
    }
}



