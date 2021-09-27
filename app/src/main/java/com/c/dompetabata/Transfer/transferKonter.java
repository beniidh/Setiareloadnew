package com.c.dompetabata.Transfer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.c.dompetabata.R;

public class transferKonter extends AppCompatActivity implements ModalTransfer.BottomSheetListenerKabupaten{

    Button buttonTransfer;
    EditText konterid,nominalkonter;
    String id;

 @SuppressLint("StaticFieldLeak")
 static Activity transfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_konter);
        transfer = this;

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Kirim Saldo <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        buttonTransfer = findViewById(R.id.buttonTransfer);
        konterid = findViewById(R.id.konterid);
        nominalkonter = findViewById(R.id.nominalkirim);
        buttonTransfer.setOnClickListener(view -> {

            if(konterid.getText().toString().isEmpty() || nominalkonter.getText().toString().isEmpty()){

                Toast.makeText(getApplicationContext(),"Konter atau nominal tidak boleh kosong",Toast.LENGTH_SHORT).show();
            }else {
                Bundle bundle = new Bundle();
                ModalPinTransfer modalPinTransfer = new ModalPinTransfer();
                bundle.putString("idkonter",getId());
                bundle.putString("nominalkonter",nominalkonter.getText().toString());
                modalPinTransfer.setArguments(bundle);
                modalPinTransfer.show(getSupportFragmentManager(),"transfer");
            }

        });

        konterid.setOnClickListener(view -> {

            ModalTransfer modalTransfer = new ModalTransfer();
            modalTransfer.show(getSupportFragmentManager(),"transfer");

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

    @Override
    public void onButtonClickKabupaten(String name, String id) {
        konterid.setText(name);
        setId(id);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}