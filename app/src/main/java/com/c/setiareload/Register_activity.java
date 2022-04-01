package com.c.setiareload;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Api.Value;
import com.c.setiareload.Helper.GpsTracker;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Helper.utils;
import com.c.setiareload.Modal.ModalKabupaten;
import com.c.setiareload.Modal.ModalKecamatan;
import com.c.setiareload.Modal.ModalKelurahan;
import com.c.setiareload.Modal.ModalKodePos;
import com.c.setiareload.Modal.ModalProvinsi;
import com.c.setiareload.Model.MRegister;
import com.c.setiareload.sharePreference.Preference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register_activity extends AppCompatActivity implements ModalProvinsi.BottomSheetListener, ModalKabupaten.BottomSheetListenerKabupaten, ModalKecamatan.BottomSheetListenerKecamatan, ModalKelurahan.BottomSheetListenerKelurahan, ModalKodePos.BottomSheetListenerPost {
    EditText provinsi, kecamatan, kabupaten, kelurahan, postcode, namapemilik, email, phone, alamatregis, namakonter, referal, serverid;
    ProgressBar progressBar;
    Button regis;
    GpsTracker gpsTracker;
    int province, Regencie, district, subdistrict, postalCode;
    private ListPopupWindow serverpopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Profil Konter<b></font>"));
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
        namakonter = findViewById(R.id.namakonter);
        postcode = findViewById(R.id.postCode);
        kelurahan = findViewById(R.id.kelurahan);
        provinsi = findViewById(R.id.provinsi);
        kabupaten = findViewById(R.id.kabupaten);
        kecamatan = findViewById(R.id.kecamatan);
        referal = findViewById(R.id.referalcodeRegis);
        serverid = findViewById(R.id.serverID);
//        serverid.setFocusable(false);

//        serverid.setOnClickListener(v -> {
//
//            ArrayList<String> Serverid = new ArrayList<>();
//            Serverid.add("SRVID00000001");
//            Serverid.add("SRVID00000002");
//
//
//            serverpopup = new ListPopupWindow(Register_activity.this);
//            ArrayAdapter adapter = new ArrayAdapter<>(Register_activity.this, R.layout.list_serverid, R.id.tv_serverid, Serverid);
//            serverpopup.setAnchorView(serverid); //this let as set the popup below the EditText
//            serverpopup.setAdapter(adapter);
//            serverpopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    serverid.setText(Serverid.get(position));//we set the selected element in the EditText
//                    serverpopup.dismiss();
//                }
//            });
//            serverpopup.show();
//
//
//        });

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
        bundle.putString("kabupatenkey","");
        modalKecamatan.setArguments(bundle);
        modalKecamatan.show(getSupportFragmentManager(), "Modalkecamatan");
    }

    private void onClickKabupaten() {
        Bundle bundle = new Bundle();
        ModalKabupaten modalKabupaten = new ModalKabupaten();
        bundle.putString("provinsikey","");
        modalKabupaten.setArguments(bundle);
        modalKabupaten.show(getSupportFragmentManager(), "Modalkabupaten");
    }

    private void onClickKelurahan() {
        Bundle bundle = new Bundle();
        ModalKelurahan modalKelurahan = new ModalKelurahan();
        bundle.putString("kecamatankey","");
        modalKelurahan.setArguments(bundle);
        modalKelurahan.show(getSupportFragmentManager(), "Modalkelurahan");
    }

    private void onClickKodePos() {
        Bundle bundle = new Bundle();
        ModalKodePos modalKodePos = new ModalKodePos();
        bundle.putString("kelurahankey","");
        modalKodePos.setArguments(bundle);
        modalKodePos.show(getSupportFragmentManager(), "Modalkodepos");
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



    public void Register(View view) {
        getLocation();


        String name = namapemilik.getText().toString().toUpperCase().trim();
        String Store_name = namakonter.getText().toString().toUpperCase().trim();
        String Phone = phone.getText().toString();
        String Email = email.getText().toString();
        String alamat = alamatregis.getText().toString();
        String parent = referal.getText().toString();
        String serveridd = serverid.getText().toString();
        String IpAddres = getIPaddress();
        String MacAddres = Value.getMacAddress(getApplicationContext());
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

            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
                @Override
                public void onComplete(@NonNull Task<String> task) {
                    String deviceToken = task.getResult();

                    MRegister register = new MRegister(name, deviceToken, Email, Phone, Store_name, MacAddres, IpAddres, alamat, parent, useragent, province, Regencie, district, subdistrict, postalCode, longlitude, latitude, serveridd);
                    Api api = RetroClient.getApiServices();
                    Call<MRegister> call = api.Register(register);

                    call.enqueue(new Callback<MRegister>() {
                        @Override
                        public void onResponse(Call<MRegister> call, Response<MRegister> response) {
                            String code = response.body().getCode();
                            if (code.equals("200")) {
                                String user_id = response.body().getData().getUser_id();
                                String user_code = response.body().getData().getUser_code();
                                String otp_id = response.body().getData().getOtp_id();
                                String phonee = response.body().getData().getPhone();
                                Intent intent = new Intent(Register_activity.this, OTPsend.class);

                                Preference.getSharedPreference(getBaseContext());
                                Preference.setKeyOtpId(getBaseContext(), otp_id);
                                Preference.setKeyPhone(getBaseContext(), phonee);
                                Preference.setKeyUserCode(getBaseContext(), user_code);
                                Preference.setKeyUserId(getBaseContext(), user_id);

                                progressBar.setVisibility(View.GONE);
                                regis.setText("Selanjutnya");

                                namapemilik.setText("");
                                namakonter.setText("");
                                phone.setText("");
                                email.setText("");
                                alamatregis.setText("");
                                provinsi.setText("");
                                kabupaten.setText("");
                                kecamatan.setText("");
                                kelurahan.setText("");
                                postcode.setText("");
                                referal.setText("");
                                serverid.setText("");
                                Preference.setTrackRegister(getApplicationContext(), "1");
                                startActivity(intent);
                                finish();

                            } else {

                                StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast).show();
                                progressBar.setVisibility(View.GONE);
                                regis.setText("Selanjutnya");
                            }


                        }

                        @Override
                        public void onFailure(Call<MRegister> call, Throwable t) {
                            StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan Internet", Toast.LENGTH_LONG, R.style.mytoast2).show();

                        }
                    });

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