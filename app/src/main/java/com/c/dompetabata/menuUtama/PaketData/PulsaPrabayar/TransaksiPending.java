package com.c.dompetabata.menuUtama.PaketData.PulsaPrabayar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
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
import com.c.dompetabata.CetakStruk.DetailTransaksiTruk;
import com.c.dompetabata.CetakStruk.cetak;
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
    TextView KeteranganTP,produkTransaksi, noSN, hargaprodukTP, nomorTP, nominalTP, saldokuterpakai, tanggalDetail, waktuDetail, NomorTransaksiDetail, hargatotalDetail;
    Button tutuppending, cetakStruk;
    ImageView iconTP, iconTPP;
    LoadingPrimer loadingPrimer;
    SwipeRefreshLayout swipeTransaksi;
    String harga;

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
        produkTransaksi = findViewById(R.id.produkTransaksi);
        iconTPP = findViewById(R.id.iconTPP);
        noSN = findViewById(R.id.noSN);
        cetakStruk = findViewById(R.id.refreshstatus);

        swipeTransaksi = findViewById(R.id.swipeTransaksi);
        String transaksiid = getIntent().getStringExtra("transaksid");
        loadingPrimer = new LoadingPrimer(TransaksiPending.this);
        swipeTransaksi.setOnRefreshListener(() -> {
            ChekTransaksi(transaksiid);
            swipeTransaksi.setRefreshing(false);
        });

        ChekTransaksi(transaksiid);
        cetakStruk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(TransaksiPending.this, DetailTransaksiTruk.class);
                intent.putExtra("nomor", nomorTP.getText().toString());
                intent.putExtra("produk", produkTransaksi.getText().toString());
                intent.putExtra("harga", getHarga());
                intent.putExtra("tanggal", tanggalDetail.getText().toString());
                intent.putExtra("waktu", waktuDetail.getText().toString());
                intent.putExtra("sn", noSN.getText().toString());
                intent.putExtra("transaksid", NomorTransaksiDetail.getText().toString());
                startActivity(intent);

            }
        });

        saldokuterpakai = findViewById(R.id.saldokuterpakai);
        tanggalDetail = findViewById(R.id.tanggalDetail);
        waktuDetail = findViewById(R.id.waktuDetail);
        NomorTransaksiDetail = findViewById(R.id.NomorTransaksiDetail);
        hargatotalDetail = findViewById(R.id.hargatotalDetail);

        if (Preference.getIconUrl(getApplicationContext()).isEmpty()) {
            iconTP.setBackground(getDrawable(R.drawable.logoapkcsoft));
        } else {

            Picasso.get().load(Preference.getIconUrl(getApplicationContext())).into(iconTP);
        }

        tutuppending.setOnClickListener(v -> {
            String code = getIntent().getStringExtra("code");

            if (code == null) {

                finish();
                KonfirmasiPembayaran.konifirmpembayaran.finish();

            } else {

                if (code.equals("saldo")) {

                    finish();
                }
            }


        });


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
                        cetakStruk.setVisibility(View.VISIBLE);
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
                    produkTransaksi.setText(response.body().getData().getProduct_name());
                    NomorTransaksiDetail.setText(response.body().getData().getId());
                    noSN.setText(response.body().getData().getSn());
                    hargatotalDetail.setText(utils.ConvertRP(response.body().getData().getTotal_price()));
                    setHarga(response.body().getData().getTotal_price());
                    loadingPrimer.dismissDialog();


                } else if(response.body().getCode().equals("401")){
                    Toast.makeText(getApplicationContext(),"Token telah berakhir,silahkan login ulang",Toast.LENGTH_LONG).show();

                    loadingPrimer.dismissDialog();
                }else {
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

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }
}