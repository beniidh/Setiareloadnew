package com.c.latansa.konter;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import com.c.latansa.Api.Api;
import com.c.latansa.Helper.RetroClient;
import com.c.latansa.R;
import com.c.latansa.konter.KirimSaldo.TransferSaldo;
import com.c.latansa.konter.MarkupKonter.MarkupKonter;
import com.c.latansa.sharePreference.Preference;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class konter_activity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Mkonter.mData> data = new ArrayList<>();
    AdapterKonter adapterKonter;
    FloatingActionButton floatButtonAddKonter;
    SearchView Search_konter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konter);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Konter <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getKonter();

        Search_konter = findViewById(R.id.Search_konter);
        recyclerView = findViewById(R.id.ReyListKonter);
        floatButtonAddKonter = findViewById(R.id.floatButtonAddKonter);
        floatButtonAddKonter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(konter_activity.this,tambahKonter.class);
                startActivity(intent);
            }
        });

        adapterKonter = new AdapterKonter(getApplicationContext(), data,konter_activity.this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterKonter);

        Search_konter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search_konter.onActionViewExpanded();
            }
        });

        Search_konter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapterKonter.getFilter().filter(newText);
                return false;
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        getKonter();
    }

    public void getKonter(){

        String token = "Bearer "+ Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<Mkonter> call = api.getKonterSales(token);
        call.enqueue(new Callback<Mkonter>() {
            @Override
            public void onResponse(Call<Mkonter> call, Response<Mkonter> response) {
                String code = response.body().getCode();
                if(code.equals("200")){

                    data = response.body().getData();
                    if(data == null){
                        Toast.makeText(getApplicationContext(),"Data tidak ditemukan",Toast.LENGTH_SHORT).show();
                    } else{
                        adapterKonter = new AdapterKonter(getApplicationContext(), data,konter_activity.this);
                        recyclerView.setAdapter(adapterKonter);

                    }

                }
            }

            @Override
            public void onFailure(Call<Mkonter> call, Throwable t) {

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

    public void TunjukkanKeberadaanPopupMenu(View view,String id,String nama) {
        PopupMenu pop = new PopupMenu(this, view);
        pop.inflate(R.menu.menukonter);
        String idd = id;
        pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.kirimsaldo:
                        /*menangani saat Share di klik*/
                        Intent intent = new Intent(konter_activity.this, TransferSaldo.class);
                        intent.putExtra("id",idd);
                        intent.putExtra("namakonter",nama);
                        startActivity(intent);
                        break;

                    case R.id.markup:
                        /*menangani saat Feedback di klik*/
                        Intent intent2 = new Intent(konter_activity.this, MarkupKonter.class);
                        intent2.putExtra("namakonter",nama);
                        intent2.putExtra("id",idd);
                        startActivity(intent2);
                        break;

                }
                return true;
            }
        });
        pop.show();
    }
}