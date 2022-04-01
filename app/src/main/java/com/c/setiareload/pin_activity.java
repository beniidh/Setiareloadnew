package com.c.setiareload;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Api.Value;
import com.c.setiareload.Helper.GpsTracker;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Helper.utils;
import com.c.setiareload.Model.Mlogin;
import com.c.setiareload.sharePreference.Preference;
import com.google.firebase.messaging.FirebaseMessaging;
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
    int salah = 0;
    TextView warningpinsalah;
    String deviceToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_activity);

        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Insert PIN<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        warningpinsalah = findViewById(R.id.warningpinsalah);


        warningpinsalah.setOnClickListener(v -> {
            Intent intent = new Intent(pin_activity.this, GantiPin.class);
            startActivity(intent);

        });

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
                    String pinenkrip = utils.hmacSha(pinn);
                    Login(pinenkrip);
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
        Intent intent = new Intent(pin_activity.this, Login_Activity.class);
        startActivity(intent);
        finish();
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


        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            String deviceToken = task.getResult();
            Mlogin mlogin = new Mlogin("SRVID00000014",telepon, pin, deviceToken, IP, Value.getMacAddress(getApplicationContext()), useragent, longlitude, latitude);

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
                        Preference.getSharedPreference(getApplicationContext());
                        Preference.setToken(getApplicationContext(), token);
                        finish();

                    } else if (code.equals("403")) {

                        StyleableToast.makeText(getApplicationContext(), response.body().getError() + " Silahkan hubungi Admin", Toast.LENGTH_LONG, R.style.mytoast).show();
                        Intent intent = new Intent(pin_activity.this, Login_Activity.class);
                        startActivity(intent);
                        finish();


                    } else {
                        progressBar.setVisibility(View.GONE);
                        pin1.setText("");
                        StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast).show();
                        salah += 1;

                    }


                }

                @Override
                public void onFailure(Call<Mlogin> call, Throwable t) {
//                progressBar.setVisibility(View.INVISIBLE);
                    StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();

                }
            });

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
        gpsTracker = new GpsTracker(pin_activity.this);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

        } else {
            gpsTracker.showSettingsAlert();
        }
    }


}