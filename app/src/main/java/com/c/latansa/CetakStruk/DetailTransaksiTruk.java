package com.c.latansa.CetakStruk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.c.latansa.Helper.utils;
import com.c.latansa.R;

import java.io.File;

public class DetailTransaksiTruk extends AppCompatActivity {

    String file_name_path = "";
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {

            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    TextView title;

    TextView idNomorStruk,namaTPC, idProdukStruk, hargastruk, idSaldokuStruk, idTanggalStruk, idWaktuStruk, idNomorSNStruk, idNomorTransaksiStruk, idTotalPembelianStruk;


    Button setHargaJual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi_truk);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Detail Transaksi Struk <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        setHargaJual = findViewById(R.id.setHargaJual);
        namaTPC = findViewById(R.id.namaTPC);
        idNomorStruk = findViewById(R.id.idNomorStruk);
        idProdukStruk = findViewById(R.id.idProdukStruk);
        idSaldokuStruk = findViewById(R.id.idSaldokuStruk);
        idTanggalStruk = findViewById(R.id.idTanggalStruk);
        idWaktuStruk = findViewById(R.id.idWaktuStruk);
        idNomorSNStruk = findViewById(R.id.idNomorSNStruk);
        idNomorTransaksiStruk = findViewById(R.id.idNomorTransaksiStruk);
        idTotalPembelianStruk = findViewById(R.id.idTotalPembelianStruk);
        hargastruk = findViewById(R.id.hargastruk);
        title = findViewById(R.id.titlestruk);
        idNomorStruk.setText(getIntent().getStringExtra("nomor"));
        idProdukStruk.setText(getIntent().getStringExtra("produk"));
        idSaldokuStruk.setText(utils.ConvertRP(getIntent().getStringExtra("harga")));
        idTanggalStruk.setText(getIntent().getStringExtra("tanggal"));
        namaTPC.setText(getIntent().getStringExtra("nama"));
        idWaktuStruk.setText(getIntent().getStringExtra("waktu"));
        idNomorSNStruk.setText(getIntent().getStringExtra("sn"));
        idNomorTransaksiStruk.setText(getIntent().getStringExtra("transaksid"));
        idTotalPembelianStruk.setText(utils.ConvertRP(getIntent().getStringExtra("harga")));
        hargastruk.setText(utils.ConvertRP(getIntent().getStringExtra("harga")));
        title.setText(getIntent().getStringExtra("produk"));

        setHargaJual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpMenuSetHargajual();

            }

        });

        Button cetakPDF = findViewById(R.id.cetakPDF);
        cetakPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DetailTransaksiTruk.this, cetak.class);
                intent.putExtra("nomor", idNomorStruk.getText().toString());
                intent.putExtra("produk", idProdukStruk.getText().toString());
                intent.putExtra("hargajual", idTotalPembelianStruk.getText().toString());
                intent.putExtra("tanggal", idTanggalStruk.getText().toString());
                intent.putExtra("nama", namaTPC.getText().toString());
                intent.putExtra("waktu", idWaktuStruk.getText().toString());
                intent.putExtra("sn", idNomorSNStruk.getText().toString());
                intent.putExtra("title", title.getText().toString());
                intent.putExtra("transaksid", idNomorTransaksiStruk.getText().toString());
                startActivity(intent);

//                createpdf();
            }
        });


        if (!hasPermissions(DetailTransaksiTruk.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(DetailTransaksiTruk.this, PERMISSIONS, PERMISSION_ALL);
        }

        File file = new File(this.getExternalFilesDir(null).getAbsolutePath(), "pdfsdcard_location");
        if (!file.exists()) {
            file.mkdir();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    public void popUpMenuSetHargajual() {

        final AlertDialog dialogBuilder = new AlertDialog.Builder(DetailTransaksiTruk.this).create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.set_hargajual, null);

        EditText editText = (EditText) dialogView.findViewById(R.id.edithargajual);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonCancelhargajual);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonSavehargajual);

        button1.setOnClickListener(view -> dialogBuilder.dismiss());
        button2.setOnClickListener(view -> {
            // DO SOMETHINGS
            String harga = utils.ConvertRP(editText.getText().toString());
            idTotalPembelianStruk.setText(harga);
            dialogBuilder.dismiss();

        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();

    }

}