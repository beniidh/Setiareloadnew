package com.c.dompetabata.PulsaPrabayar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.c.dompetabata.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DetailTransaksiPulsaPra extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_detail_transaksi_pulsa_pra,container,false);

        return v;

    }
}