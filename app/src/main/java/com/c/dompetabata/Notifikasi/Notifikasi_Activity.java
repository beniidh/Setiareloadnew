package com.c.dompetabata.Notifikasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.Html;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.c.dompetabata.R;
import com.google.android.material.tabs.TabLayout;

public class Notifikasi_Activity extends AppCompatActivity {

    FrameLayout framelayoutnotifikasi;
    TabLayout tablayoutnotifikasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi_);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Notifikasi <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        framelayoutnotifikasi = (FrameLayout) findViewById(R.id.framelayoutnotifikasi);
        tablayoutnotifikasi = (TabLayout) findViewById(R.id.tablayoutnotifikasi);
        FragmentTransaksi transaksi = new FragmentTransaksi();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.framelayoutnotifikasi, transaksi);
        fragmentTransaction.commit(); // save the changes

        tablayoutnotifikasi.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()){

                    case 0 :

                        FragmentTransaksi transaksi = new FragmentTransaksi();
                        FragmentManager fm = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayoutnotifikasi, transaksi);
                        fragmentTransaction.commit(); // save the changes
                        break;

                    case 1:
                        FragmentPesan pesan = new FragmentPesan();
                        FragmentManager fmm = getSupportFragmentManager();
                        FragmentTransaction fragmentTransactionn = fmm.beginTransaction();
                        fragmentTransactionn.replace(R.id.framelayoutnotifikasi, pesan);
                        fragmentTransactionn.commit(); // save the changes
                        break;


                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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