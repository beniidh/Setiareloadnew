package com.c.dompetabata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.c.dompetabata.Adapter.AdapterMenuUtama;
import com.c.dompetabata.Adapter.AdapterMenuUtamaLain;
import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Model.ModelMenuUtama;
import com.c.dompetabata.Respon.ResponMenuUtama;
import com.c.dompetabata.sharePreference.Preference;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class homelainnya extends AppCompatActivity {

    AdapterMenuUtamaLain adapterMenuUtamalain;
    RecyclerView reymenulain;
    ArrayList<ModelMenuUtama> menuUtamas = new ArrayList<>();
    ShimmerFrameLayout shimmerFrameLayout;
    SwipeRefreshLayout swipelainnya;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homelainnya);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Lainnya <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        getAllmenu();
        reymenulain = findViewById(R.id.reyLainnya);

        int numberOfColumns = 6;
        reymenulain.setLayoutManager(new GridLayoutManager(getApplicationContext(), numberOfColumns, GridLayoutManager.VERTICAL, false));
        adapterMenuUtamalain = new AdapterMenuUtamaLain(getApplicationContext(), menuUtamas);
//        reymenulain.setAdapter(adapterMenuUtama);

        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout);
        shimmerFrameLayout.startShimmerAnimation();

        swipelainnya = findViewById(R.id.swipelainnya);
        swipelainnya.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllmenu();
                swipelainnya.setRefreshing(false);
                Toast.makeText(getApplicationContext(),"Refresh",Toast.LENGTH_LONG).show();
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

    public void getAllmenu() {

        Api api = RetroClient.getApiServices();
        Call<ResponMenuUtama> call = api.getAllMenu2("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponMenuUtama>() {
            @Override
            public void onResponse(Call<ResponMenuUtama> call, Response<ResponMenuUtama> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {


                    menuUtamas = (ArrayList<ModelMenuUtama>) response.body().getData();
                    adapterMenuUtamalain = new AdapterMenuUtamaLain(getApplicationContext(), menuUtamas);
                    reymenulain.setAdapter(adapterMenuUtamalain);
                    shimmerFrameLayout.stopShimmerAnimation();
                    shimmerFrameLayout.setVisibility(View.GONE);


                }

            }

            @Override
            public void onFailure(Call<ResponMenuUtama> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });

    }
}