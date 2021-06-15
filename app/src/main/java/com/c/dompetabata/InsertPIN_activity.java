package com.c.dompetabata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.GpsTracker;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Helper.utils;
import com.c.dompetabata.Model.MsetPIN;
import com.muddzdev.styleabletoast.StyleableToast;
import com.oakkub.android.PinEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertPIN_activity extends AppCompatActivity {

    PinEditText pinsatu,pindua;
    Button selesaiInsertPIN;
    GpsTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_p_i_n_activity);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Insert PIN<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        pinsatu = findViewById(R.id.pinsatu);
        pindua = findViewById(R.id.pindua);
        selesaiInsertPIN = findViewById(R.id.selesaiInsertPIN);


        pindua.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                String pinsatuu = pinsatu.getText().toString();
                String pinduaa = pindua.getText().toString();
                if(pindua.length() == 6){
                    if(pinsatuu.equals(pinduaa)){

                        selesaiInsertPIN.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                getLocation();
                                String UserAgent = getUserAgent();
                                String IP = getIPaddress();
                                String pinsattu = pinsatu.getText().toString();
                                String pinddua = pindua.getText().toString();
                                String MacAddres = getMacAddress();
                                double longlitut = gpsTracker.getLongitude();
                                double latitude = gpsTracker.getLatitude();
                                Intent intent = getIntent();
                                String token = intent.getStringExtra("token");


                                Api api = RetroClient.getApiServices();
                                MsetPIN msetPIN = new MsetPIN(pinsattu,pinddua,MacAddres,IP,UserAgent,longlitut,latitude);
                                Call<MsetPIN> call = api.SetPIN("Bearer "+token,msetPIN);
                                call.enqueue(new Callback<MsetPIN>() {
                                    @Override
                                    public void onResponse(Call<MsetPIN> call, Response<MsetPIN> response) {
                                        String code = response.body().getCode();
                                      if(code.equals("200")){

                                          StyleableToast.makeText(getApplicationContext(),"Set PIN Berhasil ",Toast.LENGTH_SHORT,R.style.mytoast).show();
                                          Intent home = new Intent(InsertPIN_activity.this,drawer_activity.class);
                                          home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                                                  Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                                  Intent.FLAG_ACTIVITY_NEW_TASK);
                                          startActivity(home);


                                      }


                                    }

                                    @Override
                                    public void onFailure(Call<MsetPIN> call, Throwable t) {

                                    }
                                });



                            }
                        });


                    }else {

                        StyleableToast.makeText(getApplicationContext(),"PIN tidak sama ",Toast.LENGTH_SHORT,R.style.mytoast).show();
                    }

                }


            }
        });



    }

    private String getUserAgent() {

        String ua = new WebView(this).getSettings().getUserAgentString();
        return ua;
    }

    private String getIPaddress() {

        String IP = utils.getIPAddress(true);
        return IP;
    }

    private String getMacAddress() {
        String MAC = utils.getMACAddress("wlan0");//phone if pc use eth0 if phone wlan0
        return MAC;

    }

    public void getLocation() {
        gpsTracker = new GpsTracker(InsertPIN_activity.this);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

        } else {
            gpsTracker.showSettingsAlert();
        }
    }
}