package com.c.dompetabata.TambahKonter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Api.Value;
import com.c.dompetabata.Helper.GpsTracker;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Modal.ModalKabupaten;
import com.c.dompetabata.Modal.ModalKecamatan;
import com.c.dompetabata.Modal.ModalKelurahan;
import com.c.dompetabata.Modal.ModalKodePos;
import com.c.dompetabata.Modal.ModalProvinsi;
import com.c.dompetabata.R;
import com.c.dompetabata.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class addKonter extends AppCompatActivity implements ModalProvinsi.BottomSheetListener, ModalKabupaten.BottomSheetListenerKabupaten, ModalKecamatan.BottomSheetListenerKecamatan, ModalKelurahan.BottomSheetListenerKelurahan, ModalKodePos.BottomSheetListenerPost {

    EditText provinsi, kecamatan, kabupaten, kelurahan, postcode, namapemilik, email, phone, alamatregis, namakonter, referal, serverid;
    ProgressBar progressBar;
    int province, Regencie, district, subdistrict, postalCode;
    Button regis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_konter);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Tambah Konter<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);


        namapemilik = findViewById(R.id.namapemilikKonter);
        email = findViewById(R.id.emailRegisKonter);
        phone = findViewById(R.id.PhoneRegisKonter);
        alamatregis = findViewById(R.id.alamatRegisKonter);
        progressBar = findViewById(R.id.progressRegister);
        regis = findViewById(R.id.Register_ButtonKonter);
        namakonter = findViewById(R.id.namakonterKonter);
        postcode = findViewById(R.id.postCodeKonter);
        kelurahan = findViewById(R.id.kelurahanKonter);
        provinsi = findViewById(R.id.provinsiKonter);
        kabupaten = findViewById(R.id.kabupatenKonter);
        kecamatan = findViewById(R.id.kecamatanKonter);

        regis.setOnClickListener(v -> addKonter());

        provinsi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                kabupaten.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        kabupaten.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                kecamatan.setEnabled(true);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        kecamatan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                kelurahan.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        kelurahan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                postcode.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        postcode.setFocusable(false);
        postcode.setEnabled(false);
        postcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKodePos();
            }
        });


        kelurahan.setFocusable(false);
        kelurahan.setEnabled(false);
        kelurahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKelurahan();

            }
        });

        //ID Provinsi definition

        provinsi.setFocusable(false);
        provinsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProvinsi();
            }
        });


        //ID Kabupaten definition

        kabupaten.setFocusable(false);
        kabupaten.setEnabled(false);
        kabupaten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKabupaten();
            }
        });


        kecamatan.setFocusable(false);
        kecamatan.setEnabled(false);
        kecamatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKecamatan();
            }
        });


    }


    public void addKonter() {

        GpsTracker gpsTracker = new GpsTracker(getApplicationContext());
        String token = "Bearer " + Preference.getToken(getApplicationContext());

        String name = namapemilik.getText().toString();
        String namakonterr = namakonter.getText().toString();
        String emaill = email.getText().toString();
        String phonne = phone.getText().toString();
        String address = alamatregis.getText().toString();
        double longlitutde = gpsTracker.getLatitude();
        double latitude = gpsTracker.getLongitude();
        String UA = Value.getUserAgent(getApplicationContext());
        String IP = Value.getIPaddress();
        String mac = Value.getMacAddress();


        SendDataKonter sendDataKonter = new SendDataKonter(name, namakonterr, emaill, phonne, address, IP, mac, UA, province, Regencie, district, subdistrict, postalCode, longlitutde, latitude);
        Api api = RetroClient.getApiServices();
        Call<ResponTambahKonter> call = api.registerKonter(token, sendDataKonter);
        call.enqueue(new Callback<ResponTambahKonter>() {
            @Override
            public void onResponse(Call<ResponTambahKonter> call, Response<ResponTambahKonter> response) {

                String code = response.body().getCode();
                if (code.equals("200")) {

                    StyleableToast.makeText(getApplicationContext(), "Konter Berhasil Ditambahkan", Toast.LENGTH_SHORT, R.style.mytoast).show();
                    finish();
                } else {

                    StyleableToast.makeText(getApplicationContext(), "Gagal " + response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast).show();
                }

            }

            @Override
            public void onFailure(Call<ResponTambahKonter> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });

    }

    private void onClickKecamatan() {
        ModalKecamatan modalKecamatan = new ModalKecamatan();
        modalKecamatan.show(getSupportFragmentManager(), "Modalkecamatan");
    }

    private void onClickKabupaten() {
        ModalKabupaten modalKabupaten = new ModalKabupaten();
        modalKabupaten.show(getSupportFragmentManager(), "Modalkabupaten");
    }

    private void onClickKelurahan() {
        ModalKelurahan modalKelurahan = new ModalKelurahan();
        modalKelurahan.show(getSupportFragmentManager(), "Modalkelurahan");
    }

    private void onClickKodePos() {
        ModalKodePos modalKodePos = new ModalKodePos();
        modalKodePos.show(getSupportFragmentManager(), "Modalkelurahan");
    }

    public void onClickProvinsi() {
        ModalProvinsi modalProvinsi = new ModalProvinsi();
        modalProvinsi.show(getSupportFragmentManager(), "Modalprovinsi");
    }

    @Override
    public void onButtonClick(String name, String id) {
        provinsi.setText(name);
        province = Integer.parseInt(id);
        Preference.setIDProvinsi(getApplicationContext(), id);

    }

    @Override
    public void onButtonClickKabupaten(String name, String id) {
        kabupaten.setText(name);
        Regencie = Integer.parseInt(id);

    }

    @Override
    public void onButtonClickKecamatan(String name, String id) {
        kecamatan.setText(name);
        district = Integer.parseInt(id);
    }


    @Override
    public void onButtonClickKelurahan(String name, String id) {
        kelurahan.setText(name);
        subdistrict = Integer.parseInt(id);
    }

    @Override
    public void onButtonClickPost(String postalcode, String id) {
        postcode.setText(postalcode);
        postalCode = Integer.valueOf(id);

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
}