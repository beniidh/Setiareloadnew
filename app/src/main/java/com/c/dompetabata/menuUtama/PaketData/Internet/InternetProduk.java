package com.c.dompetabata.menuUtama.PaketData.Internet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.R;
import com.c.dompetabata.menuUtama.PaketData.air.AdapterProdukAir;
import com.c.dompetabata.menuUtama.PaketData.air.ModalAir;
import com.c.dompetabata.menuUtama.PaketData.air.ResponProdukAir;
import com.c.dompetabata.menuUtama.PaketData.air.produkair;
import com.c.dompetabata.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InternetProduk extends AppCompatActivity implements ModalInternet.BottomSheetListenerProduksms {
    EditText inputprodukinternet, inputnomorinternet;
    TextView tujukarakterinternet;
    AdapterProdukInternet adapterProdukInternet;
    ArrayList<ResponProdukInternet.VoucherData> mData = new ArrayList<>();
    RecyclerView recyclerView;
    String type = "PASCABAYAR";
    String idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_produk);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Internet <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        inputnomorinternet = findViewById(R.id.inputnomorinternet);
        inputprodukinternet = findViewById(R.id.inputprodukinternet);
        inputprodukinternet.setFocusable(false);
        tujukarakterinternet = findViewById(R.id.tujukarakterinternet);
        recyclerView = findViewById(R.id.ReyProdukInternet);


        adapterProdukInternet = new AdapterProdukInternet(getApplicationContext(), mData, inputnomorinternet.getText().toString(), type);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterProdukInternet);

        inputprodukinternet.setOnClickListener(v -> {
            String id = getIntent().getStringExtra("id");

            Bundle bundle = new Bundle();
            ModalInternet modalInternet = new ModalInternet();
            bundle.putString("id", id);
            modalInternet.setArguments(bundle);
            modalInternet.show(getSupportFragmentManager(), "Modal Internet");

        });

        inputnomorinternet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (inputnomorinternet.length() >= 7) {
                    tujukarakterinternet.setVisibility(View.INVISIBLE);
                } else {
                    tujukarakterinternet.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                getProdukk(getIdd(),inputnomorinternet.getText().toString());

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

    @Override
    public void onButtonClick(String name, String id) {
        inputprodukinternet.setText(name);
        getProdukk(id,inputnomorinternet.getText().toString());
        setIdd(id);
    }

    public void getProdukk(String id, String nomor) {
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukInternet> call = api.getProdukInternetsub(token, id);
        call.enqueue(new Callback<ResponProdukInternet>() {
            @Override
            public void onResponse(Call<ResponProdukInternet> call, Response<ResponProdukInternet> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {

                    mData = response.body().getData();
                    adapterProdukInternet = new AdapterProdukInternet(getApplicationContext(), mData, nomor, type);
                    recyclerView.setAdapter(adapterProdukInternet);

                } else {

                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponProdukInternet> call, Throwable t) {

            }
        });


    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }
}