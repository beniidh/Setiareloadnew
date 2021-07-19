package com.c.dompetabata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Api.Value;
import com.c.dompetabata.Helper.GpsTracker;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Model.MResestPIN;
import com.c.dompetabata.Respon.ResponResetPin;
import com.c.dompetabata.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;
import com.oakkub.android.PinEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPIN extends AppCompatActivity {

    PinEditText pinlamareset, pinsatureset, pinduareset;
    Button resetPIN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_p_i_n);

        pinlamareset = findViewById(R.id.pinlamareset);
        pinsatureset = findViewById(R.id.pinsatureset);
        pinduareset = findViewById(R.id.pinduareset);
        resetPIN = findViewById(R.id.resetPIN);

        resetPIN.setOnClickListener(v -> {
            ResetPin(pinlamareset.getText().toString(),pinsatureset.getText().toString(),pinduareset.getText().toString());

        });



    }

    private void ResetPin(String pinlama, String pinbaru, String pinbaruconfirm) {

        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();

        GpsTracker gpsTracker = new GpsTracker(getApplicationContext());
        MResestPIN mResestPIN = new MResestPIN(pinlama, pinbaru, pinbaruconfirm, Value.getMacAddress(), Value.getIPaddress(), Value.getUserAgent(getApplicationContext()), gpsTracker.getLatitude(), gpsTracker.getLongitude());
        Call<ResponResetPin> call = api.resetPIN(token, mResestPIN);
        call.enqueue(new Callback<ResponResetPin>() {
            @Override
            public void onResponse(Call<ResponResetPin> call, Response<ResponResetPin> response) {

                String code = response.body().getCode();
                if (code.equals("200")) {
                    StyleableToast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT, R.style.mytoast).show();
                    finish();

                } else {
                    StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast).show();

                }
            }

            @Override
            public void onFailure(Call<ResponResetPin> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan Internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });


    }


}