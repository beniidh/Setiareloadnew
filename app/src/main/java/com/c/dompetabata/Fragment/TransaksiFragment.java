package com.c.dompetabata.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.c.dompetabata.Fragment.RiwayatTransaksi.FragmentSaldoServer;
import com.c.dompetabata.Fragment.RiwayatTransaksi.FragmentSaldoku;
import com.c.dompetabata.Notifikasi.FragmentPesan;
import com.c.dompetabata.Notifikasi.FragmentTransaksi;
import com.c.dompetabata.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;

public class TransaksiFragment extends Fragment {

    private TransaksiViewModel mViewModel;
    FrameLayout framelayoutnotifikasi;
    TabLayout tablayoutnotifikasi;
    EditText idtransaksiTanggalEditText;
    TextView idTotalTransaksiTextView,idTransaksiSuksesTextView;
    private int mYear, mMonth, mDay, mHour, mMinute;

    public static TransaksiFragment newInstance() {
        return new TransaksiFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.transaksi_fragment, container, false);
        framelayoutnotifikasi = (FrameLayout) v.findViewById(R.id.framelayoutTransaksi);
        tablayoutnotifikasi = (TabLayout) v.findViewById(R.id.tablayoutransaksi);

        idtransaksiTanggalEditText = v.findViewById(R.id.idTransaksiTanggalEditText);
        idTotalTransaksiTextView = v.findViewById(R.id.idTotalTransaksiTextView);
        idTransaksiSuksesTextView = v.findViewById(R.id.idTransaksiSuksesTextView);

        idtransaksiTanggalEditText.setOnClickListener(v1 -> {
showDateDialog();
        });


        FragmentSaldoServer saldoServer = new FragmentSaldoServer();
        FragmentManager fm = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.framelayoutTransaksi, saldoServer);
        fragmentTransaction.commit(); // save the changes

        tablayoutnotifikasi.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()){

                    case 0 :

                        FragmentSaldoServer saldoServer = new FragmentSaldoServer();
                        FragmentManager fm = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.framelayoutTransaksi, saldoServer);
                        fragmentTransaction.commit(); // save the changes
                        break;

                    case 1:
                        FragmentSaldoku saldoku = new FragmentSaldoku();
                        FragmentManager fmm = getParentFragmentManager();
                        FragmentTransaction fragmentTransactionn = fmm.beginTransaction();
                        fragmentTransactionn.replace(R.id.framelayoutTransaksi, saldoku);
                        fragmentTransactionn.commit(); // save the changes
                        break;


                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TransaksiViewModel.class);
        // TODO: Use the ViewModel
    }


    public void showDateDialog(){

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year, monthOfYear, dayOfMonth) -> idtransaksiTanggalEditText.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year), mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        datePickerDialog.show();



    }

}