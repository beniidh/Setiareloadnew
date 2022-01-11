package com.c.setiareload.menuUtama.PaketData.TransaksiHandle;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.c.setiareload.Helper.utils;
import com.c.setiareload.R;
import com.c.setiareload.menuUtama.PaketData.PulsaPrabayar.KonfirmasiPembayaran;
import com.c.setiareload.sharePreference.Preference;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class DetailTransaksiPasca extends BottomSheetDialogFragment {
    TextView total;
    TextView nomor, produk, tagihan, status, harga, deskripsi,AdminP;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_detail_transaksi_pasca, container, false);

        nomor = v.findViewById(R.id.nomortujuanP);
        produk = v.findViewById(R.id.ProdukP);
        tagihan = v.findViewById(R.id.tagihanP);
        status = v.findViewById(R.id.statusP);
        harga = v.findViewById(R.id.hargaP);
        AdminP = v.findViewById(R.id.AdminP);
        deskripsi = v.findViewById(R.id.deskripsiP);

        Button konfirasidetail = v.findViewById(R.id.konfirmasidetaill);
        konfirasidetail.setOnClickListener(v12 -> {

            String urllicon = getArguments().getString("urlicon");
            Intent intent = new Intent(getContext(), KonfirmasiPembayaran.class);
            intent.putExtra("hargatotal", harga.getText().toString());
            intent.putExtra("urll", urllicon);
            Preference.setUrlIcon(getContext(),urllicon);
            intent.putExtra("RefID", getArguments().getString("RefID"));
            intent.putExtra("sku_code",getArguments().getString("sku_code"));
            intent.putExtra("inquiry",getArguments().getString("inquiry"));
            intent.putExtra("nomorr",getArguments().getString("nomorr"));
            startActivity(intent);
            dismiss();

        });
        Button tutupdetail = v.findViewById(R.id.tutupdetaill);
        tutupdetail.setOnClickListener(v1 -> {
            dismiss();
        });


        String deskripsii = getArguments().getString("deskription");
        String nomorr = getArguments().getString("nomorr");
        String hargaa = getArguments().getString("hargga");
        String nama = getArguments().getString("namecustomer");
        String admin = getArguments().getString("admin");
        String statuss = getArguments().getString("status");
        String tagihann = getArguments().getString("tagihan");

        if(statuss.equals("Gagal")){
            konfirasidetail.setEnabled(false);
        }

        nomor.setText(nomorr);
        produk.setText(nama);
        tagihan.setText(tagihann);
        harga.setText(utils.ConvertRP(hargaa));
        status.setText(statuss);
        deskripsi.setText(deskripsii);
        AdminP.setText(admin);

        return v;
    }

}