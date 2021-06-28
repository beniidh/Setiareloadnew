package com.c.dompetabata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.c.dompetabata.Modal.ModalKelurahan;
import com.c.dompetabata.Modal.ModalMetodePemayaran;
import com.c.dompetabata.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;

public class topup_saldoku_activity extends AppCompatActivity {

    Button bayarsaldoku;
    LinearLayout linsaldo100,linsaldo200,linsaldo500,linsaldo1000;
    EditText isisaldoku;
    TextView saldosaatini;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup_saldoku_activity);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Top UP Saldoku <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        linsaldo100 = findViewById(R.id.linsaldo100);
        linsaldo200 = findViewById(R.id.linsaldo200);
        saldosaatini = findViewById(R.id.saldosaatini);
        isisaldoku = findViewById(R.id.isisaldoku);
        linsaldo500 = findViewById(R.id.linsaldo500);
        linsaldo1000 = findViewById(R.id.linsaldo1000);
        Preference.getSharedPreference(getApplicationContext());
        bayarsaldoku = findViewById(R.id.bayarsaldoku);
        Intent intent = getIntent();
        String saldosaatin = intent.getStringExtra("saldoku");
        saldosaatini.setText("Rp."+saldosaatin);

        bayarsaldoku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String saldoku = isisaldoku.getText().toString();

                if(saldoku.equals("")){
                    StyleableToast.makeText(getApplicationContext(),"Jumlah Saldo Masi Kosong", Toast.LENGTH_LONG,R.style.mytoast2).show();
                } else {
                    ModalMetodePemayaran modalPembayaran = new ModalMetodePemayaran();
                    modalPembayaran.show(getSupportFragmentManager(), "ModalPebayaran");
                }

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

    public void onclickLin(View view){
        switch (view.getId()){

            case R.id.linsaldo100 :
                isisaldoku.setText("100000");
                Preference.setSaldoku(getApplicationContext(),"100000");
                linsaldo100.setBackground(getDrawable(R.drawable.bg_saldoku_choose));
                linsaldo200.setBackground(getDrawable(R.drawable.bg_saldo));
                linsaldo500.setBackground(getDrawable(R.drawable.bg_saldo));
                linsaldo1000.setBackground(getDrawable(R.drawable.bg_saldo));
                break;
            case R.id.linsaldo200 :
                isisaldoku.setText("200000");
                Preference.setSaldoku(getApplicationContext(),"200000");
                linsaldo200.setBackground(getDrawable(R.drawable.bg_saldoku_choose));
                linsaldo100.setBackground(getDrawable(R.drawable.bg_saldo));
                linsaldo500.setBackground(getDrawable(R.drawable.bg_saldo));
                linsaldo1000.setBackground(getDrawable(R.drawable.bg_saldo));
                break;
            case R.id.linsaldo500 :
                isisaldoku.setText("500000");
                Preference.setSaldoku(getApplicationContext(),"500000");
                linsaldo500.setBackground(getDrawable(R.drawable.bg_saldoku_choose));
                linsaldo100.setBackground(getDrawable(R.drawable.bg_saldo));
                linsaldo200.setBackground(getDrawable(R.drawable.bg_saldo));
                linsaldo1000.setBackground(getDrawable(R.drawable.bg_saldo));
                break;
            case R.id.linsaldo1000 :
                isisaldoku.setText("1000000");
                Preference.setSaldoku(getApplicationContext(),"1000000");
                linsaldo1000.setBackground(getDrawable(R.drawable.bg_saldoku_choose));
                linsaldo100.setBackground(getDrawable(R.drawable.bg_saldo));
                linsaldo200.setBackground(getDrawable(R.drawable.bg_saldo));
                linsaldo500.setBackground(getDrawable(R.drawable.bg_saldo));
        }


    }
}