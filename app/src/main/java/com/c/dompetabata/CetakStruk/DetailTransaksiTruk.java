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


    public void createpdf() {

        Rect bounds = new Rect();
        int pageWidth = 300;
        int pageheight = 330;
        int pathHeight = 2;

        final String fileName = "mypdf";
        file_name_path = "/pdfsdcard_locatio" + fileName + ".pdf";

        PdfDocument myPdfDocument = new PdfDocument();
        Paint paint = new Paint();
        Paint paint2 = new Paint();
        Path path = new Path();
        PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageheight, 1).create();
        PdfDocument.Page documentPage = myPdfDocument.startPage(myPageInfo);
        Canvas canvas = documentPage.getCanvas();
        int y = 25; // x = 10,
        //int x = (canvas.getWidth() / 2);
        int x = 10;

        String text = " Amadea Cell";

        paint.getTextBounds(text, 0, text.length(), bounds);
        x = (canvas.getWidth() / 2) - (bounds.width() / 2);
        canvas.drawText(text, x, y, paint);

        Paint paint1 = new Paint();

        paint1.setColor(getColor(R.color.gray4));
        Typeface type2 = ResourcesCompat.getFont(getApplicationContext(), R.font.courierprimereguler);
        paint1.setTypeface(type2);

//        //horizontal line
//        path.lineTo(pageWidth, pathHeight);
//        paint2.setColor(Color.GRAY);
//        paint2.setStyle(Paint.Style.STROKE);
//        path.moveTo(x, y);
//        canvas.drawLine(0, y, pageWidth, y, paint2);
        int left = 20;

        canvas.drawText("Telepon", left, 90, paint1);
        canvas.drawText("085788387939", 150, 90, paint1);

        canvas.drawText("Produk", left, 110, paint1);
        canvas.drawText("Pulsa Axis 10.000", 150, 110, paint1);

        canvas.drawText("Saldoku Terpakai", left, 130, paint1);
        canvas.drawText("Rp.10.000", 150, 130, paint1);

        canvas.drawText("Tanggal", left, 150, paint1);
        canvas.drawText("21 May 2021", 150, 150, paint1);

        canvas.drawText("Waktu", left, 170, paint1);
        canvas.drawText("18.35", 150, 170, paint1);

        canvas.drawText("Nomor SN", left, 190, paint1);
        canvas.drawText("0890987654762", 150, 190, paint1);

        canvas.drawText("Nomor Transaksi", left, 210, paint1);
        canvas.drawText("TRX0987654762", 150, 210, paint1);

        canvas.drawText("Total Pembelian", left, 230, paint1);
        canvas.drawText("Rp 11.000", 150, 230, paint1);

        canvas.drawText("Status", left, 250, paint1);
        canvas.drawText("Sukses", 150, 250, paint1);


        //blank space
        y += paint.descent() - paint.ascent();
        canvas.drawText("", x, y, paint);
//
        //horizontal line
        path.lineTo(pageWidth, pathHeight);
        paint2.setColor(Color.GRAY);
        paint2.setStyle(Paint.Style.STROKE);
        path.moveTo(x, y);
        canvas.drawLine(0, y, pageWidth, y, paint2);

        path.lineTo(pageWidth, pathHeight);
        paint2.setColor(Color.GRAY);
        paint2.setStyle(Paint.Style.STROKE);
        path.moveTo(x, y);
        canvas.drawLine(0, 300, pageWidth, 300, paint2);
//
//        //blank space
//        y += paint.descent() - paint.ascent();
//        canvas.drawText("", x, y, paint);


        myPdfDocument.finishPage(documentPage);


        File file = new File(this.getExternalFilesDir(null).getAbsolutePath() + file_name_path);
        try {
            myPdfDocument.writeTo(new FileOutputStream(file));

        } catch (IOException e) {
            e.printStackTrace();
        }

        myPdfDocument.close();
        viewPdfFile();
    }

    public void savePDF() {

        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(300, 400, 1).create();

        // below line is used for setting
        // start page for our PDF file.
        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);


        String directory_path = Environment.getExternalStorageDirectory() + "bismm.pdf";

        pdfDocument.finishPage(myPage);
        File file = new File(Environment.getExternalStorageDirectory(), "bismillah.pdf");
        File dir = new File(getApplicationContext().getFilesDir(), "bismillah.pdf");


        try {

            pdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(getApplicationContext(), "File Tersimpan pada internal memory, dengan nama", Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void viewPdfFile() {


        try {
            File file = new File(this.getExternalFilesDir(null).getAbsolutePath() + file_name_path);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            startActivity(intent);

        } catch (ActivityNotFoundException e) {

            StyleableToast.makeText(getApplicationContext(), "Aplikasi Pembuka Tidak ditemukan", Toast.LENGTH_SHORT, R.style.mytoast2).show();
        }


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

    private String getMacAddress() {
        String MAC = utils.getMACAddress("wlan0");//phone if pc use eth0 if phone wlan0
        return MAC;

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