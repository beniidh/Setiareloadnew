package com.c.dompetabata;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Api.Value;
import com.c.dompetabata.Modal.ModalKabupaten;
import com.c.dompetabata.Modal.ModalKecamatan;
import com.c.dompetabata.Modal.ModalProvinsi;
import com.c.dompetabata.Model.MRegister;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register_activity extends AppCompatActivity {
    EditText provinsi,kecamatan,kabupaten,namapemilik,email,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_activity);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'>Profil Konter </font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        namapemilik = findViewById(R.id.namapemilik);
        email = findViewById(R.id.emailRegis);
        phone = findViewById(R.id.PhoneRegis);


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


    }

    private void onClickKecamatan() {
        ModalKecamatan modalKecamatan = new ModalKecamatan();
        modalKecamatan.show(getSupportFragmentManager(),"Modalkecamatan");
    }

    private void onClickKabupaten() {
        ModalKabupaten modalKabupaten = new ModalKabupaten();
        modalKabupaten.show(getSupportFragmentManager(),"Modalkabupaten");
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

    public void onClickProvinsi(){
        ModalProvinsi modalProvinsi = new ModalProvinsi();
        modalProvinsi.show(getSupportFragmentManager(),"Modalprovinsi");
    }

    public void Register(View view){
        String name = namapemilik.getText().toString();
        String Phone = phone.getText().toString();
        String Email = email.getText().toString();

        if(name.isEmpty()){
            StyleableToast.makeText(getApplicationContext(), "Nama tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast).show();
        }else if(Phone.isEmpty()){
            StyleableToast.makeText(getApplicationContext(), "Telepon tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast).show();
        } else if(Email.isEmpty()){
            StyleableToast.makeText(getApplicationContext(), "Email tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast).show();
        }else {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Value.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MRegister register = new MRegister(name,Email,Phone);
            Api api = retrofit.create(Api.class);
            Call<MRegister> call = api.Register(register);
            call.enqueue(new Callback<MRegister>() {
                @Override
                public void onResponse(Call<MRegister> call, Response<MRegister> response) {
                    String message = response.body().getMessage();
                    StyleableToast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT, R.style.mytoast).show();

                    Intent intent = new Intent(Register_activity.this,RegisterFoto_activity.class);
                    startActivity(intent);

                }

                @Override
                public void onFailure(Call<MRegister> call, Throwable t) {

                }
            });


        }



    }
}