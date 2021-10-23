package com.c.setiareload.menuUtama.PaketData.ListrikPLNPasca;

import androidx.appcompat.app.AppCompatActivity;

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
import com.c.setiareload.menuUtama.PaketData.PulsaPrabayar.KonfirmasiPembayaran;
import com.c.setiareload.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pln_produk_pasca extends AppCompatActivity {
    EditText inputplnpasca;
    TextView tujukarakterplnpasca;
    String skucode, inquiry,hargaa,sellingprice,codeplnpasca;
    Button periksaplnpascaP, bayartagihanpulsapascaP;
    TextView name, nomcos, deskrip, tanggall, transaksi, tagihan, PPtarifP,PPStatusP,PPdayaP,PPAdminP;
    LinearLayout LinearPasca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pln_produk_pasca);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Listrik PLN Pasca <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        inputplnpasca = findViewById(R.id.nomorinputPLNPasca);
        tujukarakterplnpasca = findViewById(R.id.tujukarakterplnpasca);
        periksaplnpascaP = findViewById(R.id.periksaplnpascaP);
        name = findViewById(R.id.PPnamaP);
        nomcos = findViewById(R.id.PPnomorP);
        deskrip = findViewById(R.id.PPdeskriptionP);
        tanggall = findViewById(R.id.PPTanggalP);
        transaksi = findViewById(R.id.PPTransaksiP);
        PPtarifP = findViewById(R.id.PPtarifP);
        tagihan = findViewById(R.id.PPjumlahTagihanP);
        LinearPasca = findViewById(R.id.LinearPasca);
        PPStatusP = findViewById(R.id.PPStatusP);
        PPdayaP = findViewById(R.id.PPdayaP);
        PPAdminP = findViewById(R.id.PPAdminP);


        String id = getIntent().getStringExtra("id");
        getCodesub(id);


        periksaplnpascaP.setOnClickListener(v -> {

            if (periksaplnpascaP.getText().toString().equals("Periksa")) {

                if (!inputplnpasca.getText().toString().isEmpty()) {

                    LoadingPrimer loadingPrimer = new LoadingPrimer(Pln_produk_pasca.this);
                    loadingPrimer.startDialogLoading();

                    GpsTracker gpsTracker = new GpsTracker(getApplicationContext());

                    Api api = RetroClient.getApiServices();
                    MInquiry mInquiry = new MInquiry(getCodeplnpasca(), inputplnpasca.getText().toString(), "PASCABAYAR", Value.getMacAddress(getApplicationContext()), Value.getIPaddress(), Value.getUserAgent(getApplicationContext()), gpsTracker.getLatitude(), gpsTracker.getLongitude());
                    String token = "Bearer " + Preference.getToken(getApplicationContext());

                    Call<ResponInquiry> call = api.CekInquiry(token, mInquiry);
                    call.enqueue(new Callback<ResponInquiry>() {
                        @Override
                        public void onResponse(Call<ResponInquiry> call, Response<ResponInquiry> response) {

                            String code = response.body().getCode();
                            if (code.equals("200")) {

                                if (response.body().getData().getStatus().equals("Sukses")) {
                                    name.setText(response.body().getData().getCustomer_name());
                                    nomcos.setText(response.body().getData().getCustomer_no());
                                    deskrip.setText(response.body().getData().getDescription());
                                    String tanggal = response.body().getData().getCreated_at();
                                    String tahun = tanggal.substring(0, 4);
                                    String bulan = utils.convertBulan(tanggal.substring(5, 7));
                                    String hari = tanggal.substring(8, 10);
                                    setInquiry(response.body().getData().getInquiry_type());
                                    setSkucode(response.body().getData().getBuyer_sku_code());
                                    tanggall.setText(hari + " " + bulan + " " + tahun);
                                    PPdayaP.setText(response.body().getData().getDetail_product().getDaya());
                                    tagihan.setText(utils.ConvertRP(response.body().getData().getBasic_price()));
                                    transaksi.setText(response.body().getData().getRef_id());
                                    PPAdminP.setText(utils.ConvertRP(response.body().getData().getDetail_product().getDetail().get(0).getAdmin()));
                                    PPtarifP.setText(response.body().getData().getDetail_product().getTarif());
                                    PPStatusP.setText(response.body().getData().getStatus());
                                    setHargaa(response.body().getData().getDetail_product().getDetail().get(0).getNilai_tagihan());
                                    setSellingprice(response.body().getData().getSelling_price());

                                    loadingPrimer.dismissDialog();
                                    periksaplnpascaP.setText("Bayar");
                                    LinearPasca.setVisibility(View.VISIBLE);

                                }else {

                                    Toast.makeText(getApplicationContext(),response.body().getData().getStatus()+" "+response.body().getData().getDescription(),Toast.LENGTH_LONG).show();
                                    loadingPrimer.dismissDialog();
                                }

                            } else {

                                StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast2).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponInquiry> call, Throwable t) {
                            Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();
                            loadingPrimer.dismissDialog();


                        }
                    });

                } else {
                    StyleableToast.makeText(getApplicationContext(), "Nomor tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast2).show();

                }

            } else {
                Intent intent = new Intent(getApplicationContext(), KonfirmasiPembayaran.class);
                intent.putExtra("hargatotal", utils.ConvertRP(getSellingprice()));
                intent.putExtra("RefID", transaksi.getText().toString());
                intent.putExtra("sku_code",getSkucode());
                intent.putExtra("inquiry",getInquiry());
                intent.putExtra("nomorr",nomcos.getText().toString());
                startActivity(intent);


            }
        });




        inputplnpasca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (inputplnpasca.length() >= 7) {

                    tujukarakterplnpasca.setVisibility(View.INVISIBLE);

                } else {
                    tujukarakterplnpasca.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void getCodePLN(String id){
        String token = "Bearer "+Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponGetCodePasca> call = api.getCodePLNPasca(token,id);
        call.enqueue(new Callback<ResponGetCodePasca>() {
            @Override
            public void onResponse(Call<ResponGetCodePasca> call, Response<ResponGetCodePasca> response) {
                String code = response.body().getCode();
                if(code.equals("200")){

                    String codePLN = response.body().getData().get(0).getCode();
                    setCodeplnpasca(codePLN);


                }else {

                    Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponGetCodePasca> call, Throwable t) {

            }
        });



    }


    public void getCodesub(String id){
        String token = "Bearer "+Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponCodeSub> call = api.getCodeSubPln(token,id);
        call.enqueue(new Callback<ResponCodeSub>() {
            @Override
            public void onResponse(Call<ResponCodeSub> call, Response<ResponCodeSub> response) {
                String code = response.body().getCode();
                if(code.equals("200")){

                    getCodePLN(response.body().getData().get(0).getId());


                }else {

                    Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponCodeSub> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_SHORT).show();
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

    public String getSellingprice() {
        return sellingprice;
    }

    public void setSellingprice(String sellingprice) {
        this.sellingprice = sellingprice;
    }

    public String getCodeplnpasca() {
        return codeplnpasca;
    }

    public void setCodeplnpasca(String codeplnpasca) {
        this.codeplnpasca = codeplnpasca;
    }
}