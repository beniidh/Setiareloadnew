package com.c.setiareload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.setiareload.Adapter.AdapterSubMenuSide;
import com.c.setiareload.Api.Api;
import com.c.setiareload.Fragment.ChatFragment;
import com.c.setiareload.Fragment.HomeFragment;
import com.c.setiareload.Fragment.HomeViewModel;
import com.c.setiareload.Fragment.TransaksiFragment;
import com.c.setiareload.Model.MSubMenu;
import com.c.setiareload.Notifikasi.Notifikasi_Activity;
import com.c.setiareload.Respon.ResponBanner;
import com.c.setiareload.Respon.ResponMenu;
import com.c.setiareload.Respon.ResponProfil;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Model.MBanner;
import com.c.setiareload.Model.Micon;
import com.c.setiareload.Profil.Profil;
import com.c.setiareload.sharePreference.Preference;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.muddzdev.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.nikartm.support.BadgePosition;
import ru.nikartm.support.ImageBadgeView;

public class drawer_activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    BottomNavigationView menu_bawah;
    Toolbar toolbar;
    TextView tulisan, navheadernamakonter, nbhome, nbtransaksi, nbchat, nbhomet, nbtransaksit, nbchatt;
    DrawerLayout drawer_layout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    String idServer;
    Fragment fragment1, fragment2, fragment3;

    LinearLayout profil;
    ArrayList<Micon> micons = new ArrayList<>();
    ArrayList<MBanner> mBanners = new ArrayList<>();
    HomeViewModel myViewModel;

    AdapterSubMenuSide adapterSubMenuSide;
    ArrayList<MSubMenu> mSubMenus = new ArrayList<>();
    RecyclerView submenu;
    ImageView iconprofilsidebar;
    ImageBadgeView notifikasi;
    TextView parent;

    @SuppressLint("StaticFieldLeak")
    static Activity drawwer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_activity);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        drawwer = this;
        iconprofilsidebar = findViewById(R.id.iconprofilsidebar);
        parent = findViewById(R.id.parent);
        Typeface type2 = ResourcesCompat.getFont(getApplicationContext(), R.font.abata);
        nbhome = findViewById(R.id.NBhome);
        nbhomet = findViewById(R.id.NBhomeT);
        nbhome.setTypeface(type2);

        nbtransaksi = findViewById(R.id.NBTransaksi);
        nbtransaksit = findViewById(R.id.NBTransaksiT);
        nbtransaksi.setTypeface(type2);
        nbchat = findViewById(R.id.NBchat);
        nbchatt = findViewById(R.id.NBchatT);
        nbchat.setTypeface(type2);
        notifikasi = findViewById(R.id.notifikasiID);


        notifikasi.setBadgeValue(Preference.getNilaiNotif(getApplicationContext()))
                .setBadgeTextSize(9)
                .setMaxBadgeValue(999)
                .setBadgeBackground(getResources().getDrawable(R.drawable.rectangle_rounded))
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);

        notifikasi.setOnClickListener(v -> {

            Intent intent = new Intent(drawer_activity.this, Notifikasi_Activity.class);
            startActivity(intent);
            notifikasi.setBadgeValue(0);

        });

        ImageView togglenav = findViewById(R.id.togglenavheader);

        getContentProfil();


        submenu = findViewById(R.id.ReySubMenu);
        adapterSubMenuSide = new AdapterSubMenuSide(getApplicationContext(), mSubMenus, drawer_activity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        submenu.setLayoutManager(mLayoutManager);
        submenu.setAdapter(adapterSubMenuSide);


        menu_bawah = findViewById(R.id.menu_bawah);
        tulisan = findViewById(R.id.tulisan);
        navheadernamakonter = findViewById(R.id.navheadernamakonter);
        getMicons();


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


        nbhome.setTextColor(getColor(R.color.green));
        nbhomet.setTextColor(getColor(R.color.green));

        nbchat.setOnClickListener(v -> {

            FragmentManager fm3 = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction2 = fm3.beginTransaction();
            fragmentTransaction2.replace(R.id.fLayout, fragment2);
            fragmentTransaction2.commit(); // save the changes
            nbchat.setTextColor(getColor(R.color.green));
            nbchatt.setTextColor(getColor(R.color.green));
            nbtransaksi.setTextColor(getColor(R.color.gray3));
            nbtransaksit.setTextColor(getColor(R.color.gray3));
            nbhome.setTextColor(getColor(R.color.gray3));
            nbhomet.setTextColor(getColor(R.color.gray3));

        });

        nbhome.setOnClickListener(v -> {
            FragmentManager fmm = getSupportFragmentManager();
            FragmentTransaction fragmentTransactionn = fmm.beginTransaction();
            fragmentTransactionn.replace(R.id.fLayout, fragment3);
            fragmentTransactionn.commit(); // save the changes
            nbchat.setTextColor(getColor(R.color.gray3));
            nbchatt.setTextColor(getColor(R.color.gray3));
            nbtransaksi.setTextColor(getColor(R.color.gray3));
            nbtransaksit.setTextColor(getColor(R.color.gray3));
            nbhome.setTextColor(getColor(R.color.green));
            nbhomet.setTextColor(getColor(R.color.green));


        });

        nbtransaksi.setOnClickListener(v -> {
            FragmentManager fm2 = getSupportFragmentManager();
            FragmentTransaction fragmentTransactionnn = fm2.beginTransaction();
            fragmentTransactionnn.replace(R.id.fLayout, fragment1);
            fragmentTransactionnn.commit(); // save the changes
            nbchat.setTextColor(getColor(R.color.gray3));
            nbchatt.setTextColor(getColor(R.color.gray3));
            nbtransaksi.setTextColor(getColor(R.color.green));
            nbtransaksit.setTextColor(getColor(R.color.green));
            nbhome.setTextColor(getColor(R.color.gray3));
            nbhomet.setTextColor(getColor(R.color.gray3));

        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        LocalBroadcastManager.getInstance(this).registerReceiver((broadcastReceiver),
                new IntentFilter("kirim"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            notifikasi.setBadgeValue(1);

        }
    };


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

    public void LinDaftarHarga() {
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

        alertdialog.setNegativeButton("No", (dialog, which) -> {

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
                myViewModel.sendPayLater(response.body().getData().getPaylater_status());
                myViewModel.sendSaldoku(response.body().getData().getWallet().getSaldoku());
                myViewModel.sendPayyLetter(response.body().getData().getWallet().getPaylatter());
                Picasso.get().load(response.body().getData().getAvatar()).into(iconprofilsidebar);
                parent.setText(response.body().getData().getReferal_code());
                getIconBanner(response.body().getData().getServer_id());
                mSubMenus = (ArrayList<MSubMenu>) response.body().getData().getMenu();
                adapterSubMenuSide = new AdapterSubMenuSide(getApplicationContext(), mSubMenus, drawer_activity.this);
                submenu.setAdapter(adapterSubMenuSide);

            }

            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
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
                    myViewModel.sendDataIcon(micons);


                }
            }

            @Override
            public void onFailure(Call<ResponMenu> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });

        return micons;
    }

    public ArrayList<MBanner> getIconBanner(String id) {

        Api api = RetroClient.getApiServices();
        Call<ResponBanner> call = api.getBanner("Bearer " + Preference.getToken(getApplicationContext()), id);
        call.enqueue(new Callback<ResponBanner>() {
            @Override
            public void onResponse(@NonNull Call<ResponBanner> call, Response<ResponBanner> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {

                    mBanners = response.body().getData();
                    myViewModel.sendDataIconBanner(mBanners);

                }

            }

            @Override
            public void onFailure(Call<ResponBanner> call, @NonNull Throwable t) {
//                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
        return mBanners;

    }

    @Override
    protected void onResume() {
        super.onResume();
        getContentProfil();


        notifikasi.setBadgeValue(Preference.getNilaiNotif(getApplicationContext()))
                .setBadgeTextSize(9)
                .setMaxBadgeValue(999)
                .setBadgeBackground(getResources().getDrawable(R.drawable.rectangle_rounded))
                .setBadgePosition(BadgePosition.TOP_RIGHT)
                .setBadgeTextStyle(Typeface.NORMAL)
                .setShowCounter(true)
                .setBadgePadding(4);


    }

    public String getIdServer() {
        return idServer;
    }

    public void setIdServer(String idServer) {
        this.idServer = idServer;
    }
}