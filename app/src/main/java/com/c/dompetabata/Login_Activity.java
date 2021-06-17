package com.c.dompetabata;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Api.Value;
import com.c.dompetabata.Helper.GpsTracker;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Helper.utils;
import com.c.dompetabata.Model.Mlogin;
import com.c.dompetabata.Model.Mphone;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.IOException;
import java.util.HashMap;

import info.androidhive.fontawesome.FontDrawable;
import okhttp3.Interceptor;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Login_Activity extends AppCompatActivity {
    EditText numberphone;
    Button login_button;
    TextView register;
    ImageView logologin;
    ProgressBar progressBar;
    CheckBox checkBoxsave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        getSupportActionBar().hide();


        //ID Define
        numberphone = findViewById(R.id.numberphone);
        login_button = findViewById(R.id.login_button);
        register = findViewById(R.id.register);
        logologin = findViewById(R.id.logologin);
        progressBar = findViewById(R.id.progressbutton);
        checkBoxsave = findViewById(R.id.savecheck);


        setLogologin();

        FontDrawable drawable = new FontDrawable(this,R.string.userabata,true,false);
        Typeface type2 = ResourcesCompat.getFont(getApplicationContext(), R.font.abata);
        drawable.setTypeface(type2);
        drawable.setTextColor(getColor(R.color.black));
        drawable.setTextSize(20);
        numberphone.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);

        // Event Onclick for register activity
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(Login_Activity.this, Register_activity.class);
                startActivity(register);


            }
        });

        // Event Onclick for login activity
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocation();
                validation(numberphone.getText().toString());


            }
        });
    }

    private void validation(String number) {
        if (number.isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Nomor tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast).show();

        } else {

            Api api = RetroClient.getApiServices();
            Mphone mphone = new Mphone(number);
            Call<Mphone> call = api.ChekPhone(mphone);
            call.enqueue(new Callback<Mphone>() {
                @Override
                public void onResponse(Call<Mphone> call, Response<Mphone> response) {
                    String code = response.body().getCode();
                    if (code.equals("200")){

                        Intent intent = new Intent(Login_Activity.this,pin_activity.class);
                        intent.putExtra("number",number);
                        startActivity(intent);

                    }else {

                        StyleableToast.makeText(getApplicationContext(), "Nomor tidak belum terdaftar", Toast.LENGTH_SHORT, R.style.mytoast).show();

                    }
                }

                @Override
                public void onFailure(Call<Mphone> call, Throwable t) {

                }
            });


        }

    }

    private void setLogologin() {
        logologin.setImageDrawable(getDrawable(R.drawable.csoftware));
    }

    public void getLocation() {
        GpsTracker gpsTracker = new GpsTracker(Login_Activity.this);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

        } else {
            gpsTracker.showSettingsAlert();
        }
    }





}
