package com.c.latansa.Fragment.RekapsaldoFragment.Komponen;

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

import com.c.latansa.Api.Api;
import com.c.latansa.Fragment.RekapSaldo.AdapterRekapSaldo;
import com.c.latansa.Fragment.RekapSaldo.RekapSaldoActivity;
import com.c.latansa.Fragment.RekapSaldo.responRekap;
import com.c.latansa.Helper.RetroClient;
import com.c.latansa.R;
import com.c.latansa.sharePreference.Preference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SemuaFragment extends Fragment {
    EditText tanggalstartR, tanggalendR;
    Button ButtonfilterR;
    RecyclerView recyclerView;

    final Calendar myCalendar = Calendar.getInstance();
    final Calendar myCalendar3 = Calendar.getInstance();
    AdapterRekapSaldo adapterRekapSaldo;
    ArrayList<responRekap.Data.Item> data = new ArrayList<>();
    Spinner spinner;

    public SemuaFragment(ArrayList<responRekap.Data.Item> data) {
        this.data = data;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_semua, container, false);

        tanggalstartR = v.findViewById(R.id.tanggalstartR);
        tanggalendR =  v.findViewById(R.id.tanggalendR);
        ButtonfilterR =  v.findViewById(R.id.ButtonfilterR);
        recyclerView = v.findViewById(R.id.reyRekapSaldo);
        spinner = v.findViewById(R.id.pilihType);


        adapterRekapSaldo = new AdapterRekapSaldo(getContext(), data);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterRekapSaldo);




        return v;
    }



}