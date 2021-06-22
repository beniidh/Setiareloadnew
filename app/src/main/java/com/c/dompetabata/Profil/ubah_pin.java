package com.c.dompetabata.Profil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;

import com.c.dompetabata.Modal.ModalPinBaru;
import com.c.dompetabata.R;
import com.chaos.view.PinView;
import com.oakkub.android.PinEditText;

public class ubah_pin extends AppCompatActivity {

    PinEditText ubahpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_pin);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Ubah PIN <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

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
                    
                    ModalPinBaru modalPinBaru = new ModalPinBaru();
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