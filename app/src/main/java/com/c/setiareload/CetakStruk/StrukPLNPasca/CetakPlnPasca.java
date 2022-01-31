package com.c.setiareload.CetakStruk.StrukPLNPasca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.CetakStruk.DetailTransaksiTruk;
import com.c.setiareload.CetakStruk.Epos.AsyncBluetoothEscPosPrint;
import com.c.setiareload.CetakStruk.Epos.AsyncEscPosPrinter;
import com.c.setiareload.CetakStruk.cetak;
import com.c.setiareload.Helper.LoadingPrimer;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Helper.utils;
import com.c.setiareload.R;
import com.c.setiareload.Respon.ResponProfil;
import com.c.setiareload.menuUtama.PaketData.PulsaPrabayar.TransaksiPending;
import com.c.setiareload.sharePreference.Preference;
import com.dantsu.escposprinter.EscPosPrinter;
import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.muddzdev.styleabletoast.StyleableToast;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CetakPlnPasca extends AppCompatActivity {

    private final Locale locale = new Locale("id", "ID");
    private final NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
    Button setAdmin,CetakPasca;
    EditText pilihPerangkat;

    TextView KonterPS, AlamatPS, namaPS, TagihanPS,
            jumlahBulanPS, TarifDayaPS, jmlhTagihanPS, SMPS, adminPS,
            totaltagihanPS, RefPS, WaktuPS;
    LoadingPrimer loadingPrimer = new LoadingPrimer(CetakPlnPasca.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cetak_pln_pasca);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Cetak Struk <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);


        //Button
        setAdmin = findViewById(R.id.setAdmin);
        CetakPasca = findViewById(R.id.CetakPasca);
        //TextView
        KonterPS = findViewById(R.id.KonterPS);
        AlamatPS = findViewById(R.id.AlamatPS);
        WaktuPS = findViewById(R.id.WaktuPS);

        namaPS = findViewById(R.id.namaPS);
        TagihanPS = findViewById(R.id.TagihanPS);
        jumlahBulanPS = findViewById(R.id.jumlahBulanPS);
        TarifDayaPS = findViewById(R.id.TarifDayaPS);
        jmlhTagihanPS = findViewById(R.id.jmlhTagihanPS);
        SMPS = findViewById(R.id.SMPS);
        adminPS = findViewById(R.id.adminPS);
        totaltagihanPS = findViewById(R.id.totaltagihanPS);
        RefPS = findViewById(R.id.RefPS);

        namaPS.setText("Nama : " + getIntent().getStringExtra("nama"));
        WaktuPS.setText(getIntent().getStringExtra("waktu2"));
        String SN = getIntent().getStringExtra("sn");
        totaltagihanPS.setText("Total Tagihan : " + utils.ConvertRP(getIntent().getStringExtra("harga")));
        jmlhTagihanPS.setText("Tagihan : " + utils.ConvertRP(getIntent().getStringExtra("harga")));
        String [] sn = SN.split(",");
        TarifDayaPS.setText("Tarif Daya :"+sn[3].substring(3));
        namaPS.setText(sn[0]);
        TagihanPS.setText("Periode Tagihan :"+sn[1].substring(5));
        jumlahBulanPS.setText("Jumlah Bulan :"+sn[2].substring(5));
        SMPS.setText("Stand Meter :"+sn[4].substring(3));
        RefPS.setText(sn[8]);

        getContentProfil();

        setAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpMenuSetHargajual();

            }
        });
        CetakPasca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printBluetooth();

            }
        });
        pilihPerangkat = findViewById(R.id.pilihPerangkat);
        pilihPerangkat.setOnClickListener(v -> {
            browseBluetoothDevice();

        });
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

            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(CetakPlnPasca.this);
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
//
                    pilihPerangkat.setText(items[i]);
                }
            });

            android.app.AlertDialog alert = alertDialog.create();
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
    @SuppressLint("SimpleDateFormat")
    public AsyncEscPosPrinter getAsyncEscPosPrinter(DeviceConnection printerConnection) {
        SimpleDateFormat format = new SimpleDateFormat("'' yyyy-MM-dd ':' HH:mm:ss");
        AsyncEscPosPrinter printer = new AsyncEscPosPrinter(printerConnection, 203, 48f, 32);
        return printer.setTextToPrint(
                "[C]<u><font size='tall' color ='bg-black'>" + KonterPS.getText().toString() + "</font></u>\n" +
                        "[C]\n" +
                        "[C]" + AlamatPS.getText().toString() + "\n" +
                        "[C]<u type='double'>" + WaktuPS.getText().toString() + "</u>\n" +
                        "[C]\n" +
                        "[C]--------------------------------\n" +
                        "[C]\n" +
                        "[C]" + "<b>Struk Pembayaran PLN Pasca<b>" + "\n" +
                        "[C]\n" +
                        "[L]Produk [L]: PLN PASCA" + "\n" +
                        "[L]Nama [L]: "+namaPS.getText().toString().substring(6) + "\n" +
                        "[L]Periode TAG [L]: "+TagihanPS.getText().toString().substring(18) + "\n" +
                        "[L]Jumlah BLN [L]:"+jumlahBulanPS.getText().toString().substring(14) + "\n" +
                        "[L]Tarif Daya [L]:"+TarifDayaPS.getText().toString().substring(12)+ "\n" +
                        "[L]Stand Meter [L]:"+SMPS.getText().toString().substring(13) + "\n" +
                        "[L]Tagihan" + "[L]: " + jmlhTagihanPS.getText().toString().substring(10) + "\n" +
                        "[L]Admin" + "[L]: " + adminPS.getText().toString()+ "\n" +

                        "[l]\n" +
                        "[L]<b>Total Bayar<b>" + "[L]: " + totaltagihanPS.getText().toString().substring(15) + "\n" +
                        "[C]--------------------------------\n" +
                        "[l]"+RefPS.getText().toString()+"\n" +
                        "[C]\n" +
                        "[C]struk ini merupakan bukti\n" +
                        "[C]pembayaran yang SAH\n" +
                        "[C]harap disimpan\n" +
                        "[C]\n" +
                        "[C]" + "<b>---Terimakasih---<b>" + "\n" +
                        "[L]\n"
        );
    }


    public void doPrint() {

        try {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, cetak.PERMISSION_BLUETOOTH);
            } else {
                BluetoothConnection connection = BluetoothPrintersConnections.selectFirstPaired();
                loadingPrimer.startDialogLoading();
                if (connection != null) {
                    EscPosPrinter printer = new EscPosPrinter(connection, 203, 48f, 32);
                    final String text =
                            "[C]<u><font size='tall' color ='bg-black'>" + namaPS.getText().toString() + "</font></u>\n" +
                                    "[C]\n" +
                                    "[C]" + KonterPS.getText().toString() + "\n" +
                                    "[C]<u type='double'>" + WaktuPS.getText().toString() + "</u>\n" +
                                    "[C]\n" +
                                    "[C]--------------------------------\n" +
                                    "[C]\n" +
                                    "[C]" + "<b>Struk Pembayaran PLN Pasca<b>" + "\n" +
                                    "[C]\n" +
                                    "[L]Produk [L]: PLN PASCA" + "\n" +
                                    "[L]Nama [L]: +"+namaPS.getText().toString().substring(5) + "\n" +
                                    "[L]Periode TAG [L]:"+TagihanPS.getText().toString().substring(16) + "\n" +
                                    "[L]Jumlah BLN [L]:"+jumlahBulanPS.getText().toString().substring(5) + "\n" +
                                    "[L]Tarif Daya [L]:"+TarifDayaPS.getText().toString().substring(12)+ "\n" +
                                    "[L]Stand Meter [L]:"+SMPS.getText().toString().substring(13) + "\n" +
                                    "[L]Tagihan" + "[L]: " + jmlhTagihanPS.getText().toString().substring(8) + "\n" +
                                    "[L]Admin" + "[L]: " + adminPS.getText().toString().substring(6) + "\n" +

                                    "[l]\n" +
                                    "[L]<b>Total Bayar<b>" + "[L]: " + totaltagihanPS.getText().toString().substring(15) + "\n" +
                                    "[C]--------------------------------\n" +
                                    "[l]"+RefPS.getText().toString()+"\n" +
                                    "[C]\n" +
                                    "[C]struk ini merupakan bukti\n" +
                                    "[C]pembayaran yang SAH\n" +
                                    "[C]harap disimpan\n" +
                                    "[C]\n" +
                                    "[C]" + "<b>---Terimakasih---<b>" + "\n" +
                                    "[L]\n";

                    printer.printFormattedText(text);
                    Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show();

                } else {
                    loadingPrimer.dismissDialog();
                    Toast.makeText(this, "Tidak Bisa Menghubungkan ,Coba Lagi!", Toast.LENGTH_SHORT).show();

                }

            }
        } catch (Exception e) {
            Log.e("APP", "Can't print", e);
        }
    }

    public void popUpMenuSetHargajual() {

        final AlertDialog dialogBuilder = new AlertDialog.Builder(CetakPlnPasca.this).create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.set_hargaadmin, null);

        EditText editText = (EditText) dialogView.findViewById(R.id.edithargajual);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonCancelhargajual);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonSavehargajual);

        button1.setOnClickListener(view -> dialogBuilder.dismiss());
        button2.setOnClickListener(view -> {
            // DO SOMETHINGS
            String harga = utils.ConvertRP(editText.getText().toString());
            int total = Integer.parseInt(editText.getText().toString()) +Integer.parseInt(getIntent().getStringExtra("harga"));
            adminPS.setText(harga);
            totaltagihanPS.setText("Total Tagihan : "+utils.ConvertRP(String.valueOf(total)));
            dialogBuilder.dismiss();

        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();

    }



    public void doPrintPra(View view) {
        SimpleDateFormat format = new SimpleDateFormat(" yyyy-MM-dd ':' HH:mm:ss");
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.BLUETOOTH}, cetak.PERMISSION_BLUETOOTH);
            } else {
                BluetoothConnection connection = BluetoothPrintersConnections.selectFirstPaired();
                if (connection != null) {
                    EscPosPrinter printer = new EscPosPrinter(connection, 203, 48f, 32);
                    final String text =
                            "[C]<u><font size='tall' color ='bg-black'>" + "Garuntang Cell" + "</font></u>\n" +
                                    "[C]\n" +
                                    "[C]" + "Jl. Ir Sutami KM 18" + "\n" +
                                    "[C]<u type='double'>" + format.format(new Date()) + "</u>\n" +
                                    "[C]\n" +
                                    "[C]--------------------------------\n" +
                                    "[C]\n" +
                                    "[C]" + "<b>Struk Pembayaran PLN Prabayar<b>" + "\n" +
                                    "[C]\n" +
                                    "[L]Nama [L]: IKHWANUDIN" + "\n" +
                                    "[L]Nominal [L]: 200.000" + "\n" +
                                    "[L]Tarif [L]: R1" + "\n" +
                                    "[L]Daya [L]: 3500" + "\n" +
                                    "[L]KWH [L]: 66" + "\n" +
                                    "[L]Harga" + "[L]: " + nf.format(54000) + "\n" +
                                    "[L]Admin" + "[L]: " + nf.format(2000) + "\n" +

                                    "[l]\n" +
                                    "[L]<b>Total Bayar<b>" + "[L]: " + nf.format(56000) + "\n" +
                                    "[C]--------------------------------\n" +
                                    "[C]SN: 2467-5885-8160-1665-5801\n" +
                                    "[C]\n" +
                                    "[C]struk ini merupakan bukti\n" +
                                    "[C]pembayaran yang SAH\n" +
                                    "[C]harap disimpan\n" +
                                    "[C]\n" +
                                    "[C]" + "<b>---Terimakasih---<b>" + "\n" +
                                    "[L]\n";

                    printer.printFormattedText(text);
                } else {
                    Toast.makeText(this, "No printer was connected!", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Log.e("APP", "Can't print", e);
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

    public void getContentProfil() {

        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(Call<ResponProfil> call, Response<ResponProfil> response) {

                KonterPS.setText(response.body().getData().getStore_name());
                AlamatPS.setText(response.body().getData().getAddress());

            }

            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
        });
    }
}