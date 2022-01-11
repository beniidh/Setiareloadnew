package com.c.setiareload.CetakStruk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.CetakStruk.Epos.AsyncBluetoothEscPosPrint;
import com.c.setiareload.CetakStruk.Epos.AsyncEscPosPrinter;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.R;
import com.c.setiareload.Respon.ResponProfil;
import com.c.setiareload.sharePreference.Preference;
import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class cetak extends AppCompatActivity {

    TextView idNomorStruk,namaTPCC, idProdukStruk, hargastruk, titlestrukC, idTanggalStruk, idWaktuStruk, idNomorSNStruk, idNomorTransaksiStruk, idTotalPembelianStruk;
    Button buttondownloadPDF, cetakStrukPDF;
    int PERMISSION_ALL = 1;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    TextView myLabel;
    String alamat;

    // needed for communication to bluetooth device / network
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    String[] options;
    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;
    String title;

    String[] PERMISSIONS = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    EditText pilihPerangkat;
    private ListPopupWindow serverpopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cetak);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Cetak <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        myLabel = findViewById(R.id.myLabel);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (!hasPermissions(cetak.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(cetak.this, PERMISSIONS, PERMISSION_ALL);
        }
        File file = new File(this.getExternalFilesDir(null).getAbsolutePath(), "pdfsdcard_location");
        if (!file.exists()) {
            file.mkdir();
        }

        buttondownloadPDF = findViewById(R.id.buttondownloadPDF);
        pilihPerangkat = findViewById(R.id.pilihPerangkat);
        cetakStrukPDF = findViewById(R.id.cetakStrukPDF);
        namaTPCC = findViewById(R.id.namaTPCC);

        idNomorStruk = findViewById(R.id.idNomorStrukC);
        idProdukStruk = findViewById(R.id.idProdukStrukC);
        idTanggalStruk = findViewById(R.id.idTanggalStrukC);
        idWaktuStruk = findViewById(R.id.idWaktuStrukC);
        idNomorSNStruk = findViewById(R.id.idNomorSNStrukC);
        idNomorTransaksiStruk = findViewById(R.id.idNomorTransaksiStrukC);
        idTotalPembelianStruk = findViewById(R.id.idTotalPembelianStrukC);
        titlestrukC = findViewById(R.id.titlestrukC);

        idNomorStruk.setText(getIntent().getStringExtra("nomor"));
        namaTPCC.setText(getIntent().getStringExtra("nama"));
        idProdukStruk.setText(getIntent().getStringExtra("produk"));
        idTanggalStruk.setText(getIntent().getStringExtra("tanggal"));
        idWaktuStruk.setText(getIntent().getStringExtra("waktu"));
        idNomorSNStruk.setText(getIntent().getStringExtra("sn"));
        idNomorTransaksiStruk.setText(getIntent().getStringExtra("transaksid"));
        idTotalPembelianStruk.setText(getIntent().getStringExtra("hargajual"));
        title = getIntent().getStringExtra("title");
        getContentProfil();

        buttondownloadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savepdf();

            }
        });

        cetakStrukPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                printBluetooth();
