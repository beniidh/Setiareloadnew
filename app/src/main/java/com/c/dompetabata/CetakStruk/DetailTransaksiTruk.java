package com.c.dompetabata.CetakStruk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.dompetabata.Helper.utils;
import com.c.dompetabata.R;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.spec.PSSParameterSpec;

public class DetailTransaksiTruk extends AppCompatActivity {

    String file_name_path = "";
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {

            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    TextView title;

    TextView idNomorStruk, idProdukStruk, hargastruk, idSaldokuStruk, idTanggalStruk, idWaktuStruk, idNomorSNStruk, idNomorTransaksiStruk, idTotalPembelianStruk;


    Button setHargaJual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_transaksi_truk);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Detail Transaksi Struk <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        setHargaJual = findViewById(R.id.setHargaJual);
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