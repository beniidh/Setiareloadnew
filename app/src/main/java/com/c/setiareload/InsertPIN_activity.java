package com.c.setiareload;

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

import com.c.setiareload.Api.Api;
import com.c.setiareload.Api.Value;
import com.c.setiareload.Helper.GpsTracker;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Helper.utils;
import com.c.setiareload.Model.MsetPIN;
import com.c.setiareload.sharePreference.Preference;
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

        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Insert PIN<b></font>"));
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
                                String MacAddres = Value.getMacAddress(getApplicationContext());
                                double longlitut = gpsTracker.getLongitude();
                                double latitude = gpsTracker.getLatitude();
                                Intent intent = getIntent();
                                String token = Preference.getToken(getApplicationContext());

                                String pinsatuenkrip = utils.hmacSha(pinsattu);
                                String pinduaenkrip = utils.hmacSha(pinddua);

                                
                                Api api = RetroClient.getApiServices();
                                MsetPIN msetPIN = new MsetPIN(pinsatuenkrip,pinduaenkrip,MacAddres,IP,UserAgent,longlitut,latitude);
                                Call<MsetPIN> call = api.SetPIN("Bearer "+token,msetPIN);
                                call.enqueue(new Callback<MsetPIN>() {
                                    @Override
                                    public void onResponse(Call<MsetPIN> call, Response<MsetPIN> response) {
                                        String code = response.body().getCode();
                                        String error = response.body().getError();
                                      if(code.equals("200")){

                                          StyleableToast.makeText(getApplicationContext(),"Set PIN Berhasil ",Toast.LENGTH_SHORT,R.style.mytoast).show();
                                          Intent home = new Intent(InsertPIN_activity.this,drawer_activity.class);
                                          home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|
                                                  Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                                  Intent.FLAG_ACTIVITY_NEW_TASK);
                                          Preference.setTrackRegister(getApplicationContext(),"");
                                          startActivity(home);


                                      }else {
                                          StyleableToast.makeText(getApplicationContext(),error,Toast.LENGTH_SHORT,R.style.mytoast).show();

                                      }


                                    }

                                    @Override
                                    public void onFailure(Call<MsetPIN> call, Throwable t) {
                                        StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
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



    public void getLocation() {
        gpsTracker = new GpsTracker(InsertPIN_activity.this);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

        } else {
            gpsTracker.showSettingsAlert();
        }
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