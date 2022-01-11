package com.c.setiareload.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Fragment.RekapSaldo.RekapSaldoActivity;
import com.c.setiareload.Fragment.RiwayatTransaksi.FragmentSaldoServer;
import com.c.setiareload.Fragment.RiwayatTransaksi.FragmentSaldoku;
import com.c.setiareload.Fragment.RiwayatTransaksi.ResponTransaksi;
import com.c.setiareload.Fragment.RiwayatTransaksi.TabAdapter;
import com.c.setiareload.Helper.LoadingPrimer;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Helper.utils;
import com.c.setiareload.R;
import com.c.setiareload.sharePreference.Preference;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransaksiFragment extends Fragment {

    private TransaksiViewModel mViewModel;

    TabLayout tablayoutnotifikasi;
    private ViewPager viewPager;
    TabAdapter tabAdapter;
    Button RekapSaldo;
    EditText idtransaksiTanggalEditText;
    ArrayList<ResponTransaksi.DataTransaksi> datahistory = new ArrayList<>();
    ArrayList<ResponTransaksi.DataTransaksi> datasaldoserver = new ArrayList<>();
    ArrayList<ResponTransaksi.DataTransaksi> datasaldoku = new ArrayList<>();
    TextView idTotalTransaksiTextView, idTransaksiSuksesTextView, idTotalPengeluaranTextView;
    private int mYear, mMonth, mDay, mHour, mMinute;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.transaksi_fragment, container, false);

//        framelayoutnotifikasi = (FrameLayout) v.findViewById(R.id.framelayoutTransaksi);
        tablayoutnotifikasi = (TabLayout) v.findViewById(R.id.tablayoutransaksi);
        viewPager = v.findViewById(R.id.ViewPagerlayoutTransaksi);
        idtransaksiTanggalEditText = v.findViewById(R.id.idTransaksiTanggalEditText);
        idTotalTransaksiTextView = v.findViewById(R.id.idTotalTransaksiTextView);
        idTransaksiSuksesTextView = v.findViewById(R.id.idTransaksiSuksesTextView);
        idTotalPengeluaranTextView = v.findViewById(R.id.idTotalPengeluaranTextView);
        RekapSaldo = v.findViewById(R.id.RekapSaldo);
        RekapSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RekapSaldoActivity.class);
                startActivity(intent);
            }
        });

        idtransaksiTanggalEditText.setOnClickListener(v1 -> {
            showDateDialog();
        });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TransaksiViewModel.class);
        // TODO: Use the ViewModel
    }

    public void getDataHistory(String tanggall) {
        LoadingPrimer loadingPrimer = new LoadingPrimer(getActivity());
        loadingPrimer.startDialogLoading();
        String token = "Bearer " + Preference.getToken(getContext());
        Api api = RetroClient.getApiServices();
        Call<ResponTransaksi> call = api.getHistoriTransaksi(token, tanggall);
        call.enqueue(new Callback<ResponTransaksi>() {
            @Override
            public void onResponse(Call<ResponTransaksi> call, Response<ResponTransaksi> response) {

                int totaltransaksi = 0;
                int totaltransaksisukses = 0;
                double totalpengeluaran = 0;
                String code = response.body().getCode();
                if (code.equals("200")) {

                    datahistory = response.body().getData();
                    datasaldoku.clear();
                    datasaldoserver.clear();

                    for (ResponTransaksi.DataTransaksi dataa : datahistory) {

                        totaltransaksi += 1;

                        if (dataa.getStatus().equals("SUKSES")) {
                            totaltransaksisukses += 1;
                            double totalpengeluarann = Double.parseDouble(dataa.getTotal_price());
                            totalpengeluaran += totalpengeluarann;
                        }
                        if (dataa.getWallet_type().equals("PAYLATTER")) {
                            datasaldoserver.add(dataa);
                        }
                        if (dataa.getWallet_type().equals("SALDOKU")) {
                            datasaldoku.add(dataa);

                        }


                    }
                    loadingPrimer.dismissDialog();


                } else {
                    Toast.makeText(getContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                    datahistory.clear();
                    datasaldoku.clear();
                    datasaldoserver.clear();
                    loadingPrimer.dismissDialog();

                }
                tabAdapter = new TabAdapter(getParentFragmentManager());
                tabAdapter.addFragment(new FragmentSaldoku(datasaldoku), "Tab 1");
                tabAdapter.addFragment(new FragmentSaldoServer(datasaldoserver), "Tab 2");
                viewPager.setAdapter(tabAdapter);
                tablayoutnotifikasi.setupWithViewPager(viewPager);
                tablayoutnotifikasi.getTabAt(0).setText("Saldoku");
                tablayoutnotifikasi.getTabAt(1).setText("Saldo Server");


                idTotalTransaksiTextView.setText(String.valueOf(totaltransaksi));
                idTransaksiSuksesTextView.setText(String.valueOf(totaltransaksisukses));
                String total = String.valueOf(totalpengeluaran);
                idTotalPengeluaranTextView.setText(utils.ConvertRP(total));

            }

            @Override
            public void onFailure(Call<ResponTransaksi> call, Throwable t) {
                loadingPrimer.dismissDialog();

            }
        });

    }


    public void showDateDialog() {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    idtransaksiTanggalEditText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    String bulan = String.valueOf(monthOfYear + 1);
                    String day = String.valueOf(dayOfMonth);
                    if (bulan.length() == 1) {
                        bulan = "0" + bulan;

                    }
                    if (day.length() == 1) {

                        day = "0" + day;
                    }

                    String tnggl = year + "-" + bulan + "-" + day;
                    datahistory.clear();
                    getDataHistory(tnggl);
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        idtransaksiTanggalEditText.setText(date);
        getDataHistory(date);
        getDataHistory(date);
        datahistory.clear();
    }
}