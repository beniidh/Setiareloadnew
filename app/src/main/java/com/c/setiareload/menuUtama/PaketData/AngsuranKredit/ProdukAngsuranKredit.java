package com.c.setiareload.menuUtama.PaketData.AngsuranKredit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
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
import com.c.setiareload.menuUtama.PaketData.BPJS.produkBPJS;
import com.c.setiareload.menuUtama.PaketData.PulsaPrabayar.KonfirmasiPembayaran;
import com.c.setiareload.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProdukAngsuranKredit extends AppCompatActivity implements ModalAngsuran.BottomSheetListenerProduksms {

    EditText inputprodukangsuran,inputnomorangsuran;
    TextView tujukarakterangsuran;
    LinearLayout LinerAngsuran;
    TextView PPnomorA,PPnamaA,PPtarifA,PPdayaA,PPjumlahTagihanA,PPTanggalA,PPTransaksiA,PPStatusA;
    AdapterProdukAngsuran adapterProdukAngsuran;
    Button PeriksaA;
    RecyclerView recyclerView;
    ArrayList<ResponProdukAngsuran.VoucherData> mData = new ArrayList<>();
    String type ="PASCABAYAR";
    String id;
    String skucode, inquiry;
    String hargaa;
    String code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_angsuran_kredit);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Angsuran Kredit <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);


        PPnomorA = findViewById(R.id.PPnomorA);
        PPnamaA= findViewById(R.id.PPnamaA);
        PPtarifA= findViewById(R.id.PPtarifA);
        PPdayaA = findViewById(R.id.PPdayaA);
        PPjumlahTagihanA = findViewById(R.id.PPjumlahTagihanA);
        PPTanggalA= findViewById(R.id.PPTanggalA);
        PPTransaksiA = findViewById(R.id.PPTransaksiA);
        PPStatusA = findViewById(R.id.PPStatusA);
        LinerAngsuran = findViewById(R.id.LinerAngsuran);

        inputprodukangsuran = findViewById(R.id.inputprodukangsuran);
        PeriksaA = findViewById(R.id.PeriksaA);
        inputprodukangsuran.setFocusable(false);

        inputnomorangsuran = findViewById(R.id.inputnomorangsuran);


        inputprodukangsuran.setOnClickListener(v -> {
            String id = getIntent().getStringExtra("id");
            Bundle bundle = new Bundle();
            ModalAngsuran modalAngsuran = new ModalAngsuran();
            bundle.putString("id",id);
            modalAngsuran.setArguments(bundle);
            modalAngsuran.show(getSupportFragmentManager(),"modal angsuran");
        });


        tujukarakterangsuran = findViewById(R.id.tujukarakterangsuran);

        inputnomorangsuran.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(inputnomorangsuran.length() >= 7){
                    tujukarakterangsuran.setVisibility(View.INVISIBLE);
                } else {
                    tujukarakterangsuran.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        PeriksaA.setOnClickListener(v -> {

            if (PeriksaA.getText().toString().equals("Periksa")) {

                if (!inputnomorangsuran.getText().toString().isEmpty()) {

                    LoadingPrimer loadingPrimer = new LoadingPrimer(ProdukAngsuranKredit.this);
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
                    MInquiry mInquiry = new MInquiry(getId(), inputnomorangsuran.getText().toString(), "PASCABAYAR", Value.getMacAddress(getApplicationContext()), Value.getIPaddress(), Value.getUserAgent(getApplicationContext()),
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

                                    PPnamaA.setText(response.body().getData().getCustomer_name());
                                    PPnomorA.setText(response.body().getData().getCustomer_no());
                                    PPStatusA.setText(response.body().getData().getStatus());
                                    PPdayaA.setText(utils.ConvertRP(response.body().getData().getAdmin_fee()));
                                    String tanggal = response.body().getData().getCreated_at();
                                    String tahun = tanggal.substring(0, 4);
                                    String bulan = utils.convertBulan(tanggal.substring(5, 7));
                                    String hari = tanggal.substring(8, 10);
                                    setInquiry(response.body().getData().getInquiry_type());
                                    setSkucode(response.body().getData().getBuyer_sku_code());
                                    PPTanggalA.setText(hari + " " + bulan + " " + tahun);
                                    PPTransaksiA.setText(response.body().getData().getRef_id());
                                    setHargaa(utils.ConvertRP(response.body().getData().getSelling_price()));
                                    PPtarifA.setText(utils.ConvertRP(response.body().getData().getSelling_price()));
                                    loadingPrimer.dismissDialog();
                                    LinerAngsuran.setVisibility(View.VISIBLE);
                                    PeriksaA.setText("Bayar");



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
                intent.putExtra("RefID", PPTransaksiA.getText().toString());
                intent.putExtra("sku_code",getSkucode());
                intent.putExtra("inquiry",getInquiry());
                intent.putExtra("nomorr",inputnomorangsuran.getText().toString());
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

    @Override
    public void onButtonClick(String name, String id) {
        getProduk(id,inputnomorangsuran.getText().toString());
        inputprodukangsuran.setText(name);
        setId(id);
    }

    public void getProduk(String id,String nomor){
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukAngsuran> call = api.getProdukAngsuranSub(token,id);
        call.enqueue(new Callback<ResponProdukAngsuran>() {
            @Override
            public void onResponse(Call<ResponProdukAngsuran> call, Response<ResponProdukAngsuran> response) {
                String code = response.body().getCode();
                if (code.equals("200")){

                  setId(response.body().getData().get(0).getCode());

                }else {

                    Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponProdukAngsuran> call, Throwable t) {

            }
        });


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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