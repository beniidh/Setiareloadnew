package com.c.setiareload.MarkUP.markupSpesifik;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;

import com.c.setiareload.R;

public class markupSpesifikActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markup_spesifik);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Markup Global <b></font>"));
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
        super.onBackPressed();
    }
}