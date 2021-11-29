package com.c.setiareload.KomisiSales;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Helper.utils;
import com.c.setiareload.Login_Activity;
import com.c.setiareload.R;
import com.c.setiareload.sharePreference.Preference;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Komisi_sales extends AppCompatActivity {

    EditText TanggalMulaiKomisi, TanggalSelesaiKomisi;
    TextView jumlahKomisi;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button periksa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komisi_sales);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Komisi Sales<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        TanggalSelesaiKomisi = findViewById(R.id.TanggalSelesaiKomisi);
        TanggalMulaiKomisi = findViewById(R.id.TanggalMulaiKomisi);
        jumlahKomisi = findViewById(R.id.jumlahKomisi);
        periksa = findViewById(R.id.PeriksaKomisi);

        periksa.setOnClickListener(view -> {

            if(TanggalMulaiKomisi.getText().toString().equals("")||TanggalSelesaiKomisi.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),"Tanggal tidak boleh kosong",Toast.LENGTH_SHORT).show();

            }else {

                getKomisi(TanggalMulaiKomisi.getText().toString(), TanggalSelesaiKomisi.getText().toString());
            }

        });

        TanggalMulaiKomisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialogStart();
            }
        });
        TanggalSelesaiKomisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateDialogEnd();
            }
        });



    }

    public void showDateDialogStart() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(Komisi_sales.this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    TanggalMulaiKomisi.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    String bulan = String.valueOf(monthOfYear + 1);
                    String day = String.valueOf(dayOfMonth);
                    if (bulan.length() == 1) {
                        bulan = "0" + bulan;

                    }
                    if (day.length() == 1) {

                        day = "0" + day;
                    }

                    String tnggl = year + "-" + bulan + "-" + day;
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    public void showDateDialogEnd() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(Komisi_sales.this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    TanggalSelesaiKomisi.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    String bulan = String.valueOf(monthOfYear + 1);
                    String day = String.valueOf(dayOfMonth);
                    if (bulan.length() == 1) {
                        bulan = "0" + bulan;

                    }
                    if (day.length() == 1) {

                        day = "0" + day;
                    }

                    String tnggl = year + "-" + bulan + "-" + day;
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
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

    public void getKomisi(String dateStart, String dateEnd){

        String token = "Bearer "+ Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponSales>call = api.getKomisiSales(token,dateStart,dateEnd);
        call.enqueue(new Callback<ResponSales>() {
            @Override
            public void onResponse(Call<ResponSales> call, Response<ResponSales> response) {
                String code = response.body().getCode();
                if(code.equals("200")){

                    ArrayList<ResponSales.mData> komisi = response.body().getData();
                    int harga =0;

                    for (int i = 0; i < komisi.size(); i++){

                        harga += Integer.parseInt(komisi.get(i).getKomisi());

                    }

                    jumlahKomisi.setText(utils.ConvertRP(String.valueOf(harga)));
                }else {


                    Toast.makeText(getApplicationContext(),"Tidak ada komisi",Toast.LENGTH_SHORT).show();
                    jumlahKomisi.setText(utils.ConvertRP("0"));
                }

            }

            @Override
            public void onFailure(Call<ResponSales> call, Throwable t) {

            }
        });


    }
}