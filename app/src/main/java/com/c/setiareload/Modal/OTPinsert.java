package com.c.setiareload.Modal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Model.MOtpVerif;
import com.c.setiareload.Model.MRegisData;
import com.c.setiareload.R;
import com.c.setiareload.sharePreference.Preference;
import com.c.setiareload.syaratdanketentuan_activity;
import com.chaos.view.PinView;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OTPinsert extends AppCompatActivity {

    PinView otp;
    Button verif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_pinsert);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Verifikasi OTP<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        otp = findViewById(R.id.otpid);
        verif = findViewById(R.id.VerifikasiOTP);
        otp.setPasswordHidden(true);
        verif.setOnClickListener(v -> {

            if (otp.length() < 6) {
                StyleableToast.makeText(getApplicationContext(), "Lengkapi OTP", Toast.LENGTH_SHORT).show();
            } else {
                String otpid = otp.getText().toString();

                Bundle ekstra = getIntent().getExtras();
                MOtpVerif mOtpVerif = new MOtpVerif(Preference.getKeyUserId(getBaseContext()), Preference.getKeyOtpId(getBaseContext()), otpid);

                Api api = RetroClient.getApiServices();
                Call<MOtpVerif> call = api.verifOTP(mOtpVerif);
                call.enqueue(new Callback<MOtpVerif>() {
                    @Override
                    public void onResponse(Call<MOtpVerif> call, Response<MOtpVerif> response) {
                        String code = response.body().getCode();
                        if (code.equals("200")) {
                            StyleableToast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT, R.style.mytoast).show();
                            String token = response.body().getData().getToken();
                            Preference.getSharedPreference(getApplicationContext());
                            Preference.setToken(getApplicationContext(), token);
                            Intent intent = new Intent(OTPinsert.this, syaratdanketentuan_activity.class);
                            Preference.setTrackRegister(getApplicationContext(), "3");
                            startActivity(intent);
                        } else {

                            StyleableToast.makeText(getApplicationContext(), "OTP Salah", Toast.LENGTH_SHORT, R.style.mytoast).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<MOtpVerif> call, Throwable t) {

                    }
                });


            }
        });


        TextView timer = findViewById(R.id.timer);
        new CountDownTimer(30000, 1000) {


            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText("Kirim Ulang setelah: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                timer.setText("Kirim Ulang");
                timer.setTextColor(getColor(R.color.green));
                timer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intentOTP();
                    }
                });

            }
        }.start();
    }

    public void intentOTP() {

        Preference.getSharedPreference(getBaseContext());
        String user_id = Preference.getKeyUserId(getBaseContext());
        String user_code = Preference.getKeyUserCode(getBaseContext());
        String phone = Preference.getKeyPhone(getBaseContext());
        String otp_id = Preference.getKeyOtpId(getBaseContext());

        Api api = RetroClient.getApiServices();
        MRegisData mRegisData = new MRegisData(user_id, user_code, phone, otp_id);
        Call<MRegisData> call = api.SendOTP(mRegisData);
        call.enqueue(new Callback<MRegisData>() {
            @Override
            public void onResponse(Call<MRegisData> call, Response<MRegisData> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    StyleableToast.makeText(getApplicationContext(), "OTP Telah dikirim", Toast.LENGTH_SHORT, R.style.mytoast).show();

                } else {

                    StyleableToast.makeText(getApplicationContext(), "Belum dikirim", Toast.LENGTH_SHORT, R.style.mytoast).show();
                }

            }

            @Override
            public void onFailure(Call<MRegisData> call, Throwable t) {

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
}