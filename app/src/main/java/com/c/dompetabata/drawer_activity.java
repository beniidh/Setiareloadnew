package com.c.dompetabata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.dompetabata.Adapter.AdapterKabupaten;
import com.c.dompetabata.Adapter.AdapterSubMenuSide;
import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Fragment.ChatFragment;
import com.c.dompetabata.Fragment.HomeFragment;
import com.c.dompetabata.Fragment.HomeViewModel;
import com.c.dompetabata.Fragment.TransaksiFragment;
import com.c.dompetabata.Model.MSubMenu;
import com.c.dompetabata.Model.ModelKabupaten;
import com.c.dompetabata.Notifikasi.Notifikasi_Activity;
import com.c.dompetabata.Respon.ResponBanner;
import com.c.dompetabata.Respon.ResponMenu;
import com.c.dompetabata.Respon.ResponProfil;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Model.MBanner;
import com.c.dompetabata.Model.Micon;
import com.c.dompetabata.Profil.Profil;
import com.c.dompetabata.sharePreference.Preference;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class drawer_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView menu_bawah;
    Toolbar toolbar;
    TextView tulisan, navheadernamakonter;
    DrawerLayout drawer_layout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    Fragment fragment1, fragment2, fragment3;
    MenuItem menuItem;
    LinearLayout profil;
    ArrayList<Micon> micons = new ArrayList<>();
    ArrayList<MBanner> mBanners = new ArrayList<>();
    HomeViewModel myViewModel;

    AdapterSubMenuSide adapterSubMenuSide;
    ArrayList<MSubMenu> mSubMenus = new ArrayList<>();
    RecyclerView submenu;
    ImageView notifikasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_activity);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        notifikasi = findViewById(R.id.notifikasiID);

        notifikasi.setOnClickListener(v -> {

            Intent intent = new Intent(drawer_activity.this, Notifikasi_Activity.class);
            startActivity(intent);

        });

        ImageView togglenav = findViewById(R.id.togglenavheader);
        getContentProfil();
        submenu = findViewById(R.id.ReySubMenu);
        adapterSubMenuSide = new AdapterSubMenuSide(getApplicationContext(), mSubMenus);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        submenu.setLayoutManager(mLayoutManager);
        submenu.setAdapter(adapterSubMenuSide);


        menu_bawah = findViewById(R.id.menu_bawah);
        tulisan = findViewById(R.id.tulisan);
        navheadernamakonter = findViewById(R.id.navheadernamakonter);
        getMicons();

        getIconBanner();
        myViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        myViewModel.init();

        profil = findViewById(R.id.LinearProfil);
        profil.setOnClickListener(v -> {

            Intent intent = new Intent(drawer_activity.this, Profil.class);
            startActivity(intent);
        });
        navigationView = findViewById(R.id.nav_view);
        drawer_layout = findViewById(R.id.drawer_layout);
        togglenav.setOnClickListener(v -> drawer_layout.open());
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        toggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();


        fragment1 = new TransaksiFragment();
        fragment2 = new ChatFragment();
        fragment3 = new HomeFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fLayout, fragment3);
        fragmentTransaction.commit(); // save the changes


        menu_bawah.setOnNavigationItemSelectedListener(this::onOptionsItemSelected);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {


            case R.id.home:
                // create a FragmentManager
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fLayout, fragment3);
                fragmentTransaction.commit(); // save the changes
                break;

            case R.id.transaksi:

                FragmentManager fm2 = getSupportFragmentManager();
                FragmentTransaction fragmentTransactionn = fm2.beginTransaction();
                fragmentTransactionn.replace(R.id.fLayout, fragment1);
                fragmentTransactionn.commit(); // save the changes

                break;
            case R.id.chat:

                FragmentManager fm3 = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction2 = fm3.beginTransaction();
                fragmentTransaction2.replace(R.id.fLayout, fragment2);
                fragmentTransaction2.commit(); // save the changes

                break;

        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.daftarharga:
                Toast.makeText(getApplicationContext(), "Daftar Harga", Toast.LENGTH_SHORT).show();
                drawer_layout.closeDrawers();
                break;
        }
        return false;
    }

    public void LinDaftarHarga(View view) {
        drawer_layout.closeDrawers();

    }

    public void LinKeluar(View view) {


        AlertDialog.Builder alertdialog = new AlertDialog.Builder(drawer_activity.this);
        alertdialog.setTitle("Keluar");
        alertdialog.setMessage("Apakah anda yakin ingin keluar ?");
        alertdialog.setPositiveButton("yes", (dialog, which) -> {
            Preference.getSharedPreference(getApplicationContext());
            Preference.setkredentials(getApplicationContext(), "");
            Preference.setPIN(getApplicationContext(), "");
            Preference.setToken(getApplicationContext(), "");
            finish();
        });

        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });


        AlertDialog alertDialog = alertdialog.create();
        alertDialog.show();

    }

    public void getContentProfil() {

        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(Call<ResponProfil> call, Response<ResponProfil> response) {
                navheadernamakonter.setText(response.body().getData().getStore_name());
//                Toast.makeText(getApplicationContext(),response.body().getMenu().get(0).getName(),Toast.LENGTH_SHORT).show();
                myViewModel.sendPayLater(response.body().getData().getPaylater_status());
                myViewModel.sendSaldoku(response.body().getData().getWallet().getSaldoku());
                myViewModel.sendPayyLetter(response.body().getData().getWallet().getPaylatter());

                mSubMenus = (ArrayList<MSubMenu>) response.body().getData().getMenu();
                adapterSubMenuSide = new AdapterSubMenuSide(getApplicationContext(), mSubMenus);
                submenu.setAdapter(adapterSubMenuSide);

            }

            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {

            }
        });
    }

    public ArrayList<Micon> getMicons() {
        Api api = RetroClient.getApiServices();
        Call<ResponMenu> call = api.getAllProduct("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponMenu>() {
            @Override
            public void onResponse(Call<ResponMenu> call, Response<ResponMenu> response) {
                String code = response.body().getCode();


                if (code.equals("200")) {

                    micons = (ArrayList<Micon>) response.body().getData();
                    String jumlah = String.valueOf(micons.size());


                    myViewModel.sendDataIcon(micons);


                }
            }

            @Override
            public void onFailure(Call<ResponMenu> call, Throwable t) {

            }
        });

        return micons;
    }

    public ArrayList<MBanner> getIconBanner() {

        Api api = RetroClient.getApiServices();
        Call<ResponBanner> call = api.getBanner("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponBanner>() {
            @Override
            public void onResponse(Call<ResponBanner> call, Response<ResponBanner> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {

                    mBanners = response.body().getData();
                    String jumlah = String.valueOf(mBanners.size());
                    myViewModel.sendDataIconBanner(mBanners);

                }

            }

            @Override
            public void onFailure(Call<ResponBanner> call, Throwable t) {

            }
        });
        return mBanners;

    }


}