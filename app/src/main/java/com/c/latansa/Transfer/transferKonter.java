package com.c.latansa.Transfer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.latansa.Helper.utils;
import com.c.latansa.R;

public class transferKonter extends AppCompatActivity implements ModalTransfer.BottomSheetListenerKabupaten{

    Button buttonTransfer;
    EditText konterid,nominalkonter;
    String id;
    TextView jumlahTransferK;

 @SuppressLint("StaticFieldLeak")
 static Activity transfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_konter);
        transfer = this;

        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Kirim Saldo <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        buttonTransfer = findViewById(R.id.buttonTransfer);
        konterid = findViewById(R.id.konterid);
        jumlahTransferK = findViewById(R.id.jumlahTransferK);
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
        nominalkonter.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(nominalkonter.length()>1){
                            jumlahTransferK.setText(utils.ConvertRP(nominalkonter.getText().toString()));
                        }else {
                            jumlahTransferK.setText("");
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                }
        );

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