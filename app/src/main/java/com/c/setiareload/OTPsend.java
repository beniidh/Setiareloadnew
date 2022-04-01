package com.c.setiareload;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Api.Value;
import com.c.setiareload.Modal.OTPinsert;
import com.c.setiareload.Model.MRegisData;
import com.c.setiareload.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OTPsend extends AppCompatActivity {

    Button sendEmail,SendWaOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_psend);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Pilih metode OTP <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        sendEmail = findViewById(R.id.sendEmailOtp);
        sendEmail.setOnClickListener(v -> intentOTP());
        SendWaOtp = findViewById(R.id.SendWaOtp);
        SendWaOtp.setOnClickListener(v -> intentOTPWA());
    }

    public void intentOTP() {

        Preference.getSharedPreference(getBaseContext());
        String user_id = Preference.getKeyUserId(getBaseContext());
        String user_code = Preference.getKeyUserCode(getBaseContext());
        String phone = Preference.getKeyPhone(getBaseContext());
        String otp_id = Preference.getKeyOtpId(getBaseContext());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Value.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        MRegisData mRegisData = new MRegisData(user_id, user_code, phone, otp_id);
        Call<MRegisData> call = api.SendOTP(mRegisData);
        call.enqueue(new Callback<MRegisData>() {
            @Override
            public void onResponse(Call<MRegisData> call, Response<MRegisData> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    StyleableToast.makeText(getApplicationContext(), "OTP Telah dikirim", Toast.LENGTH_SHORT, R.style.mytoast).show();
                    Intent otpInsert = new Intent(OTPsend.this, OTPinsert.class);
                    otpInsert.putExtra("user_id", user_id);
                    otpInsert.putExtra("otp_id", otp_id);
                    Preference.setTrackRegister(getApplicationContext(), "2");
                    startActivity(otpInsert);

                } else {

                    StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast).show();
                }

            }

            @Override
            public void onFailure(Call<MRegisData> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();

            }
        });


    }

    public void intentOTPWA() {

        Preference.getSharedPreference(getBaseContext());
        String user_id = Preference.getKeyUserId(getBaseContext());
        String user_code = Preference.getKeyUserCode(getBaseContext());
        String phone = Preference.getKeyPhone(getBaseContext());
        String otp_id = Preference.getKeyOtpId(getBaseContext());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Value.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        MRegisData mRegisData = new MRegisData(user_id, user_code, phone, otp_id);
        Call<MRegisData> call = api.SendOTPWA(mRegisData);
        call.enqueue(new Callback<MRegisData>() {
            @Override
            public void onResponse(Call<MRegisData> call, Response<MRegisData> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    StyleableToast.makeText(getApplicationContext(), "OTP Telah dikirim", Toast.LENGTH_SHORT, R.style.mytoast).show();
                    Intent otpInsert = new Intent(OTPsend.this, OTPinsert.class);
                    otpInsert.putExtra("user_id", user_id);
                    otpInsert.putExtra("otp_id", otp_id);
                    Preference.setTrackRegister(getApplicationContext(), "2");
                    startActivity(otpInsert);

                } else {

                    StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast).show();
                }

            }

            @Override
            public void onFailure(Call<MRegisData> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();

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