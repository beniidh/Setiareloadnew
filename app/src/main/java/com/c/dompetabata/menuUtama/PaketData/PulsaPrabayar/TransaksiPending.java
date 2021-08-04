package com.c.dompetabata.menuUtama.PaketData.PulsaPrabayar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Helper.LoadingPrimer;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Helper.utils;
import com.c.dompetabata.R;
import com.c.dompetabata.sharePreference.Preference;
import com.google.android.gms.common.util.DynamiteApi;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiPending extends AppCompatActivity {

    ImageView expand;
    LinearLayout linearExpand;
    TextView KeteranganTP, noSN, hargaprodukTP, nomorTP, nominalTP, saldokuterpakai, tanggalDetail, waktuDetail, NomorTransaksiDetail, hargatotalDetail;
    Button tutuppending, refreshstatus;
    ImageView iconTP, iconTPP;
    LoadingPrimer loadingPrimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_pending);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Transaksi <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        expand = findViewById(R.id.expandtransaksi);
        linearExpand = findViewById(R.id.linearexpand);
        KeteranganTP = findViewById(R.id.KeteranganTP);
        hargaprodukTP = findViewById(R.id.hargaprodukTP);
        nomorTP = findViewById(R.id.nomorTP);
        nominalTP = findViewById(R.id.nominalTP);
        tutuppending = findViewById(R.id.tutuppending);
        iconTP = findViewById(R.id.iconTP);
        iconTPP = findViewById(R.id.iconTPP);
        noSN = findViewById(R.id.noSN);
        refreshstatus = findViewById(R.id.refreshstatus);

        String transaksiid = getIntent().getStringExtra("transaksid");
        loadingPrimer = new LoadingPrimer(TransaksiPending.this);

        ChekTransaksi(transaksiid);
        refreshstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChekTransaksi(transaksiid);
            }
        });

        saldokuterpakai = findViewById(R.id.saldokuterpakai);
        tanggalDetail = findViewById(R.id.tanggalDetail);
        waktuDetail = findViewById(R.id.waktuDetail);
        NomorTransaksiDetail = findViewById(R.id.NomorTransaksiDetail);
        hargatotalDetail = findViewById(R.id.hargatotalDetail);

        if (Preference.getIconUrl(getApplicationContext()).isEmpty()) {
            iconTP.setBackground(getDrawable(R.drawable.listrik));
        } else {

            Picasso.get().load(Preference.getIconUrl(getApplicationContext())).into(iconTP);
        }

        tutuppending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = getIntent().getStringExtra("code");

                if (code == null) {

                    finish();
                    KonfirmasiPembayaran.konifirmpembayaran.finish();

                } else {

                    if (code.equals("saldo")) {

                        finish();
                    }
                }


            }
        });


//        KeteranganTP.setText(getIntent().getStringExtra("pesan"));
//        hargaprodukTP.setText(utils.ConvertRP(getIntent().getStringExtra("hargatotal")));
//        nominalTP.setText(utils.ConvertRP(getIntent().getStringExtra("hargatotal")));
//        nomorTP.setText(getIntent().getStringExtra("nomorcustomer"));
        expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (linearExpand.getVisibility() == View.GONE) {

                    TransitionManager.beginDelayedTransition(linearExpand, new AutoTransition());
                    linearExpand.setVisibility(View.VISIBLE);
                    expand.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up);
                } else {

                    TransitionManager.beginDelayedTransition(linearExpand, new AutoTransition());
                    linearExpand.setVisibility(View.GONE);
                    expand.setImageResource(R.drawable.ic_baseline_expand_more_24);

                }

            }
        });

        new CountDownTimer(3000, 1000) {


            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                String code = getIntent().getStringExtra("code");
                if (code == null) {
                    ChekTransaksi(transaksiid);
                } else {

                    if (code.equals("saldo")) {

                    }

                }


            }
        }.start();

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

    public void ChekTransaksi(String id) {
        loadingPrimer.startDialogLoading();

        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<Mchek> call = api.CekTransaksi(token, id);
        call.enqueue(new Callback<Mchek>() {
            @Override
            public void onResponse(Call<Mchek> call, Response<Mchek> response) {
                if (response.body().getCode().equals("200")) {
                    saldokuterpakai.setText(utils.ConvertRP(response.body().getData().getTotal_price()));

                    String status = response.body().getData().getStatus();
                    if (status.equals("SUKSES")) {
                        KeteranganTP.setTextColor(getColor(R.color.green));
                        iconTPP.setBackground(getDrawable(R.drawable.check));

                    } else if (status.equals("GAGAL")) {
                        KeteranganTP.setTextColor(getColor(R.color.red));
                        iconTPP.setBackground(getDrawable(R.drawable.failed));
                    }

                    KeteranganTP.setText(response.body().getData().getStatus());
                    hargaprodukTP.setText(utils.ConvertRP(response.body().getData().getTotal_price()));
                    nomorTP.setText(response.body().getData().getCustomer_no());
                    nominalTP.setText(utils.ConvertRP(response.body().getData().getTotal_price()));
                    String create = response.body().getData().getUpdated_at();
                    String tahun = create.substring(0, 4);
                    String bulan = utils.convertBulan(create.substring(5, 7));
                    String hari = create.substring(8, 10);
                    String jam = create.substring(11, 16);
                    tanggalDetail.setText(hari + " " + bulan + " " + tahun);
                    waktuDetail.setText(jam);
                    NomorTransaksiDetail.setText(response.body().getData().getId());
                    noSN.setText(response.body().getData().getSn());
                    hargatotalDetail.setText(utils.ConvertRP(response.body().getData().getTotal_price()));
                    loadingPrimer.dismissDialog();


                } else {

                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                    loadingPrimer.dismissDialog();
                }

            }

            @Override
            public void onFailure(Call<Mchek> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                loadingPrimer.dismissDialog();
            }
        });


    }
}