package com.c.dompetabata.SaldoServer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.PengajuanLimit.ResponPengajuan;
import com.c.dompetabata.R;
import com.c.dompetabata.Respon.ResponMenuUtama;
import com.c.dompetabata.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AjukanLimit extends AppCompatActivity {

    Button idAjukanLimitServerButton;
    EditText idPengajuanLimitServerEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajukan_limit);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Pengajuan Limit <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        idAjukanLimitServerButton = findViewById(R.id.idAjukanLimitServerButton);
        idPengajuanLimitServerEditText = findViewById(R.id.idPengajuanLimitServerEditText);
        getStatusPayLatter();

        idAjukanLimitServerButton.setOnClickListener(v -> {

            if (idPengajuanLimitServerEditText.getText().toString().equals("")) {

                StyleableToast.makeText(getApplicationContext(), "Limit tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast2).show();


            } else {

                Bundle bundle = new Bundle();
                bundle.putString("saldo", idPengajuanLimitServerEditText.getText().toString());
                ModalPinPengajuanServer modalPinPengajuanServer = new ModalPinPengajuanServer(AjukanLimit.this);
                modalPinPengajuanServer.setArguments(bundle);
                modalPinPengajuanServer.show(getSupportFragmentManager(), "Pin Server");
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

    public void openDialog() {
        PopupPengajuan popupPengajuan = new PopupPengajuan();
        popupPengajuan.show(getSupportFragmentManager(), "PopUp dialog");
    }

    public void getStatusPayLatter() {
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<Responn> call = api.GetPayLetter(token);
        call.enqueue(new Callback<Responn>() {
            @Override
            public void onResponse(Call<Responn> call, Response<Responn> response) {

                String code = response.body().getCode();
                if (code.equals("200")) {


                    String status = response.body().getData().get(0).getStatus();

                    if(status.equals("PENDING")){

                        idAjukanLimitServerButton.setText("Menunggu Persetujuan");
                        idAjukanLimitServerButton.setEnabled(false);
                        openDialog();
                    }else {


                    }

                } else {


                }
            }

            @Override
            public void onFailure(Call<Responn> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
}