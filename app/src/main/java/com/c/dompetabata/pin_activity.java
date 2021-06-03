package com.c.dompetabata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.oakkub.android.PinEditText;

public class pin_activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_activity);

        PinEditText coba = findViewById(R.id.pinEditText);
        Button cobapin = findViewById(R.id.cobapin);
        PinView coba3 = findViewById(R.id.firstPinView);


        coba.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (coba.length()==6){

                    Toast.makeText(getApplicationContext(),coba.getText().toString(),Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        cobapin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),coba3.getText().toString(),Toast.LENGTH_SHORT).show();
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