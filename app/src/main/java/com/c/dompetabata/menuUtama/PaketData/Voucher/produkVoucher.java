package com.c.dompetabata.menuUtama.PaketData.Voucher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.c.dompetabata.R;

public class produkVoucher extends AppCompatActivity {

    EditText inputprodukvoucher,inputnomorvoucher;
    TextView tujukaraktervoucher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_voucher);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Voucher <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        inputnomorvoucher = findViewById(R.id.inputnomorvoucher);
        inputprodukvoucher = findViewById(R.id.inputprodukvoucher);
        inputprodukvoucher.setFocusable(false);
        tujukaraktervoucher = findViewById(R.id.tujukaraktervoucher);

        inputprodukvoucher.setOnClickListener(v -> {

            ModalVoucher modalVoucher = new ModalVoucher();
            modalVoucher.show(getSupportFragmentManager(),"Modal Voucher");
        });

        inputnomorvoucher.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                if(inputnomorvoucher.length() >= 7){
                    tujukaraktervoucher.setVisibility(View.INVISIBLE);
                } else {

                    tujukaraktervoucher.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

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