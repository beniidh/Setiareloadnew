package com.c.setiareload.konter;

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

import com.c.setiareload.Api.Api;
import com.c.setiareload.Api.Value;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Helper.utils;
import com.c.setiareload.Modal.ModalKabupaten;
import com.c.setiareload.Modal.ModalKecamatan;
import com.c.setiareload.Modal.ModalKelurahan;
import com.c.setiareload.Modal.ModalKodePos;
import com.c.setiareload.Modal.ModalProvinsi;
import com.c.setiareload.R;
import com.c.setiareload.sharePreference.Preference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tambahKonter extends AppCompatActivity implements ModalProvinsi.BottomSheetListener, ModalKabupaten.BottomSheetListenerKabupaten, ModalKecamatan.BottomSheetListenerKecamatan, ModalKelurahan.BottomSheetListenerKelurahan, ModalKodePos.BottomSheetListenerPost {

    EditText provinsi, kecamatan, kabupaten, kelurahan, postcode, namapemilik, email, phone, alamatregis, namakonter, PIN, KonfirmPIN;
    ProgressBar progressBar;
    Button regis;

    int province, Regencie, district, subdistrict, postalCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_konter);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 + "'><b>Tambah Konter <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        namapemilik = findViewById(R.id.namapemilik);
        email = findViewById(R.id.emailRegis);
        phone = findViewById(R.id.PhoneRegis);
        alamatregis = findViewById(R.id.alamatRegis);
        progressBar = findViewById(R.id.progressRegister);
        regis = findViewById(R.id.DaftarKonter);
        namakonter = findViewById(R.id.namakonter);
        postcode = findViewById(R.id.postCode);
        kelurahan = findViewById(R.id.kelurahan);
        provinsi = findViewById(R.id.provinsi);
        kabupaten = findViewById(R.id.kabupaten);
        kecamatan = findViewById(R.id.kecamatan);
        PIN = findViewById(R.id.PINadd);
        KonfirmPIN = findViewById(R.id.KonfirmPIN);

        regis.setOnClickListener(v -> {

            Register(namapemilik.getText().toString(), email.getText().toString(), phone.getText().toString(), namakonter.getText().toString(),
                    Value.getMacAddress(getApplicationContext()), Value.getIPaddress(), Value.getMacAddress(getApplicationContext()),
                    PIN.getText().toString(), KonfirmPIN.getText().toString(), Value.getUserAgent(getApplicationContext()),
                    province, Regencie, district, subdistrict, postalCode, Double.valueOf(Preference.getLong(getApplicationContext())), Double.valueOf(Preference.getLang(getApplicationContext()))
            );
        });

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

    public void onClickProvinsi() {
        ModalProvinsi modalProvinsi = new ModalProvinsi();
        modalProvinsi.show(getSupportFragmentManager(), "Modalprovinsi");
    }

    private void onClickKecamatan() {
        Bundle bundle = new Bundle();
        ModalKecamatan modalKecamatan = new ModalKecamatan();
        bundle.putString("kabupatenkey", "");
        modalKecamatan.setArguments(bundle);
        modalKecamatan.show(getSupportFragmentManager(), "Modalkecamatan");
    }

    private void onClickKabupaten() {
        Bundle bundle = new Bundle();
        ModalKabupaten modalKabupaten = new ModalKabupaten();
        bundle.putString("provinsikey", "");
        modalKabupaten.setArguments(bundle);
        modalKabupaten.show(getSupportFragmentManager(), "Modalkabupaten");
    }

    private void onClickKelurahan() {
        Bundle bundle = new Bundle();
        ModalKelurahan modalKelurahan = new ModalKelurahan();
        bundle.putString("kecamatankey", "");
        modalKelurahan.setArguments(bundle);
        modalKelurahan.show(getSupportFragmentManager(), "Modalkelurahan");
    }

    private void onClickKodePos() {
        Bundle bundle = new Bundle();
        ModalKodePos modalKodePos = new ModalKodePos();
        bundle.putString("kelurahankey", "");
        modalKodePos.setArguments(bundle);
        modalKodePos.show(getSupportFragmentManager(), "Modalkodepos");
    }

    private void Register(String name, String email, String phone, String store_name, String mac_address,
                          String ip_address, String address, String password, String confirm_password,
                          String user_agent, int province_id, int regencies_id, int districts_id,
                          int sub_districts_id, int postal_code_id, Double latitude, Double longitude) {
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();

        MRegisKonter mRegisKonter = new MRegisKonter(name, email, phone, store_name, mac_address,
                ip_address, address, utils.hmacSha(password), utils.hmacSha(confirm_password),
                user_agent, province_id, regencies_id, districts_id,
                sub_districts_id, postal_code_id, latitude, longitude);
        Call<MRegisKonter> call = api.RegisterKonter(token, mRegisKonter);
        call.enqueue(new Callback<MRegisKonter>() {
            @Override
            public void onResponse(Call<MRegisKonter> call, Response<MRegisKonter> response) {
                if (response.body().getCode().equals("200")) {
                    Toast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MRegisKonter> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();

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


}