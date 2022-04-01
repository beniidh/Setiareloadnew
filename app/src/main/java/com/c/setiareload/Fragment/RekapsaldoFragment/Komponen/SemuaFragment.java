package com.c.setiareload.Fragment.RekapsaldoFragment.Komponen;

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

import com.c.setiareload.Fragment.RekapSaldo.AdapterRekapSaldo;
import com.c.setiareload.Fragment.RekapSaldo.responRekap;
import com.c.setiareload.R;

import java.util.ArrayList;
import java.util.Calendar;


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