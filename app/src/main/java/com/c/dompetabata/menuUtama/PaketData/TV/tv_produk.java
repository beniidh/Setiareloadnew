package com.c.dompetabata.menuUtama.PaketData.TV;

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
import com.c.dompetabata.menuUtama.PaketData.Internet.AdapterProdukInternet;
import com.c.dompetabata.menuUtama.PaketData.Internet.ResponProdukInternet;
import com.c.dompetabata.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tv_produk extends AppCompatActivity implements ModalTV.BottomSheetListenerProduksms {
    EditText inputproduktv, inputnomortv;
    TextView tujukaraktertv;
    RecyclerView recyclerView;
    AdapterProdukTV adapterProdukTV;
    ArrayList<ResponProdukTV.VoucherData> mData = new ArrayList<>();
    String type = "PASCABAYAR";
    String idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_produk);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>TV <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        recyclerView = findViewById(R.id.ReyProdukTV);
        inputnomortv = findViewById(R.id.inputnomortv);
        inputproduktv = findViewById(R.id.inputproduktv);
        adapterProdukTV = new AdapterProdukTV(getApplicationContext(), mData, inputnomortv.getText().toString(), type);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterProdukTV);


        inputproduktv.setFocusable(false);
        inputproduktv.setOnClickListener(v -> {
            String id = getIntent().getStringExtra("id");
            Bundle bundle = new Bundle();
            ModalTV modalTV = new ModalTV();
            bundle.putString("id", id);
            modalTV.setArguments(bundle);
            modalTV.show(getSupportFragmentManager(), "modal tv");
        });

        tujukaraktertv = findViewById(R.id.tujukaraktertv);

        inputnomortv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (inputnomortv.length() >= 7) {
                    tujukaraktertv.setVisibility(View.INVISIBLE);
                } else {

                    tujukaraktertv.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                getProduk(getIdd(), inputnomortv.getText().toString());
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
        inputproduktv.setText(name);
        setIdd(id);
    }

    public void getProduk(String id, String nomor) {
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukTV> call = api.getProdukTVsub(token, id);
        call.enqueue(new Callback<ResponProdukTV>() {
            @Override
            public void onResponse(Call<ResponProdukTV> call, Response<ResponProdukTV> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {

                    mData = response.body().getData();
                    adapterProdukTV = new AdapterProdukTV(getApplicationContext(), mData, nomor, type);
                    recyclerView.setAdapter(adapterProdukTV);

                } else {

                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponProdukTV> call, Throwable t) {

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