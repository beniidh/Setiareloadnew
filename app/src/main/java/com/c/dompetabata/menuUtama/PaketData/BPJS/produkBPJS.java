package com.c.dompetabata.menuUtama.PaketData.BPJS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Api.Value;
import com.c.dompetabata.Helper.GpsTracker;
import com.c.dompetabata.Helper.LoadingPrimer;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Helper.utils;
import com.c.dompetabata.R;
import com.c.dompetabata.Transaksi.MInquiry;
import com.c.dompetabata.Transaksi.ResponInquiry;
import com.c.dompetabata.menuUtama.PaketData.ListrikPLNPasca.Pln_produk_pasca;
import com.c.dompetabata.menuUtama.PaketData.PulsaPrabayar.KonfirmasiPembayaran;
import com.c.dompetabata.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class produkBPJS extends AppCompatActivity {

    EditText nomorinputBpjs;
    TextView PPnomorBp,PPnamaBp,PPtarifBp,PPdayaBp,PPjumlahTagihanBp,PPTanggalBp,PPTransaksiBp,PPStatusBp;
    Button periksaBpjs;
    LinearLayout LineraBPJS;
    String skucode, inquiry;
    String hargaa;
    String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_b_p_j_s);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>BPJS <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        nomorinputBpjs   = findViewById(R.id.nomorinputBpjs);
        PPnomorBp = findViewById(R.id.PPnomorBp);
        PPnamaBp= findViewById(R.id.PPnamaBp);
        PPtarifBp= findViewById(R.id.PPtarifBp);
        PPdayaBp = findViewById(R.id.PPdayaBp);
        PPjumlahTagihanBp = findViewById(R.id.PPjumlahTagihanBp);
        PPTanggalBp= findViewById(R.id.PPTanggalBp);
        PPTransaksiBp = findViewById(R.id.PPTransaksiBp);
        PPStatusBp = findViewById(R.id.PPStatusBp);
        periksaBpjs = findViewById(R.id.periksaBpjs);
        LineraBPJS = findViewById(R.id.LineraBPJS);
        String id = getIntent().getStringExtra("id");
        getProduk(id);


        periksaBpjs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (periksaBpjs.getText().toString().equals("Periksa")) {

                if (!nomorinputBpjs.getText().toString().isEmpty()) {

                    LoadingPrimer loadingPrimer = new LoadingPrimer(produkBPJS.this);
                    loadingPrimer.startDialogLoading();

                    GpsTracker gpsTracker = new GpsTracker(getApplicationContext());

                    Api api = RetroClient.getApiServices();
                    MInquiry mInquiry = new MInquiry(getCode(), nomorinputBpjs.getText().toString(), "PASCABAYAR", Value.getMacAddress(), Value.getIPaddress(), Value.getUserAgent(getApplicationContext()), gpsTracker.getLatitude(), gpsTracker.getLongitude());
                    String token = "Bearer " + Preference.getToken(getApplicationContext());

                    Call<ResponInquiry> call = api.CekInquiry(token, mInquiry);
                    call.enqueue(new Callback<ResponInquiry>() {
                        @Override
                        public void onResponse(Call<ResponInquiry> call, Response<ResponInquiry> response) {

                            String code = response.body().getCode();
                            if (code.equals("200")) {

                                if(response.body().getData().getStatus().equals("Sukses")){

                                    PPnamaBp.setText(response.body().getData().getCustomer_name());
                                    PPnomorBp.setText(response.body().getData().getCustomer_no());
                                    PPStatusBp.setText(response.body().getData().getStatus());
                                    PPdayaBp.setText(utils.ConvertRP(response.body().getData().getDetail_product().getDetail().get(0).getAdmin()));
                                    String tanggal = response.body().getData().getCreated_at();
                                    String tahun = tanggal.substring(0, 4);
                                    String bulan = utils.convertBulan(tanggal.substring(5, 7));
                                    String hari = tanggal.substring(8, 10);
                                    setInquiry(response.body().getData().getInquiry_type());
                                    setSkucode(response.body().getData().getBuyer_sku_code());
                                    PPTanggalBp.setText(hari + " " + bulan + " " + tahun);
                                    PPTransaksiBp.setText(response.body().getData().getRef_id());
                                    PPtarifBp.setText(utils.ConvertRP(response.body().getData().getDetail_product().getDetail().get(0).getNilai_tagihan()));
                                    loadingPrimer.dismissDialog();
                                    LineraBPJS.setVisibility(View.VISIBLE);
                                    periksaBpjs.setText("Bayar");



                                }else {
                                    Toast.makeText(getApplicationContext(),response.body().getData().getStatus()+" "+response.body().getData().getDescription(),Toast.LENGTH_SHORT).show();
                                    loadingPrimer.dismissDialog();
                                }



                            } else {

                                StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast2).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponInquiry> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_SHORT).show();

                        }
                    });

                } else {
                    StyleableToast.makeText(getApplicationContext(), "Nomor tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast2).show();

                }

                } else {
                    Intent intent = new Intent(getApplicationContext(), KonfirmasiPembayaran.class);
                    intent.putExtra("hargatotal", getHargaa());
                    intent.putExtra("RefID", PPTransaksiBp.getText().toString());
                    intent.putExtra("sku_code",getSkucode());
                    intent.putExtra("inquiry",getInquiry());
                    intent.putExtra("nomorr",nomorinputBpjs.getText().toString());
                    startActivity(intent);


                }

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

    public void getProduk(String id){

        String token = "Bearer "+ Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponBPJS> call = api.getProdukBpjs(token,id);
        call.enqueue(new Callback<ResponBPJS>() {
            @Override
            public void onResponse(Call<ResponBPJS> call, Response<ResponBPJS> response) {
                if (response.body().getCode().equals("200")){

                    String id = response.body().getData().get(0).getId();
                    getProdukSub(id);

                }

            }

            @Override
            public void onFailure(Call<ResponBPJS> call, Throwable t) {

            }
        });
    }

    public void getProdukSub(String id){
        String token = "Bearer "+ Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukBPJS> call =api.getProdukBpjsSub(token,id);
        call.enqueue(new Callback<ResponProdukBPJS>() {
            @Override
            public void onResponse(Call<ResponProdukBPJS> call, Response<ResponProdukBPJS> response) {

                if(response.body().getCode().equals("200")){

                    String code = response.body().getData().get(0).getCode();
                    setCode(code);

                }
            }

            @Override
            public void onFailure(Call<ResponProdukBPJS> call, Throwable t) {

            }
        });


    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSkucode() {
        return skucode;
    }

    public void setSkucode(String skucode) {
        this.skucode = skucode;
    }

    public String getInquiry() {
        return inquiry;
    }

    public void setInquiry(String inquiry) {
        this.inquiry = inquiry;
    }

    public String getHargaa() {
        return hargaa;
    }

    public void setHargaa(String hargaa) {
        this.hargaa = hargaa;
    }
}