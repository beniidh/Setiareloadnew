package com.c.dompetabata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView menu_bawah;
    TextView tulisan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Home <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        menu_bawah = findViewById(R.id.menu_bawah);
        tulisan = findViewById(R.id.tulisan);

        menu_bawah.setOnNavigationItemSelectedListener(this::onOptionsItemSelected);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
  switch (item.getItemId()){
      case R.id.home :
          tulisan.setText("Home");

          break;
      case R.id.transaksi :
          tulisan.setText("Transaksi");
          break;
      case R.id.chat :
          tulisan.setText("Chat");

  }

        return true;
    }

}