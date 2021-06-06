package com.c.dompetabata;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Api.Value;
import com.c.dompetabata.Helper.GpsTracker;
import com.c.dompetabata.Helper.utils;
import com.c.dompetabata.Modal.ModalKabupaten;
import com.c.dompetabata.Modal.ModalKecamatan;
import com.c.dompetabata.Modal.ModalProvinsi;
import com.c.dompetabata.Model.MRegister;
import com.c.dompetabata.Model.ModelKabupaten;
import com.c.dompetabata.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register_activity extends AppCompatActivity implements ModalProvinsi.BottomSheetListener, ModalKabupaten.BottomSheetListenerKabupaten, ModalKecamatan.BottomSheetListenerKecamatan {
    EditText provinsi, kecamatan, kabupaten, namapemilik, email, phone, alamatregis;
    ProgressBar progressBar;
    Button regis;
    GpsTracker gpsTracker;
    int province;
    int Regencie;
    int district;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Profil Konter<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        // give permission
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        getLocation();
        namapemilik = findViewById(R.id.namapemilik);
        email = findViewById(R.id.emailRegis);
        phone = findViewById(R.id.PhoneRegis);
        alamatregis = findViewById(R.id.alamatRegis);
        progressBar = findViewById(R.id.progressRegister);
        regis = findViewById(R.id.Register_Button);


        //ID Provinsi definition
        provinsi = findViewById(R.id.provinsi);
        provinsi.setFocusable(false);
        provinsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickProvinsi();
            }
        });

        //ID Kabupaten definition
        kabupaten = findViewById(R.id.kabupaten);
        kabupaten.setFocusable(false);
        kabupaten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKabupaten();
            }
        });

        kecamatan = findViewById(R.id.kecamatan);
        kecamatan.setFocusable(false);
        kecamatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickKecamatan();
            }
        });

//        try {
//            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
//                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        getLocation();

//        String IpAddres = getIPaddress();
//        String MacAddres = getMacAddress();
//        String useragent = getUserAgent();
//        String longlitude = String.valueOf(gpsTracker.getLongitude());
//        String latitude = String.valueOf(gpsTracker.getLatitude());
//
//        Toast.makeText(getApplication(),IpAddres,Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplication(),MacAddres,Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplication(),useragent,Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplication(),longlitude,Toast.LENGTH_LONG).show();
//        Toast.makeText(getApplication(),latitude,Toast.LENGTH_LONG).show();


    }

    private void onClickKecamatan() {
        ModalKecamatan modalKecamatan = new ModalKecamatan();
        modalKecamatan.show(getSupportFragmentManager(), "Modalkecamatan");
    }

    private void onClickKabupaten() {
        ModalKabupaten modalKabupaten = new ModalKabupaten();
        modalKabupaten.show(getSupportFragmentManager(), "Modalkabupaten");
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

    public void onClickProvinsi() {
        ModalProvinsi modalProvinsi = new ModalProvinsi();
        modalProvinsi.show(getSupportFragmentManager(), "Modalprovinsi");
    }

    public void Register(View view) {
        getLocation();


        String name = namapemilik.getText().toString();
        String Store_name = "Xtra Cell";
        String Phone = phone.getText().toString();
        String Email = email.getText().toString();
        String alamat = alamatregis.getText().toString();
        String parent = "D00g0g0";

        int subdistrict = 11;
        int postalCode = 14;
        String IpAddres = getIPaddress();
        String MacAddres = getMacAddress();
        String useragent = getUserAgent();
        double longlitude = gpsTracker.getLongitude();
        double latitude = gpsTracker.getLatitude();
        String prov = String.valueOf(province);


        if (name.isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Nama tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast).show();
        } else if (Phone.isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Telepon tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast).show();
        } else if (Email.isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Email tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            regis.setText("");

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Value.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MRegister register = new MRegister(name, Email, Phone, Store_name, MacAddres, IpAddres, alamat, parent, useragent, province, Regencie, district, subdistrict, postalCode, longlitude, latitude);
            Api api = retrofit.create(Api.class);
            Call<MRegister> call = api.Register(register);
            namapemilik.setText("");
            phone.setText("");
            email.setText("");
            call.enqueue(new Callback<MRegister>() {
                @Override
                public void onResponse(Call<MRegister> call, Response<MRegister> response) {
                    String code = response.body().getCode();
                    if (code.equals("200")) {
                        String user_id = response.body().getData().getUser_id();
                        String user_code = response.body().getData().getUser_code();
                        String otp_id = response.body().getData().getOtp_id();
                        String phone = response.body().getData().getPhone();
                        Intent intent = new Intent(Register_activity.this, RegisterFoto_activity.class);

                        Preference.getSharedPreference(getBaseContext());
                        Preference.setKeyOtpId(getBaseContext(), otp_id);
                        Preference.setKeyPhone(getBaseContext(), phone);
                        Preference.setKeyUserCode(getBaseContext(), user_code);
                        Preference.setKeyUserId(getBaseContext(), user_id);
//
//                        intent.putExtra("user_id",user_id);
//                        intent.putExtra("user_code",user_code);
//                        intent.putExtra("phone",phone);
//                        intent.putExtra("otp_id",otp_id);
                        progressBar.setVisibility(View.GONE);
                        regis.setText("Selanjutnya");


                        startActivity(intent);

                    } else {

                        StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast).show();
                        progressBar.setVisibility(View.GONE);
                        regis.setText("Selanjutnya");
                    }


                }

                @Override
                public void onFailure(Call<MRegister> call, Throwable t) {

                }
            });


        }

    }

    private String getUserAgent() {

        String ua = new WebView(this).getSettings().getUserAgentString();
        return ua;
    }

    private String getIPaddress() {

        String IP = utils.getIPAddress(true);
        return IP;
    }

    private String getMacAddress() {
        String MAC = utils.getMACAddress("wlan0");//phone if pc use eth0 if phone wlan0
        return MAC;

    }

    public void getLocation() {
        gpsTracker = new GpsTracker(Register_activity.this);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

        } else {
            gpsTracker.showSettingsAlert();
        }
    }

    @Override
    public void onButtonClick(String name, String id) {
        provinsi.setText(name);
        province = Integer.parseInt(id);

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
}