//                try {
//                    openBT();
//                    sendData();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

            }
        });

        pilihPerangkat.setOnClickListener(v -> {
            browseBluetoothDevice();
//            ArrayList<String> Serverid = new ArrayList<>();
//            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//            Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
//
//            if (pairedDevices.size() > 0) {
//
//                for (BluetoothDevice device : pairedDevices) {
//                    Serverid.add(device.getName());
//
//                }
//            }
//
//            if (Serverid.isEmpty()) {
//                Serverid.add("Tidak ada perangkat");
//            }
//
//
//            serverpopup = new ListPopupWindow(cetak.this);
//            ArrayAdapter adapter = new ArrayAdapter<>(cetak.this, R.layout.list_serverid, R.id.tv_serverid, Serverid);
//            serverpopup.setAnchorView(pilihPerangkat); //this let as set the popup below the EditText
//            serverpopup.setAdapter(adapter);
//            serverpopup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    pilihPerangkat.setText(Serverid.get(position));//we set the selected element in the EditText
//
//                    for (BluetoothDevice device : pairedDevices) {
//
//                        if (device.getName().equals(Serverid.get(position))) {
//                            mmDevice = device;
//                            break;
//                        }
//                    }
//                    try {
//                        openBT();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//
//                    serverpopup.dismiss();
//                }
//            });
//            serverpopup.show();


        });
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

    void openBT() throws IOException {
        try {

            // Standard SerialPortService ID
            UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
            mmSocket.connect();
            mmOutputStream = mmSocket.getOutputStream();
            mmInputStream = mmSocket.getInputStream();

            beginListenForData();

            myLabel.setText("Bluetooth Opened");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void beginListenForData() {
        try {
            final Handler handler = new Handler();

            // this is the ASCII code for a newline character
            final byte delimiter = 10;

            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            workerThread = new Thread(new Runnable() {
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker) {

                        try {

                            int bytesAvailable = mmInputStream.available();

                            if (bytesAvailable > 0) {

                                byte[] packetBytes = new byte[bytesAvailable];
                                mmInputStream.read(packetBytes);

                                for (int i = 0; i < bytesAvailable; i++) {

                                    byte b = packetBytes[i];
                                    if (b == delimiter) {

                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer, 0,
                                                encodedBytes, 0,
                                                encodedBytes.length
                                        );

                                        // specify US-ASCII encoding
                                        final String data = new String(encodedBytes, "US-ASCII");
                                        readBufferPosition = 0;

                                        // tell the user data were sent to bluetooth printer device
                                        handler.post(new Runnable() {
                                            public void run() {
                                                myLabel.setText(data);
                                            }
                                        });

                                    } else {
                                        readBuffer[readBufferPosition++] = b;
                                    }
                                }
                            }

                        } catch (IOException ex) {
                            stopWorker = true;
                        }

                    }
                }
            });

            workerThread.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void sendData() throws IOException {
        try {

            // the text typed by the user


            String telepon = idNomorStruk.getText().toString();
            String produk = idProdukStruk.getText().toString();
            String saldo = idTotalPembelianStruk.getText().toString();
            String tanggal = idTanggalStruk.getText().toString();
            String waktu = idWaktuStruk.getText().toString();
            String SN = idNomorSNStruk.getText().toString();
            String nomorTransaksi = idNomorTransaksiStruk.getText().toString();


            String pesanstruk = "[C]<u type='double'>" + titlestrukC.getText().toString().toUpperCase() + "</u>\n"
                    + "----------------------------" + "\n"
                    + "Telepon " + "---- " + telepon + "\n"
                    + "Produk " + "---- " + produk + "\n"
                    + "Harga " + "---- " + saldo + "\n"
                    + "Tanggal " + "---- " + tanggal + "\n"
                    + "waktu " + "---- " + waktu + "\n"
                    + "SN " + "---- " + SN + "\n"
                    + "Transaksi " + "-- " + nomorTransaksi + "\n"
                    + "-----------------------------" + "\n" + "\n";

            mmOutputStream.write(pesanstruk.getBytes());

            // tell the user data were sent
            myLabel.setText("Data sent.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savepdf() {

        Rect bounds = new Rect();
        int pageWidth = 300;
        int pageheight = 330;
        int pathHeight = 2;

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

        String text = titlestrukC.getText().toString();

        paint.getTextBounds(text, 0, text.length(), bounds);
        x = (canvas.getWidth() / 2) - (bounds.width() / 2);
        canvas.drawText(text, x, y, paint);

        Paint paint1 = new Paint();

        paint1.setColor(getColor(R.color.gray4));
        Typeface type2 = ResourcesCompat.getFont(getApplicationContext(), R.font.courierprimereguler);
        paint1.setTypeface(type2);

        int left = 20;

        canvas.drawText("Nomor", left, 90, paint1);
        canvas.drawText(idNomorStruk.getText().toString(), 150, 90, paint1);

        canvas.drawText("Produk", left, 110, paint1);
        canvas.drawText(idProdukStruk.getText().toString(), 150, 110, paint1);

        canvas.drawText("Tanggal", left, 130, paint1);
        canvas.drawText(idTanggalStruk.getText().toString(), 150, 130, paint1);

        canvas.drawText("Waktu", left, 150, paint1);
        canvas.drawText(idWaktuStruk.getText().toString(), 150, 150, paint1);

        canvas.drawText("Nomor SN", left, 170, paint1);
        canvas.drawText(idNomorSNStruk.getText().toString(), 150, 170, paint1);

        canvas.drawText("Nomor Transaksi", left, 190, paint1);
        canvas.drawText(idNomorTransaksiStruk.getText().toString(), 150, 190, paint1);

        canvas.drawText("Total Pembelian", left, 210, paint1);
        canvas.drawText(idTotalPembelianStruk.getText().toString(), 150, 210, paint1);


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


        myPdfDocument.finishPage(documentPage);

        File file = new File(Environment.getExternalStorageDirectory(), idTanggalStruk.getText().toString() + "-" + idNomorTransaksiStruk.getText().toString() + ".pdf");
        try {
            myPdfDocument.writeTo(new FileOutputStream(file));
            Toast.makeText(getApplicationContext(), "File Tersimpan pada internal memory, dengan nama" + idTanggalStruk.getText().toString() + "-" + idNomorTransaksiStruk.getText().toString() + ".pdf", Toast.LENGTH_LONG).show();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void getContentProfil() {

        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(Call<ResponProfil> call, Response<ResponProfil> response) {

                titlestrukC.setText(response.body().getData().getStore_name());
                setAlamat(response.body().getData().getAddress());

            }

            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
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

    private BluetoothConnection selectedDevice;

    public void browseBluetoothDevice() {
        final BluetoothConnection[] bluetoothDevicesList = (new BluetoothPrintersConnections()).getList();

        if (bluetoothDevicesList != null) {
            final String[] items = new String[bluetoothDevicesList.length + 1];
            items[0] = "Default printer";
            int i = 0;
            for (BluetoothConnection device : bluetoothDevicesList) {
                items[++i] = device.getDevice().getName();
            }

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(cetak.this);
            alertDialog.setTitle("Bluetooth printer selection");
            alertDialog.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int index = i - 1;
                    if (index == -1) {
                        selectedDevice = null;
                    } else {
                        selectedDevice = bluetoothDevicesList[index];
                    }

                    pilihPerangkat.setText(items[i]);
                }
            });

            AlertDialog alert = alertDialog.create();
            alert.setCanceledOnTouchOutside(false);
            alert.show();

        }
    }

    public static final int PERMISSION_BLUETOOTH = 1;

    public void printBluetooth() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, cetak.PERMISSION_BLUETOOTH);
        } else {
            new AsyncBluetoothEscPosPrint(this).execute(this.getAsyncEscPosPrinter(selectedDevice));
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case cetak.PERMISSION_BLUETOOTH:
                    this.printBluetooth();
                    break;
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    public AsyncEscPosPrinter getAsyncEscPosPrinter(DeviceConnection printerConnection) {
        SimpleDateFormat format = new SimpleDateFormat("'' yyyy-MM-dd ':' HH:mm:ss");
        AsyncEscPosPrinter printer = new AsyncEscPosPrinter(printerConnection, 203, 48f, 32);
        return printer.setTextToPrint(
                "[C]<u><font size='big'>" + titlestrukC.getText().toString() + "</font></u>\n" +
                        "[C]\n" +
                        "[C]"+"JL "+getAlamat()+"\n"+
                        "[C]<u type='double'>" + format.format(new Date()) + "</u>\n" +
                        "[C]\n" +
                        "[C]<b>STRUK PEMBELIAN<b>\n"+
                        "[C]================================\n" +
                        "[L]\n" +
                        "[L]<b>Nomor</b>[R]" + idNomorStruk.getText().toString() + "\n" +
//                        "[L]\n" +
                        "[L]<b>Nama</b>[R]" + namaTPCC.getText().toString() + "\n" +
                        //"[L]\n" +
                        "[L]<b>Produk</b>[R]" + idProdukStruk.getText().toString() + "\n" +
//                        "[L]\n" +
                        "[L]<b>Transaksi</b>[R]" + idNomorTransaksiStruk.getText().toString() + "\n" +
                        "[C]--------------------------------\n" +
//                        "[L]\n" +
                        "[L]<b>Total Bayar</b>[R]" + idTotalPembelianStruk.getText().toString() + "\n" +
                        "[L]\n" +
                        "[C]"+"SN "+idNomorSNStruk.getText().toString()+"\n"+
                        "[L]\n"

        );
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}