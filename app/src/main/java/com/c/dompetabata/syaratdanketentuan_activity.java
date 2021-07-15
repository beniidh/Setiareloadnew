package com.c.dompetabata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class syaratdanketentuan_activity extends AppCompatActivity {

    CheckBox setuju;
    Button setujui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syaratdanketentuan_activity);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Syarat & Ketentuan <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        setuju = findViewById(R.id.checksetuju);


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

    public void SendOTP(View view){
        Bundle ekstra =getIntent().getExtras();
        Intent intent = new Intent(syaratdanketentuan_activity.this,InsertPIN_activity.class);
        startActivity(intent);
    }
}