package com.c.latansa.Fragment.RekapsaldoFragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.c.latansa.Api.Api;
import com.c.latansa.Fragment.RekapSaldo.AdapterRekapSaldo;
import com.c.latansa.Fragment.RekapSaldo.responRekap;
import com.c.latansa.Fragment.RekapsaldoFragment.Komponen.DebitFragment;
import com.c.latansa.Fragment.RekapsaldoFragment.Komponen.KreditFragment;
import com.c.latansa.Fragment.RekapsaldoFragment.Komponen.SemuaFragment;
import com.c.latansa.Fragment.RiwayatTransaksi.FragmentSaldoServer;
import com.c.latansa.Fragment.RiwayatTransaksi.FragmentSaldoku;
import com.c.latansa.Fragment.RiwayatTransaksi.TabAdapter;
import com.c.latansa.Helper.RetroClient;
import com.c.latansa.Modal.ModalKelurahan;
import com.c.latansa.R;
import com.c.latansa.sharePreference.Preference;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RekapFragment extends Fragment {

    TabLayout tablayoutRekap;
    ViewPager ViewPagerlayoutTRekap;
    TabAdapter tabAdapter;
    ImageView FilterRekap;
    modalFilter Modalfiltedr;
    ArrayList<responRekap.Data.Item> data = new ArrayList<>();
    ArrayList<responRekap.Data.Item> dataDebit = new ArrayList<>();
    ArrayList<responRekap.Data.Item> dataKredit = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rekap, container, false);

        tablayoutRekap = v.findViewById(R.id.tablayoutRekap);
        ViewPagerlayoutTRekap = v.findViewById(R.id.ViewPagerlayoutTRekap);
        FilterRekap = v.findViewById(R.id.FilterRekap);

        String dateToday = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -3);
        String dateYesterday = dateFormat.format(cal.getTime());

        getDataRekap(dateYesterday, dateToday, "SALDOKU");
        FilterRekap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                modalFilter modalKelurahan = new modalFilter();
                bundle.putString("kecamatankey", "");
                modalKelurahan.setArguments(bundle);
                modalKelurahan.show(getChildFragmentManager(), "Modal Filter");
            }
        });

        getChildFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String resultt = result.getString("bundleKey");
                String tanggalmulai = result.getString("Tanggalmulai");
                String tanggalselesai = result.getString("Tanggalselesai");
                String jenissaldo = result.getString("jenissaldo");
                getDataRekap(tanggalmulai, tanggalselesai, jenissaldo);

            }
        });


        return v;
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
                    dataDebit.clear();
                    dataKredit.clear();
                    for (responRekap.Data.Item datasecond : data) {

                        if (datasecond.getType_nominal().equals("KREDIT")) {

                            dataDebit.add(datasecond);
                        } else {
                            dataKredit.add(datasecond);
                        }
                    }

                } else {
                    data.clear();
                    dataDebit.clear();
                    dataKredit.clear();
                    Toast.makeText(getContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }


                tabAdapter = new TabAdapter(getParentFragmentManager());
                tabAdapter.addFragment(new SemuaFragment(data), "Tab 1");
                tabAdapter.addFragment(new DebitFragment(dataDebit), "Tab 2");
                tabAdapter.addFragment(new KreditFragment(dataKredit), "Tab 3");
                ViewPagerlayoutTRekap.setAdapter(tabAdapter);
                tablayoutRekap.setupWithViewPager(ViewPagerlayoutTRekap);
                tablayoutRekap.getTabAt(0).setText("Semua");
                tablayoutRekap.getTabAt(1).setText("Debit");
                tablayoutRekap.getTabAt(2).setText("Kredit");
            }

            @Override
            public void onFailure(Call<responRekap> call, Throwable t) {
                Toast.makeText(getContext(), "Koneksi tidak stabil", Toast.LENGTH_SHORT).show();

            }
        });

    }
}