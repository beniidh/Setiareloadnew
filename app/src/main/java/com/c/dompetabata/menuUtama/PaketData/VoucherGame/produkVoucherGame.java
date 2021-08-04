package com.c.dompetabata.menuUtama.PaketData.VoucherGame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.c.dompetabata.menuUtama.PaketData.PulsaPrabayar.AdapterPulsaPrabayar;
import com.c.dompetabata.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class produkVoucherGame extends AppCompatActivity implements ModalVoucherGame.BottomSheetListenerVoucherGame {

    EditText inputprodukvouchergame, inputnomorvouchergame;
    TextView tujukaraktervouchergame;
    AdapterProdukVoucher adapterProdukVoucher;
    ArrayList<ResponProdukVoucher.VoucherData> mVoucherGame = new ArrayList<>();
    RecyclerView recyclerView;
    String id;
    String type = "PRABAYAR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_voucher_game);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Voucher Game <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        inputprodukvouchergame = findViewById(R.id.inputprodukvouchergame);
        inputprodukvouchergame.setFocusable(false);
        inputnomorvouchergame = findViewById(R.id.inputnomorvouchergame);
        tujukaraktervouchergame = findViewById(R.id.tujukaraktervouchergame);
        recyclerView = findViewById(R.id.ReyVoucherGame);

        adapterProdukVoucher = new AdapterProdukVoucher(getApplicationContext(), mVoucherGame, inputnomorvouchergame.getText().toString(), type);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterProdukVoucher);

        inputprodukvouchergame.setOnClickListener(v -> {

            Intent intent = getIntent();
            String id = intent.getStringExtra("id");

            ModalVoucherGame modalVoucherGame = new ModalVoucherGame(id);
            modalVoucherGame.show(getSupportFragmentManager(), "Modal Voucher");

        });

        inputnomorvouchergame.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (inputnomorvouchergame.length() >= 7) {
                    tujukaraktervouchergame.setVisibility(View.INVISIBLE);
                } else {
                    tujukaraktervouchergame.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                getVoucherGameProduk(getId(),inputnomorvouchergame.getText().toString());

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
    public void onButtonClickKabupaten(String name, String id) {
        setId(id);
        getVoucherGameProduk(id,inputnomorvouchergame.getText().toString());
        inputprodukvouchergame.setText(name);
    }

    public void getVoucherGameProduk(String id,String nomor) {

        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukVoucher> call = api.getProdukVG(token, id);
        call.enqueue(new Callback<ResponProdukVoucher>() {
            @Override
            public void onResponse(Call<ResponProdukVoucher> call, Response<ResponProdukVoucher> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    mVoucherGame = response.body().getData();
                    adapterProdukVoucher = new AdapterProdukVoucher(getApplicationContext(), mVoucherGame, nomor, type);
                    recyclerView.setAdapter(adapterProdukVoucher);

                } else {

                    ArrayList<ResponProdukVoucher.VoucherData> mVoucherGamee = new ArrayList<>();
                    mVoucherGame = mVoucherGamee;
                    adapterProdukVoucher = new AdapterProdukVoucher(getApplicationContext(), mVoucherGame, inputnomorvouchergame.getText().toString(), type);
                    recyclerView.setAdapter(adapterProdukVoucher);

                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponProdukVoucher> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}