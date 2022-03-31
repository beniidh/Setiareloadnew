package com.c.latansa.menuUtama.PulsaPascaBayar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.latansa.Api.Api;
import com.c.latansa.Api.Value;
import com.c.latansa.Helper.GpsTracker;
import com.c.latansa.Helper.LoadingPrimer;
import com.c.latansa.Helper.RetroClient;
import com.c.latansa.Helper.utils;
import com.c.latansa.Transaksi.MInquiry;
import com.c.latansa.Transaksi.ResponInquiry;
import com.c.latansa.menuUtama.PulsaPrabayar.KonfirmasiPembayaran;
import com.c.latansa.R;
import com.c.latansa.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class PulsaPascaBayar_activity extends AppCompatActivity {

    EditText nomorbelipulsa;
     String url;
    ImageView iconproduk;
    String skucode, inquiry,sellingprice;
    String codeinquiry;
    Button periksapaket,bayartagihanpulsapascaa;
    TextView name, nomcos, deskrip, tanggall, transaksi, tagihan,PPstatus;
    LinearLayout LinearpPasca;
    String hargaa;

    ImageView getContact;
    private static final int CONTACT_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category_activity);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Pulsa Pasca Bayar <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        iconproduk = findViewById(R.id.iconprodukPasca);
        nomorbelipulsa = findViewById(R.id.nomorbelipulsaPasca);
        periksapaket = findViewById(R.id.PeriksaPaketPascaPulsa);
        name = findViewById(R.id.PPnama);
        nomcos = findViewById(R.id.PPnomor);
        deskrip = findViewById(R.id.PPdeskription);
        tanggall = findViewById(R.id.PPTanggal);
        transaksi = findViewById(R.id.PPTransaksi);
        tagihan = findViewById(R.id.PPjumlahTagihan);
        PPstatus = findViewById(R.id.PPstatus);
        LinearpPasca = findViewById(R.id.LinearpPasca);

        getContact = findViewById(R.id.getContact);
        getContact.setOnClickListener(view -> {
            if (checkContactPermission()) {
                openYourActivity();
            } else {
                requestContactPermission();
            }
        });


        periksapaket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (periksapaket.getText().toString().equals("Periksa")) {

                    if (!nomorbelipulsa.getText().toString().isEmpty()) {

                        if (!codeinquiry.equals("")) {

                            LoadingPrimer loadingPrimer = new LoadingPrimer(PulsaPascaBayar_activity.this);
                            loadingPrimer.startDialogLoading();

                            GpsTracker gpsTracker = new GpsTracker(getApplicationContext());

                            Api api = RetroClient.getApiServices();
                            MInquiry mInquiry = new MInquiry(codeinquiry, nomorbelipulsa.getText().toString(), "PASCABAYAR", Value.getMacAddress(getApplicationContext()), Value.getIPaddress(), Value.getUserAgent(getApplicationContext()), gpsTracker.getLatitude(), gpsTracker.getLongitude());
                            String token = "Bearer " + Preference.getToken(getApplicationContext());

                            Call<ResponInquiry> call = api.CekInquiry(token, mInquiry);
                            call.enqueue(new Callback<ResponInquiry>() {
                                @Override
                                public void onResponse(Call<ResponInquiry> call, Response<ResponInquiry> response) {

                                    String code = response.body().getCode();
                                    if (code.equals("200")) {
                                        name.setText(response.body().getData().getCustomer_name());
                                        nomcos.setText(response.body().getData().getCustomer_no());
                                        deskrip.setText(response.body().getData().getStatus());
                                        String tanggal = response.body().getData().getCreated_at();
                                        String tahun = tanggal.substring(0, 4);
                                        setInquiry(response.body().getData().getInquiry_type());
                                        setSkucode(response.body().getData().getBuyer_sku_code());
                                        String bulan = utils.convertBulan(tanggal.substring(5, 7));
                                        String hari = tanggal.substring(8, 10);
                                        tanggall.setText(hari + " " + bulan + " " + tahun);
                                        transaksi.setText(response.body().getData().getRef_id());
                                        tagihan.setText(utils.ConvertRP(response.body().getData().getSelling_price()));
                                        sellingprice = response.body().getData().getSelling_price();
                                        loadingPrimer.dismissDialog();

                                        String status = response.body().getData().getStatus();
                                        if (status.equals("Sukses")) {
                                            LinearpPasca.setVisibility(View.VISIBLE);
                                            periksapaket.setText("Bayar");

                                        } else {

                                            Toast.makeText(getApplicationContext(), response.body().getData().getStatus()+" "+response.body().getData().getDescription(), Toast.LENGTH_LONG).show();
                                        }


                                    } else {

                                        StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast2).show();
                                    }


                                }

                                @Override
                                public void onFailure(Call<ResponInquiry> call, Throwable t) {

                                }
                            });

                        } else {

                            StyleableToast.makeText(getApplicationContext(), "Produk belum tersedia", R.style.mytoast2).show();
                        }


                    } else if (nomorbelipulsa.getText().toString().length() < 4) {
                        StyleableToast.makeText(getApplicationContext(), "Lengkapi nomor", R.style.mytoast2).show();

                    } else {
                        StyleableToast.makeText(getApplicationContext(), "Nomor tidak boleh kosong", R.style.mytoast2).show();
                    }
                }else {

                    Intent intent = new Intent(getApplicationContext(), KonfirmasiPembayaran.class);
                    intent.putExtra("hargga",sellingprice );
                    intent.putExtra("RefID", transaksi.getText().toString());
                    intent.putExtra("sku_code",getSkucode());
                    intent.putExtra("inquiry",getInquiry());
                    intent.putExtra("nomorr",nomcos.getText().toString());
                    startActivity(intent);
                }
            }
        });



        nomorbelipulsa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (nomorbelipulsa.length() >= 4) {

                    String nomor = nomorbelipulsa.getText().toString().substring(0,4);
                    String id = getIntent().getStringExtra("id");
                    getProdukByPrefix(nomor, id);

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

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

    public void getProdukByPrefix(String prefix, String id) {
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponPulsaPasca> call = api.getSubPulsaPascaByPrefix(token, prefix, id);
        call.enqueue(new Callback<ResponPulsaPasca>() {
            @Override
            public void onResponse(Call<ResponPulsaPasca> call, Response<ResponPulsaPasca> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    String id = response.body().getData().getId();
                    String urlicon = response.body().getData().getIcon();
                    Picasso.get().load(urlicon).into(iconproduk);
                    Preference.setUrlIcon(getApplicationContext(),urlicon);
                    getCodeProdukPasca(id);
                } else {
                    StyleableToast.makeText(getApplicationContext(), "Nomor tidak Terdaftar", R.style.mytoast2).show();
                }

            }

            @Override
            public void onFailure(Call<ResponPulsaPasca> call, Throwable t) {

            }
        });


    }

    public void getCodeProdukPasca(String id) {
        String token = "Bearer " + Preference.getToken(getApplicationContext());

        Api api = RetroClient.getApiServices();
        Call<ResponProdukSubPPasca> call = api.getProdukPulsaPasca(token, id);
        call.enqueue(new Callback<ResponProdukSubPPasca>() {
            @Override
            public void onResponse(Call<ResponProdukSubPPasca> call, Response<ResponProdukSubPPasca> response) {
                String code = response.body().getCode();

                if (code.equals("200")) {
                    codeinquiry = response.body().getData().get(0).getCode();


                } else {

                    codeinquiry = "";
                }

            }

            @Override
            public void onFailure(Call<ResponProdukSubPPasca> call, Throwable t) {

            }
        });

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

    //Chek Contact Permision
    private boolean checkContactPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    //permintaan koneksi
    private void requestContactPermission() {
        String[] permision = {Manifest.permission.READ_CONTACTS};
        ActivityCompat.requestPermissions(this, permision, CONTACT_PERMISSION_CODE);

    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Cursor cursor1, cursor2;
                        Uri uri = data.getData();

                        cursor1 = getContentResolver().query(uri, null, null, null, null);
                        if (cursor1.moveToFirst()) {
                            @SuppressLint("Range") String contacid = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));
                            @SuppressLint("Range") String contactName = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            @SuppressLint("Range") String idResult = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                            int idResultHold = Integer.parseInt(idResult);

                            if (idResultHold == 1) {
                                cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.
                                                Phone.CONTENT_URI, null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " +
                                                contacid, null, null);

                                while (cursor2.moveToNext()) {

                                    @SuppressLint("Range") String contactNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                                    if (contactNumber.substring(0, 3).equals("+62")) {
                                        String nom = "0" + contactNumber.substring(3).replaceAll(" ","");
                                        nomorbelipulsa.setText(nom.replaceAll("-",""));

                                    } else {
                                        nomorbelipulsa.setText(contactNumber);
                                    }

                                }

                                cursor2.close();


                            }

                            cursor1.close();
                        }


                    }
                }
            });

    public void openYourActivity() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        launchSomeActivity.launch(intent);
    }
}