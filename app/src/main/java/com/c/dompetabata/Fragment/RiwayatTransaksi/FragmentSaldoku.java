package com.c.dompetabata.Fragment.RiwayatTransaksi;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.c.dompetabata.CetakStruk.Cetakstruk;
import com.c.dompetabata.CetakStruk.DetailTransaksiTruk;
import com.c.dompetabata.R;

public class FragmentSaldoku extends Fragment {

    private FragmentSaldokuViewModel mViewModel;

    public static FragmentSaldoku newInstance() {
        return new FragmentSaldoku();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_saldoku_fragment, container, false);

        LinearLayout lintest = v.findViewById(R.id.lintest);
        lintest.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), HistoryTransaksi.class);
            startActivity(intent);

        });

        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentSaldokuViewModel.class);
        // TODO: Use the ViewModel
    }

    public void getCetakDetaill(View v){


    }

}