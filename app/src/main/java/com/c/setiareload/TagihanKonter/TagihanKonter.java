package com.c.setiareload.TagihanKonter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.R;
import com.c.setiareload.sharePreference.Preference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.slybeaver.slycalendarview.SlyCalendarDialog;

public class TagihanKonter extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ResponTagihanKonter.mData> mdata = new ArrayList<>();
    AdapterTagihanKonter adapterTagihanKonter;
    EditText TanggalFilter;
    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar2 = Calendar.getInstance();
    String myFormat = "yyyy-MM-dd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tagihan_konter);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Tagihan Konter<b></font>"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        recyclerView = findViewById(R.id.ReyTagihanKonter);

        TanggalFilter = findViewById(R.id.TanggalFilter);
        adapterTagihanKonter = new AdapterTagihanKonter(getApplicationContext(), mdata);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterTagihanKonter);

        UpdateData();


        SlyCalendarDialog.Callback callback = new SlyCalendarDialog.Callback() {
            @Override
            public void onCancelled() {
            }

            @Override
            public void onDataSelected(Calendar firstDate, Calendar secondDate, int hours, int minutes) {

                if (firstDate == null || secondDate == null) {
                    Toast.makeText(getApplicationContext(), "Silahkan Pilih rentang tanggal", Toast.LENGTH_SHORT).show();
                } else {
                    String TanggalAwal = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(firstDate.getTime());
                    String TanggalAkhir = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(secondDate.getTime());
                    getDataTagihan(TanggalAwal, TanggalAkhir);
                    TanggalFilter.setText(TanggalAwal + " To " + TanggalAkhir);
                }
            }
        };

        TanggalFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SlyCalendarDialog()
                        .setSingle(false)
                        .setHeaderColor(getColor(R.color.green))
                        .setCallback(callback)
                        .show(getSupportFragmentManager(), "TAG_SLYCALENDAR");
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void UpdateData() {
        myCalendar2.add(Calendar.DAY_OF_YEAR, -1);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        getDataTagihan(sdf.format(myCalendar2.getTime()), sdf.format(myCalendar.getTime()));
        TanggalFilter.setText(sdf.format(myCalendar2.getTime()) + " To " + sdf.format(myCalendar.getTime()));

        myCalendar2.add(Calendar.DAY_OF_YEAR, +1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        UpdateData();
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

    public void getDataTagihan(String tanggalAwal, String TanggalAkhir) {

        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponTagihanKonter> call = api.getTagihanSales(token, tanggalAwal, TanggalAkhir);
        call.enqueue(new Callback<ResponTagihanKonter>() {
            @Override
            public void onResponse(Call<ResponTagihanKonter> call, Response<ResponTagihanKonter> response) {

                String code = response.body().getCode();
                if (code.equals("200")) {
                    mdata = response.body().getData();
                } else {
                    mdata.clear();
                    Toast.makeText(getApplicationContext(), "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
                adapterTagihanKonter = new AdapterTagihanKonter(getApplicationContext(), mdata);
                recyclerView.setAdapter(adapterTagihanKonter);
            }

            @Override
            public void onFailure(Call<ResponTagihanKonter> call, Throwable t) {

            }
        });

    }
}