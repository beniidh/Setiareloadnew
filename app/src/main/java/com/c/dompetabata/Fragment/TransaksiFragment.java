package com.c.dompetabata.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Fragment.RiwayatTransaksi.FragmentSaldoServer;
import com.c.dompetabata.Fragment.RiwayatTransaksi.FragmentSaldoku;
import com.c.dompetabata.Fragment.RiwayatTransaksi.HistoryTransaksi;
import com.c.dompetabata.Fragment.RiwayatTransaksi.ResponTransaksi;
import com.c.dompetabata.Fragment.RiwayatTransaksi.TabAdapter;
import com.c.dompetabata.Helper.LoadingPrimer;
import com.c.dompetabata.Helper.RetroClient;
import com.c.dompetabata.Helper.utils;
import com.c.dompetabata.Notifikasi.FragmentPesan;
import com.c.dompetabata.Notifikasi.FragmentTransaksi;
import com.c.dompetabata.R;
import com.c.dompetabata.sharePreference.Preference;
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
    FrameLayout framelayoutnotifikasi;
    TabLayout tablayoutnotifikasi;
    private ViewPager viewPager;
    TabAdapter tabAdapter;
    EditText idtransaksiTanggalEditText;
    ArrayList<ResponTransaksi.DataTransaksi> datahistory = new ArrayList<>();
    ArrayList<ResponTransaksi.DataTransaksi> datasaldoserver = new ArrayList<>();
    ArrayList<ResponTransaksi.DataTransaksi> datasaldoku = new ArrayList<>();
    TextView idTotalTransaksiTextView, idTransaksiSuksesTextView, idTotalPengeluaranTextView;
    private int mYear, mMonth, mDay, mHour, mMinute;

    public static TransaksiFragment newInstance() {
        return new TransaksiFragment();
    }

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
        Call<ResponTransaksi> call = api.getHistoriTransaksi(token);
        call.enqueue(new Callback<ResponTransaksi>() {
            @Override
            public void onResponse(Call<ResponTransaksi> call, Response<ResponTransaksi> response) {

                int totaltransaksi = 0;
                int totaltransaksisukses = 0;
                double totalpengeluaran = 0;
                String code = response.body().getCode();
                if (code.equals("200")) {

                    datahistory = response.body().getData();



                    for (ResponTransaksi.DataTransaksi dataa : datahistory) {

                        String tanggal = dataa.getUpdated_at().substring(0, 10);
                        if (tanggal.equals(tanggall)) {


                            totaltransaksi += 1;


                        }
                        if (tanggal.equals(tanggall) && dataa.getStatus().equals("SUKSES")) {
                            totaltransaksisukses += 1;
                            double totalpengeluarann = Double.parseDouble(dataa.getTotal_price());
                            totalpengeluaran += totalpengeluarann;
                        }
                        if (tanggal.equals(tanggall) && dataa.getWallet_type().equals("PAYLATTER")) {
                            datasaldoserver.add(dataa);

                        }
                        if (tanggal.equals(tanggall) && dataa.getWallet_type().equals("SALDOKU")) {
                            datasaldoku.add(dataa);

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
                    loadingPrimer.dismissDialog();
                }
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

                    getDataHistory(tnggl);
                }, mYear, mMonth, mDay);

        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();
    }

    @Override
    public void onResume() {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        datahistory.clear();
        datasaldoserver.clear();
        datasaldoku.clear();
        getDataHistory(date);
        idtransaksiTanggalEditText.setText(date);
        super.onResume();
    }
}