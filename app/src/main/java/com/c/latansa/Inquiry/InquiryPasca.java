package com.c.latansa.Inquiry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;

import com.c.latansa.Login_Activity;
import com.c.latansa.R;

public class InquiryPasca extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiry_pasca);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Transaksi<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Login_Activity.class);
        startActivity(intent);
        finish();
    }
}