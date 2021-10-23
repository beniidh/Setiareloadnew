package com.c.setiareload;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Api.Value;
import com.c.setiareload.Helper.GpsTracker;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Model.mResetPassword;
import com.c.setiareload.Respon.ResponResetPassword;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GantiPin extends AppCompatActivity {

    EditText inputemail;
    Button resetPIN;
    TextView keterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_pin);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Lupa PIN<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        inputemail = findViewById(R.id.inputEmailGantiPin);
        resetPIN = findViewById(R.id.ResetPINGanti);
        keterangan = findViewById(R.id.keterangangantipin);

        resetPIN.setOnClickListener(v -> {

            if (inputemail.getText().toString().isEmpty()) {
                StyleableToast.makeText(getApplicationContext(), "Email tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            } else {
                resetPassword(inputemail.getText().toString());
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
        Intent intent = new Intent(this, Login_Activity.class);
        startActivity(intent);
        finish();
    }

    public void resetPassword(String email) {
        GpsTracker gpsTracker = new GpsTracker(getApplicationContext());
        Api api = RetroClient.getApiServices();
        mResetPassword mResetPassword = new mResetPassword(email, Value.getIPaddress(), Value.getMacAddress(getApplicationContext()), Value.getUserAgent(getApplicationContext()), gpsTracker.getLongitude(), gpsTracker.getLatitude());
        Call<ResponResetPassword> call = api.resetPassword(mResetPassword);
        call.enqueue(new Callback<ResponResetPassword>() {
            @Override
            public void onResponse(Call<ResponResetPassword> call, Response<ResponResetPassword> response) {
                String respon = response.body().getCode();
                if (respon.equals("200")) {

                    keterangan.setText(response.body().getData().getMessage());
                } else {

                    keterangan.setText(response.body().getError());
                }
            }

            @Override
            public void onFailure(Call<ResponResetPassword> call, Throwable t) {

            }
        });


    }
}