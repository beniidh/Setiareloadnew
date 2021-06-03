package com.c.dompetabata.Modal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Api.Value;
import com.c.dompetabata.Model.MOtpVerif;
import com.c.dompetabata.R;
import com.chaos.view.PinView;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OTPinsert extends AppCompatActivity {

    PinView otp;
    Button verif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_pinsert);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Verifikasi OTP<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        otp = findViewById(R.id.otpid);
        verif = findViewById(R.id.VerifikasiOTP);
        verif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(otp.length()<6){
                    StyleableToast.makeText(getApplicationContext(),"Lengkapi OTP", Toast.LENGTH_SHORT).show();
                }else {
                    String otpid = otp.getText().toString();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Value.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    Bundle ekstra = getIntent().getExtras();
                    MOtpVerif mOtpVerif = new MOtpVerif(ekstra.getString("user_id"),ekstra.getString("otp_id"),otpid);

                    Api api = retrofit.create(Api.class);
                    Call<MOtpVerif> call = api.verifOTP(mOtpVerif);
                    call.enqueue(new Callback<MOtpVerif>() {
                        @Override
                        public void onResponse(Call<MOtpVerif> call, Response<MOtpVerif> response) {
                            String code = response.body().getCode();
                            if(code.equals("200")){
                                StyleableToast.makeText(getApplicationContext(),"Berhasil", Toast.LENGTH_SHORT, R.style.mytoast).show();
                            }else {

                                StyleableToast.makeText(getApplicationContext(),code, Toast.LENGTH_SHORT, R.style.mytoast).show();
                                StyleableToast.makeText(getApplicationContext(),ekstra.getString("user_id"), Toast.LENGTH_SHORT).show();
                                StyleableToast.makeText(getApplicationContext(),ekstra.getString("otp_id"), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MOtpVerif> call, Throwable t) {

                        }
                    });




                }
            }
        });



    }
}