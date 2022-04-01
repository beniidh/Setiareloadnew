package com.c.setiareload.Modal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.c.setiareload.R;
import com.c.setiareload.SaldoServer.BayarSalesServer;
import com.c.setiareload.SaldoServer.TrasferBankServer;
import com.c.setiareload.TopUpSaldoku.BayarSales;
import com.c.setiareload.Transaksi.BayarViaBank;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ModalMetodePemayaran extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modal_metode_pebayaran, container, false);

        LinearLayout linearSales = v.findViewById(R.id.LinearBayarSales);
        linearSales.setOnClickListener(v1 -> {
            String codebayar = getArguments().getString("saldotipe");

            if (codebayar.equals("saldoserver")) {
                Intent intent = new Intent(getActivity(), BayarSalesServer.class);
                startActivity(intent);
                dismiss();

            } else {
                Intent intent = new Intent(getActivity(), BayarSales.class);
                startActivity(intent);
                dismiss();
            }


        });

        LinearLayout LinearBayarBank = v.findViewById(R.id.LinearBayarBank);
//
//        String codebayar = getArguments().getString("saldotipe");
//
//        if (codebayar.equals("saldoserver")) {
//            LinearBayarBank.setVisibility(View.GONE);
//
//        }

        LinearBayarBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codebayar = getArguments().getString("saldotipe");
                if (codebayar.equals("saldoserver")) {
                    Intent intent = new Intent(getActivity(), TrasferBankServer.class);
                    startActivity(intent);
                    dismiss();

                } else {

                    Intent intent = new Intent(getActivity(), BayarViaBank.class);
                    startActivity(intent);
                    dismiss();
                }
            }
        });

        return v;
    }

}