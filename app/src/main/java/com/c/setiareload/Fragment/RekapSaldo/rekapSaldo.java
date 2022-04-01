package com.c.setiareload.Fragment.RekapSaldo;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.R;
import com.c.setiareload.sharePreference.Preference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class rekapSaldo extends Fragment {
    EditText tanggalstartR, tanggalendR;
    Button ButtonfilterR;
    RecyclerView recyclerView;

    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar3 = Calendar.getInstance();
    AdapterRekapSaldo adapterRekapSaldo;
    ArrayList<responRekap.Data.Item> data = new ArrayList<>();
    Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rekap_saldo, container, false);

        tanggalstartR = v.findViewById(R.id.tanggalstartR);
        tanggalendR = v.findViewById(R.id.tanggalendR);
        ButtonfilterR = v.findViewById(R.id.ButtonfilterR);
        recyclerView = v.findViewById(R.id.reyRekapSaldo);
        spinner = v.findViewById(R.id.pilihType);

        ButtonfilterR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (tanggalstartR.getText().toString().isEmpty() || tanggalendR.getText().toString().isEmpty()) {

                    Toast.makeText(getContext(), "tanggal tidak boleh kosong", Toast.LENGTH_SHORT).show();

                } else {

                    getDataRekap(tanggalstartR.getText().toString(), tanggalendR.getText().toString(), spinner.getSelectedItem().toString());
                }


            }
        });


        adapterRekapSaldo = new AdapterRekapSaldo(getContext(), data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterRekapSaldo);

        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        };
        DatePickerDialog.OnDateSetListener date2 = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar3.set(Calendar.YEAR, year);
            myCalendar3.set(Calendar.MONTH, monthOfYear);
            myCalendar3.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel2();
        };

        tanggalstartR.setOnClickListener(view -> {

            final Calendar myCalendar2 = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(myCalendar2.getTimeInMillis());
            datePickerDialog.show();

        });


        tanggalendR.setOnClickListener(view -> {

            final Calendar myCalendar2 = Calendar.getInstance();
            DatePickerDialog datePickerDialog2 = new DatePickerDialog(getContext(), date2, myCalendar3
                    .get(Calendar.YEAR), myCalendar3.get(Calendar.MONTH),
                    myCalendar3.get(Calendar.DAY_OF_MONTH));
            datePickerDialog2.getDatePicker().setMaxDate(myCalendar2.getTimeInMillis());
            datePickerDialog2.show();


        });


        return v;

    }


    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
        tanggalstartR.setText(sdf.format(myCalendar.getTime()));
    }

    private void updateLabel2() {
        String myFormat2 = "yyyy-MM-dd"; //In which you need put here
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf2 = new SimpleDateFormat(myFormat2);
        tanggalendR.setText(sdf2.format(myCalendar3.getTime()));
    }

    public void getDataRekap(String tanggalStart, String tanggalEnd, String type) {

        String token = "Bearer " + Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        Call<responRekap> call = api.getSaldoRekap(token, tanggalStart, tanggalEnd, type);
        call.enqueue(new Callback<responRekap>() {
            @Override
            public void onResponse(Call<responRekap> call, Response<responRekap> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    data = response.body().getData().getItems();
                } else {
                    data.clear();
                    Toast.makeText(getContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }

                adapterRekapSaldo = new AdapterRekapSaldo(getContext(), data);
                recyclerView.setAdapter(adapterRekapSaldo);
            }

            @Override
            public void onFailure(Call<responRekap> call, Throwable t) {
                Toast.makeText(getContext(), "Koneksi tidak stabil", Toast.LENGTH_SHORT).show();

            }
        });

    }

}