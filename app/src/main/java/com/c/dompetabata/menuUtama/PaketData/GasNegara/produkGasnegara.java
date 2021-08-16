package com.c.dompetabata.menuUtama.PaketData.GasNegara;

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
import com.c.dompetabata.menuUtama.PaketData.PajakPBB.AdapterProdukPBB;
import com.c.dompetabata.menuUtama.PaketData.PajakPBB.ModalPajak;
import com.c.dompetabata.menuUtama.PaketData.PajakPBB.ResponProdukPBB;
import com.c.dompetabata.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class produkGasnegara extends AppCompatActivity implements ModalGasnegara.BottomSheetListenerProduksms {

    EditText inputprodukgasnegara,inputnomorgasnegara;
    TextView tujukaraktergasnegara;
    AdapterProdukGasnegara adapterProdukGasnegara;
    ArrayList<ResponProdukGasnegara.VoucherData> mData = new ArrayList<>();
    RecyclerView recyclerView;
    String type = "PASCABAYAR";
    String idd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_gasnegara);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Gas Negara <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        inputprodukgasnegara = findViewById(R.id.inputprodukgasnegara);
        inputprodukgasnegara.setFocusable(false);
        inputnomorgasnegara = findViewById(R.id.inputnomorgasnegara);
        tujukaraktergasnegara = findViewById(R.id.tujukaraktergasnegara);
        recyclerView = findViewById(R.id.ReyProdukGasnegara);

        adapterProdukGasnegara = new AdapterProdukGasnegara(getApplicationContext(), mData, inputnomorgasnegara.getText().toString(), type);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterProdukGasnegara);

        inputprodukgasnegara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = getIntent().getStringExtra("id");
                Bundle bundle = new Bundle();
                ModalGasnegara modalgas = new ModalGasnegara();
                bundle.putString("id", id);
                modalgas.setArguments(bundle);
                modalgas.show(getSupportFragmentManager(), "Modal pajak");

            }
        });

        inputnomorgasnegara.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (inputnomorgasnegara.length() >= 7) {

                    tujukaraktergasnegara.setVisibility(View.INVISIBLE);
                } else {

                    tujukaraktergasnegara.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                getProduk(getIdd(),inputnomorgasnegara.getText().toString());

            }
        });


    }

    public void getProduk(String id, String nomor) {
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukGasnegara> call = api.getProdukGasSub(token, id);
        call.enqueue(new Callback<ResponProdukGasnegara>() {
            @Override
            public void onResponse(Call<ResponProdukGasnegara> call, Response<ResponProdukGasnegara> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {

                    mData = response.body().getData();
                    adapterProdukGasnegara = new AdapterProdukGasnegara(getApplicationContext(), mData, nomor, type);
                    recyclerView.setAdapter(adapterProdukGasnegara);

                } else {

                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponProdukGasnegara> call, Throwable t) {

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
        getProduk(id,inputnomorgasnegara.getText().toString());
        inputprodukgasnegara.setText(name);
        setIdd(id);

    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }
}