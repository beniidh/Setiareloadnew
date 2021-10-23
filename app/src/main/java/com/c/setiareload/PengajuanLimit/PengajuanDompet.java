package com.c.setiareload.PengajuanLimit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Helper.utils;
import com.c.setiareload.R;
import com.c.setiareload.Respon.ResponProfil;
import com.c.setiareload.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PengajuanDompet extends AppCompatActivity {

    EditText idPengajuanLimitEditText;
    Button idajukanlimitButton;
    RecyclerView idRecyclePengajuanDompet;
    AdapterPengajuanLimit adapterPengajuanLimit;
    ArrayList<MPengajuanLimit> mPengajuanLimits = new ArrayList<>();
    TextView saldoServerSaatini;
    double saldopengajuann;
    String saldoserverlimit;
    double saldokuserverlimitt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengajuan_dompet);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Pengajuan Saldo Server <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        idPengajuanLimitEditText = findViewById(R.id.idPengajuanLimitEditText);
        idajukanlimitButton = findViewById(R.id.idAjukanLimitButton);
        idRecyclePengajuanDompet = findViewById(R.id.idRecyclePengajuanDompet);
        saldoServerSaatini = findViewById(R.id.saldoServerSaatini);



        getContentProfil();
        adapterPengajuanLimit = new AdapterPengajuanLimit(getApplicationContext(), mPengajuanLimits);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        idRecyclePengajuanDompet.setLayoutManager(mLayoutManager);
        idRecyclePengajuanDompet.setAdapter(adapterPengajuanLimit);

        getPengajuanDompet();
        idPengajuanLimitEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (idPengajuanLimitEditText.length() >= 1) {
                    String saldoPengajuan = idPengajuanLimitEditText.getText().toString();
                    saldopengajuann = Double.parseDouble(saldoPengajuan);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        idajukanlimitButton.setOnClickListener(v -> {

            if (idPengajuanLimitEditText.getText().toString().isEmpty()) {
                StyleableToast.makeText(getApplicationContext(), "Jumlah Tidak Boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast2).show();

            } else {
                Bundle bundle = new Bundle();
                bundle.putString("jumlahpengajuan", idPengajuanLimitEditText.getText().toString());
                ModalPinPengajuan modalPinPengajuan = new ModalPinPengajuan(PengajuanDompet.this);
                modalPinPengajuan.setArguments(bundle);
                modalPinPengajuan.show(getSupportFragmentManager(), "modalpengajuan");

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

    public void getPengajuanDompet() {
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponPengajuan> call = api.getPengajuanDompet(token);
        call.enqueue(new Callback<ResponPengajuan>() {
            @Override
            public void onResponse(Call<ResponPengajuan> call, Response<ResponPengajuan> response) {

                String code = response.body().getCode();
                if (code.equals("200")) {

                    mPengajuanLimits = response.body().getData();
                    adapterPengajuanLimit = new AdapterPengajuanLimit(getApplicationContext(), mPengajuanLimits);
                    idRecyclePengajuanDompet.setAdapter(adapterPengajuanLimit);
                }

            }

            @Override
            public void onFailure(Call<ResponPengajuan> call, Throwable t) {

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        getPengajuanDompet();
    }

    public void getContentProfil() {

        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(Call<ResponProfil> call, Response<ResponProfil> response) {

                saldoServerSaatini.setText(utils.ConvertRP(response.body().getData().getWallet().getPaylatter()));
                saldoserverlimit = response.body().getData().getWallet().getPaylatter();
                saldokuserverlimitt = Double.valueOf(saldoserverlimit);


            }

            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }
}