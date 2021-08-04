package com.c.dompetabata.menuUtama.PaketData.PajakPBB;

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
import com.c.dompetabata.menuUtama.PaketData.TV.AdapterProdukTV;
import com.c.dompetabata.menuUtama.PaketData.TV.ResponProdukTV;
import com.c.dompetabata.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class produkPajakPBB extends AppCompatActivity implements ModalPajak.BottomSheetListenerProduksms {

    EditText inputprodukpajak, inputnomorpajak;
    TextView tujukarakterpajak;
    RecyclerView recyclerView;
    ArrayList<ResponProdukPBB.VoucherData> mData = new ArrayList<>();
    AdapterProdukPBB adapterProdukPBB;
    String type = "PASCABAYAR";
    String idd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_pajak_p_b_b);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Pajak PBB <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        inputprodukpajak = findViewById(R.id.inputprodukpajak);
        inputprodukpajak.setFocusable(false);
        inputnomorpajak = findViewById(R.id.inputnomorpajak);
        tujukarakterpajak = findViewById(R.id.tujukarakterpajak);
        recyclerView = findViewById(R.id.ReyProdukPBB);

        adapterProdukPBB = new AdapterProdukPBB(getApplicationContext(), mData, inputnomorpajak.getText().toString(), type);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterProdukPBB);

        inputprodukpajak.setOnClickListener(v -> {
            String id = getIntent().getStringExtra("id");
            Bundle bundle = new Bundle();
            ModalPajak modalPajak = new ModalPajak();
            bundle.putString("id", id);
            modalPajak.setArguments(bundle);
            modalPajak.show(getSupportFragmentManager(), "Modal pajak");
        });

        inputnomorpajak.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (inputnomorpajak.length() >= 7) {

                    tujukarakterpajak.setVisibility(View.INVISIBLE);
                } else {

                    tujukarakterpajak.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                getProduk(getIdd(), inputnomorpajak.getText().toString());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void getProduk(String id, String nomor) {
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukPBB> call = api.getProdukPBBSub(token, id);
        call.enqueue(new Callback<ResponProdukPBB>() {
            @Override
            public void onResponse(Call<ResponProdukPBB> call, Response<ResponProdukPBB> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {

                    mData = response.body().getData();
                    adapterProdukPBB = new AdapterProdukPBB(getApplicationContext(), mData, nomor, type);
                    recyclerView.setAdapter(adapterProdukPBB);

                } else {

                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponProdukPBB> call, Throwable t) {

            }
        });


    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onButtonClick(String name, String id) {
        inputprodukpajak.setText(name);
        setIdd(id);
    }
}