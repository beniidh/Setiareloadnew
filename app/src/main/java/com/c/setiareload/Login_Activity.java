package com.c.setiareload;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.GpsTracker;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Model.Mphone;
import com.c.setiareload.pinNew.pinnew;
import com.c.setiareload.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import info.androidhive.fontawesome.FontDrawable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity {
    EditText numberphone;
    Button login_button;
    TextView register;
    ImageView logologin;
    ProgressBar progressBar;
    CheckBox checkBoxsave,savecheck;

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
        savecheck = findViewById(R.id.savecheck);


        //get informasi lokasi login
        getLocation();

        // set drawable end to editText Login
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
                validation(numberphone.getText().toString());
            }
        });

        savecheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked == true){

                    Preference.getSharedPreference(getApplicationContext());
                    Preference.setkredentials(getApplicationContext(),numberphone.getText().toString());

                }

            }
        });
    }

    private void validation(String number) {
        if (number.isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Nomor tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast2).show();
        } else {
            Api api = RetroClient.getApiServices();
            Mphone mphone = new Mphone(number);
            Call<Mphone> call = api.ChekPhone(mphone);
            call.enqueue(new Callback<Mphone>() {
                @Override
                public void onResponse(Call<Mphone> call, Response<Mphone> response) {
                    String code = response.body().getCode();
                    if (code.equals("200")){
                        Intent intent = new Intent(Login_Activity.this, pinnew.class);
                        intent.putExtra("number",number);
                        Preference.getSharedPreference(getApplicationContext());
                        Preference.setkredentials(getApplicationContext(),numberphone.getText().toString());
                        startActivity(intent);
                        numberphone.setText("");
                    }else {
                        StyleableToast.makeText(getApplicationContext(), "Nomor belum terdaftar", Toast.LENGTH_SHORT, R.style.mytoast2).show();
                    }
                }

                @Override
                public void onFailure(Call<Mphone> call, Throwable t) {
                    StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();

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
