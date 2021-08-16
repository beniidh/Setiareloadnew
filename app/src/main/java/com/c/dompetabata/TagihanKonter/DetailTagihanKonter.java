package com.c.dompetabata.TagihanKonter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.text.Html;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Helper.utils;
import com.c.dompetabata.R;
import com.c.dompetabata.Respon.ResponProfil;
import com.c.dompetabata.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailTagihanKonter extends AppCompatActivity {

    TextView DetailTagihanKonterNominal, DetailTagihanKonterKeterangan;
    Button ButtonSudahBayar,ButtonTolak;
    static Activity detailtagihan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tagihan_konter);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Tagihan Konter <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        detailtagihan = this;

        DetailTagihanKonterNominal = findViewById(R.id.DetailTagihanKonterNominal);
        DetailTagihanKonterKeterangan = findViewById(R.id.DetailTagihanKonterKeterangan);
        ButtonSudahBayar = findViewById(R.id.ButtonSudahBayar);
        ButtonTolak = findViewById(R.id.ButtonTolak);

        String tanggal = getIntent().getStringExtra("tanggaltagihan");
        String namakonter = getIntent().getStringExtra("namakonter");
        String idtagihan = getIntent().getStringExtra("idtagihan");
        String tagihan = getIntent().getStringExtra("tagihan");
        DetailTagihanKonterNominal.setText(utils.ConvertRP(tagihan));

        DetailTagihanKonterKeterangan.setText("Tagihan konter " + Html.fromHtml(bold(namakonter)) + " jatuh tempo pada tanggal " + tanggal);

        ButtonSudahBayar.setOnClickListener(v -> {

            ModalPinTagihanKonter modalPinTagihanKonter = new ModalPinTagihanKonter();
            Bundle bundle = new Bundle();
            bundle.putString("idtagihan", idtagihan);
            bundle.putString("aksi","APPROVED");
            modalPinTagihanKonter.setArguments(bundle);
            modalPinTagihanKonter.show(getSupportFragmentManager(), "Tagihan");
        });

        ButtonTolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModalPinTagihanKonter modalPinTagihanKonter = new ModalPinTagihanKonter();
                Bundle bundle = new Bundle();
                bundle.putString("idtagihan", idtagihan);
                bundle.putString("aksi","REJECT");
                modalPinTagihanKonter.setArguments(bundle);
                modalPinTagihanKonter.show(getSupportFragmentManager(), "Tagihan");

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

    public static String bold(String text) {
        return new StringBuffer().append("<b>").append(text).append("</b>").toString();
    }

    public void getContentProfil() {

        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(Call<ResponProfil> call, Response<ResponProfil> response) {



            }

            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }

}