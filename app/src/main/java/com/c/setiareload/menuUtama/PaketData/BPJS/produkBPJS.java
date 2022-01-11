package com.c.setiareload.menuUtama.PaketData.BPJS;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Api.Value;
import com.c.setiareload.Helper.GpsTracker;
import com.c.setiareload.Helper.LoadingPrimer;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Helper.utils;
import com.c.setiareload.R;
import com.c.setiareload.Transaksi.MInquiry;
import com.c.setiareload.Transaksi.ResponInquiry;
import com.c.setiareload.menuUtama.PaketData.PulsaPrabayar.KonfirmasiPembayaran;
import com.c.setiareload.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class produkBPJS extends AppCompatActivity implements ModalBpjs.BottomSheetListenerProduksms {

    EditText nomorinputBpjs,pilihProdukBPJS;
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
        pilihProdukBPJS = findViewById(R.id.pilihProdukBPJS);
        String id = getIntent().getStringExtra("id");


        pilihProdukBPJS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModalBpjs modalBpjs = new ModalBpjs();
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                modalBpjs.setArguments(bundle);
                modalBpjs.show(getSupportFragmentManager(),"modal");
            }
        });


        periksaBpjs.setOnClickListener(v -> {

            if (periksaBpjs.getText().toString().equals("Periksa")) {

                if (!nomorinputBpjs.getText().toString().isEmpty()) {

                    LoadingPrimer loadingPrimer = new LoadingPrimer(produkBPJS.this);
                    loadingPrimer.startDialogLoading();

                    GpsTracker gpsTracker = new GpsTracker(getApplicationContext());

                    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .readTimeout(60, TimeUnit.SECONDS)
                            .connectTimeout(60, TimeUnit.SECONDS)
                            .writeTimeout(60, TimeUnit.SECONDS)
                            .build();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Value.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(okHttpClient)
                            .build();

                    Api api = retrofit.create(Api.class);
                    MInquiry mInquiry = new MInquiry(getCode(), nomorinputBpjs.getText().toString(), "PASCABAYAR", Value.getMacAddress(getApplicationContext()), Value.getIPaddress(), Value.getUserAgent(getApplicationContext()),
                            gpsTracker.getLatitude(), gpsTracker.getLongitude());
                    String token = "Bearer " + Preference.getToken(getApplicationContext());

                    Call<ResponInquiry> call = api.CekInquiry(token, mInquiry);
                    call.enqueue(new Callback<ResponInquiry>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(Call<ResponInquiry> call, Response<ResponInquiry> response) {

                            String code = response.body().getCode();
                            if (code.equals("200")) {
//
                                if(response.body().getData().getStatus().equals("Sukses")){

                                    PPnamaBp.setText(response.body().getData().getCustomer_name());
                                    PPnomorBp.setText(response.body().getData().getCustomer_no());
                                    PPStatusBp.setText(response.body().getData().getStatus());
                                    PPdayaBp.setText(utils.ConvertRP(response.body().getData().getAdmin_fee()));
                                    String tanggal = response.body().getData().getCreated_at();
                                    String tahun = tanggal.substring(0, 4);
                                    String bulan = utils.convertBulan(tanggal.substring(5, 7));
                                    String hari = tanggal.substring(8, 10);
                                    setInquiry(response.body().getData().getInquiry_type());
                                    setSkucode(response.body().getData().getBuyer_sku_code());
                                    PPTanggalBp.setText(hari + " " + bulan + " " + tahun);
                                    PPTransaksiBp.setText(response.body().getData().getRef_id());
                                    setHargaa(utils.ConvertRP(response.body().getData().getSelling_price()));
                                    PPtarifBp.setText(utils.ConvertRP(response.body().getData().getSelling_price()));
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
                            loadingPrimer.dismissDialog();

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

//    public void getProduk(String id){
//
//        String token = "Bearer "+ Preference.getToken(getApplicationContext());
//        Api api = RetroClient.getApiServices();
//        Call<ResponBPJS> call = api.getProdukBpjs(token,id);
//        call.enqueue(new Callback<ResponBPJS>() {
//            @Override
//            public void onResponse(Call<ResponBPJS> call, Response<ResponBPJS> response) {
//                if (response.body().getCode().equals("200")){
//
//                    String id = response.body().getData().get(0).getId();
////                    getProdukSub(id);
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponBPJS> call, Throwable t) {
//
//            }
//        });
//    }



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

    @Override
    public void onButtonClick(String name, String id) {
        setCode(id);
        pilihProdukBPJS.setText(name);

    }
}