package com.c.latansa.konter.KirimSaldo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.latansa.R;
import com.c.latansa.TopUpSaldoku.NumberChageLive;
import com.c.latansa.Transfer.ModalPinTransfer;

public class TransferSaldo extends AppCompatActivity {

    EditText nominalkirimSaldo;
    Button buttonTransferSaldo;
    static Activity transfersaldo;
    TextView namakirim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_saldo);
        transfersaldo = this;

        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 + "'><b>Kirim Saldo <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        nominalkirimSaldo = findViewById(R.id.nominalkirimSaldo);
        namakirim = findViewById(R.id.namakirim);
        buttonTransferSaldo = findViewById(R.id.buttonTransferSaldo);
        String id = getIntent().getStringExtra("id");
        namakirim.setText("Kirim saldo ke : "+getIntent().getStringExtra("namakonter"));

        nominalkirimSaldo.addTextChangedListener(new NumberChageLive(nominalkirimSaldo));

        buttonTransferSaldo.setOnClickListener(view -> {

            if (nominalkirimSaldo.getText().toString().isEmpty()) {

                Toast.makeText(getApplicationContext(), "Konter atau nominal tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                Bundle bundle = new Bundle();
                ModalPinTransfers modalPinTransfer = new ModalPinTransfers();
                bundle.putString("idkonter", id);
                bundle.putString("nominalkonter", nominalkirimSaldo.getText().toString().replaceAll(",", ""));
                modalPinTransfer.setArguments(bundle);
                modalPinTransfer.show(getSupportFragmentManager(), "transfer");
            }

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
}