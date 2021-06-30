package com.c.dompetabata.menuUtama.PaketData.PulsaPrabayar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.c.dompetabata.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.NumberFormat;
import java.util.Locale;

public class DetailTransaksiPulsaPra extends BottomSheetDialogFragment {
    TextView total;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_detail_transaksi_pulsa_pra,container,false);

        Button konfirasidetail = v.findViewById(R.id.konfirmasidetail);
        konfirasidetail.setOnClickListener(v12 -> {

            String urllicon = getArguments().getString("urlicon");

            Intent intent = new Intent(getContext(),KonfirmasiPembayaran.class);
            intent.putExtra("hargatotal",total.getText().toString());
            intent.putExtra("urll",urllicon);
            startActivity(intent);
            dismiss();

        });
        Button tutupdetail = v.findViewById(R.id.tutupdetail);
        tutupdetail.setOnClickListener(v1 -> {
            dismiss();
        });

        TextView deskripsi = v.findViewById(R.id.detaildeskripsifragment);
        TextView nomor = v.findViewById(R.id.nomortujuan);
        TextView hargaproduk = v.findViewById(R.id.hargaproduk);
         total = v.findViewById(R.id.totalpembayaran);

        String strtext = getArguments().getString("deskripsi");
        String nomorr = getArguments().getString("nomorr");
        String harga = getArguments().getString("hargga");

        hargaproduk.setText(harga);
        double totall = Double.valueOf(harga);
        Locale localeid = new Locale("in","ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeid);
        total.setText(formatRupiah.format(totall));

        nomor.setText(nomorr);
        deskripsi.setText(strtext);
        return v;

    }
}