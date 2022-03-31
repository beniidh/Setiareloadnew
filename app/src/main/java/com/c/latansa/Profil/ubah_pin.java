package com.c.latansa.Profil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;

import com.c.latansa.Helper.GpsTracker;
import com.c.latansa.Modal.ModalPinBaru;
import com.c.latansa.R;
import com.oakkub.android.PinEditText;

public class ubah_pin extends AppCompatActivity {

    PinEditText ubahpin;
    GpsTracker gpsTracker;
    public static Activity pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_pin);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Ubah PIN <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        pin = this;

        ubahpin = findViewById(R.id.ubahpin);
        ubahpin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(ubahpin.length() == 6){
                    Bundle bundle = new Bundle();
                    ModalPinBaru modalPinBaru = new ModalPinBaru();
                    bundle.putString("PINedit",ubahpin.getText().toString());
                    modalPinBaru.setArguments(bundle);
                    modalPinBaru.show(getSupportFragmentManager(),"pin baru");
                }

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