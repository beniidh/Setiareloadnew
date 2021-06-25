package com.c.dompetabata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Api.Value;
import com.c.dompetabata.Helper.GpsTracker;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Helper.utils;
import com.c.dompetabata.Model.Mlogin;
import com.c.dompetabata.sharePreference.Preference;
import com.chaos.view.PinView;
import com.muddzdev.styleabletoast.StyleableToast;
import com.oakkub.android.PinEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pin_activity extends AppCompatActivity {

    ProgressBar progressBar;
    PinEditText pin1;
    String telepon;
    GpsTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_activity);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Insert PIN<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

       getLocation();

        progressBar = findViewById(R.id.progressPIN);

        pin1 = findViewById(R.id.pinEditText);
        pin1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (pin1.length() == 6) {
                    progressBar.setVisibility(View.VISIBLE);
                    String pinn = pin1.getText().toString();
                    Login(pinn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

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

    private void Login(String pin) {


        double longlitude = gpsTracker.getLongitude();
        double latitude = gpsTracker.getLatitude();
        String useragent = getUserAgent();
        String IP = getIPaddress();
        Intent tlp = getIntent();

        Preference.getSharedPreference(getApplicationContext());
        telepon = Preference.getKredentials(getApplicationContext());

        if (telepon.equals("")) {

            telepon = tlp.getStringExtra("number");
        }


        Mlogin mlogin = new Mlogin(telepon, pin, IP, useragent, longlitude, latitude);

        Api api = RetroClient.getApiServices();
        Call<Mlogin> call = api.Login(mlogin);
        call.enqueue(new Callback<Mlogin>() {
            @Override
            public void onResponse(Call<Mlogin> call, Response<Mlogin> response) {

                String code = response.body().getCode();

                if (code.equals("200")) {
                    progressBar.setVisibility(View.GONE);
                    Intent home = new Intent(pin_activity.this, drawer_activity.class);
                    startActivity(home);
                  String token = response.body().getData().getToken();

                    Preference.getSharedPreference(pin_activity.this);
                    Preference.setToken(getApplicationContext(),token);


                    finish();

                } else {
                    progressBar.setVisibility(View.GONE);
                    pin1.setText("");

                    StyleableToast.makeText(getApplicationContext(), "PIN salah", Toast.LENGTH_SHORT, R.style.mytoast).show();
                }


            }

            @Override
            public void onFailure(Call<Mlogin> call, Throwable t) {
//                progressBar.setVisibility(View.INVISIBLE);

                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast).show();

            }
        });


    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
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
        gpsTracker = new GpsTracker(pin_activity.this);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

        } else {
            gpsTracker.showSettingsAlert();
        }
    }

}