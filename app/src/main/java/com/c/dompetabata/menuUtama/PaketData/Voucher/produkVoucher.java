package com.c.dompetabata.menuUtama.PaketData.Voucher;

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

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.R;
import com.c.dompetabata.menuUtama.PaketData.PaketsmsTelpon.AdapterProdukST;
import com.c.dompetabata.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class produkVoucher extends AppCompatActivity implements ModalVoucher.BottomSheetListenerProduksms {

    EditText inputprodukvoucher,inputnomorvoucher;
    TextView tujukaraktervoucher;
    RecyclerView recyclerView;
    AdapterProdukVoucher adapterProdukVoucher;
    ArrayList<ResponProdukVoucherv.VoucherData> mData = new ArrayList<>();
    String url,idd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_voucher);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Voucher <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        inputnomorvoucher = findViewById(R.id.inputnomorvoucher);
        inputprodukvoucher = findViewById(R.id.inputprodukvoucher);
        inputprodukvoucher.setFocusable(false);
        tujukaraktervoucher = findViewById(R.id.tujukaraktervoucher);
        recyclerView = findViewById(R.id.ReyProdukVoucher);

        adapterProdukVoucher = new AdapterProdukVoucher(getApplicationContext(), mData,inputnomorvoucher.getText().toString(),getUrl());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterProdukVoucher);

        inputprodukvoucher.setOnClickListener(v -> {

            String id = getIntent().getStringExtra("id");
            Bundle bundle = new Bundle();
            ModalVoucher modalVoucher = new ModalVoucher();
            bundle.putString("id",id);
            modalVoucher.setArguments(bundle);
            modalVoucher.show(getSupportFragmentManager(),"Modal Voucher");
        });

        inputnomorvoucher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if(inputnomorvoucher.length() >= 7){
                    tujukaraktervoucher.setVisibility(View.INVISIBLE);
                } else {

                    tujukaraktervoucher.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getProduk(getIdd(),inputnomorvoucher.getText().toString());

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
    public void onButtonClick(String name, String id,String icon) {
        inputprodukvoucher.setText(name);
        setIdd(id);
        setUrl(icon);

    }

    public void getProduk(String id,String nomor){

        String token = "Bearer "+ Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukVoucherv> call = api.getProdukVoucherSub(token,id);
        call.enqueue(new Callback<ResponProdukVoucherv>() {
            @Override
            public void onResponse(Call<ResponProdukVoucherv> call, Response<ResponProdukVoucherv> response) {

                String code = response.body().getCode();
                if (code.equals("200")){
                    mData = response.body().getData();
                    adapterProdukVoucher = new AdapterProdukVoucher(getApplicationContext(),mData,nomor,getUrl());
                    recyclerView.setAdapter(adapterProdukVoucher);

                }
            }

            @Override
            public void onFailure(Call<ResponProdukVoucherv> call, Throwable t) {

            }
        });


    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }
}