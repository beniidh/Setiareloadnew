package com.c.latansa.CetakStruk.StrukPLNPra;

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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.latansa.Api.Api;
import com.c.latansa.CetakStruk.Epos.AsyncBluetoothEscPosPrint;
import com.c.latansa.CetakStruk.Epos.AsyncEscPosPrinter;
import com.c.latansa.CetakStruk.cetak;
import com.c.latansa.Helper.RetroClient;
import com.c.latansa.Helper.utils;
import com.c.latansa.R;
import com.c.latansa.Respon.ResponProfil;
import com.c.latansa.sharePreference.Preference;
import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.muddzdev.styleabletoast.StyleableToast;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CetakPlnPra extends AppCompatActivity {

    TextView KonterPR, AlamatPR, WaktuPR, namaPR, tarifPR, DayaPR,
            KWHPR, NominalPR, adminPR, totaltagihanPR, SNPR, nomorPR;
    Button setAdmin, CetakPra;
    EditText pilihPerangkat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cetak_pln_pra);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 + "'><b>Cetak Struk <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        //Button
        setAdmin = findViewById(R.id.setAdmin);
        CetakPra = findViewById(R.id.CetakPra);
        //TextView
        KonterPR = findViewById(R.id.KonterPR);
        AlamatPR = findViewById(R.id.AlamatPR);
        WaktuPR = findViewById(R.id.WaktuPR);

        nomorPR = findViewById(R.id.nomorPR);
        namaPR = findViewById(R.id.namaPR);
        tarifPR = findViewById(R.id.tarifPR);
        DayaPR = findViewById(R.id.DayaPR);
        KWHPR = findViewById(R.id.KWHPR);
        NominalPR = findViewById(R.id.NominalPR);
        adminPR = findViewById(R.id.adminPR);
        totaltagihanPR = findViewById(R.id.totaltagihanPR);
        SNPR = findViewById(R.id.SNPR);
        pilihPerangkat = findViewById(R.id.pilihPerangkat);
        String SN = getIntent().getStringExtra("sn");
        String[] sn = SN.split(",");

        nomorPR.setText("Nomor : " + getIntent().getStringExtra("nomor"));
        namaPR.setText("Nama : " + sn[1].substring(5));
        WaktuPR.setText(getIntent().getStringExtra("waktu2"));
        tarifPR.setText("Tarif/Daya : " + sn[2].substring(5));
        totaltagihanPR.setText("Total Tagihan : " + utils.ConvertRP(getIntent().getStringExtra("harga")));
        NominalPR.setText("Nominal : " + utils.ConvertRP(getIntent().getStringExtra("harga")));
        SNPR.setText("Token : " + sn[0].substring(6));
        KWHPR.setText("KWH : " + sn[3].substring(4));

        setAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popUpMenuSetHargajual();

            }
        });
        CetakPra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printBluetooth();

            }
        });
        pilihPerangkat = findViewById(R.id.pilihPerangkat);
        pilihPerangkat.setOnClickListener(v -> {
            browseBluetoothDevice();

        });


        getContentProfil();
    }

    public void getContentProfil() {

        Api api = RetroClient.getApiServices();
        Call<ResponProfil> call = api.getProfileDas("Bearer " + Preference.getToken(getApplicationContext()));
        call.enqueue(new Callback<ResponProfil>() {
            @Override
            public void onResponse(Call<ResponProfil> call, Response<ResponProfil> response) {

                KonterPR.setText(response.body().getData().getStore_name());
                AlamatPR.setText(response.body().getData().getAddress());

            }

            @Override
            public void onFailure(Call<ResponProfil> call, Throwable t) {
                StyleableToast.makeText(getApplicationContext(), "Periksa Sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast2).show();
            }
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
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                items[++i] = device.getDevice().getName();
            }

            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(CetakPlnPra.this);
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
        AsyncEscPosPrinter printer = new AsyncEscPosPrinter(printerConnection, 203, 48f, 32);
        return printer.setTextToPrint(
                "[C]<u><font size='tall' color ='bg-black'>" + KonterPR.getText().toString() + "</font></u>\n" +
                        "[C]\n" +
                        "[C]" + AlamatPR.getText().toString() + "\n" +
                        "[C]<u type='double'>" + WaktuPR.getText().toString() + "</u>\n" +
                        "[C]\n" +
                        "[C]--------------------------------\n" +
                        "[C]\n" +
                        "[C]" + "<b>Struk Pembayaran PLN Prabayar<b>" + "\n" +
                        "[C]\n" +
                        "[L]Nomor [L]: " + nomorPR.getText().toString().substring(7) + "\n" +
                        "[L]Nama [L]: " + namaPR.getText().toString().substring(7) + "\n" +
                        "[L]Tarif/Daya [L]: " + tarifPR.getText().toString().substring(13) + "\n" +
                        "[L]KwH [L]: " + KWHPR.getText().toString().substring(6) + "\n" +
                        "[L]Nominal" + "[L]: " + NominalPR.getText().toString().substring(9) + "\n" +
                        "[L]Admin" + "[L]:  " + adminPR.getText().toString() + "\n" +

                        "[l]\n" +
                        "[L]<b>Total Bayar<b>" + "[L]: " + totaltagihanPR.getText().toString().substring(15) + "\n" +
                        "[C]--------------------------------\n" +
                        "[C]<font size='tall'>" + SNPR.getText().toString().substring(7) + "</font>" + "\n" +
                        "[C]\n" +
                        "[C]struk ini merupakan bukti\n" +
                        "[C]pembayaran yang SAH\n" +
                        "[C]harap disimpan\n" +
                        "[C]\n" +
                        "[C]" + "<b>---Terimakasih---<b>" + "\n" +
                        "[L]\n"
        );
    }

    public void popUpMenuSetHargajual() {

        final AlertDialog dialogBuilder = new AlertDialog.Builder(CetakPlnPra.this).create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.set_hargaadmin, null);

        EditText editText = (EditText) dialogView.findViewById(R.id.edithargajual);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonCancelhargajual);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonSavehargajual);

        button1.setOnClickListener(view -> dialogBuilder.dismiss());
        button2.setOnClickListener(view -> {
            // DO SOMETHINGS
            String harga = utils.ConvertRP(editText.getText().toString());
            int total = Integer.parseInt(editText.getText().toString()) + Integer.parseInt(getIntent().getStringExtra("harga"));
            adminPR.setText(harga);
            totaltagihanPR.setText("Total Tagihan : " + utils.ConvertRP(String.valueOf(total)));
            dialogBuilder.dismiss();

        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();

    }

}