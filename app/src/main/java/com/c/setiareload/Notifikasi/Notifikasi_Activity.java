package com.c.setiareload.Notifikasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.widget.FrameLayout;

import com.c.setiareload.Fragment.RiwayatTransaksi.TabAdapter;
import com.c.setiareload.R;
import com.google.android.material.tabs.TabLayout;

public class Notifikasi_Activity extends AppCompatActivity {


    TabLayout tablayoutnotifikasi;
    ViewPager viewPagerNotifikasi;
    TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifikasi_);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Notifikasi <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        viewPagerNotifikasi = findViewById(R.id.viewPagerNotifikasi);
        tablayoutnotifikasi = (TabLayout) findViewById(R.id.tablayoutnotifikasi);

        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new FragmentTransaksi(), "Tab 1");
        tabAdapter.addFragment(new FragmentPesan(), "Tab 2");
        viewPagerNotifikasi.setAdapter(tabAdapter);
        tablayoutnotifikasi.setupWithViewPager(viewPagerNotifikasi);
        tablayoutnotifikasi.getTabAt(0).setText("Transaksi");
        tablayoutnotifikasi.getTabAt(1).setText("Pesan");



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