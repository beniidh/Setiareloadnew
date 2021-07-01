package com.c.dompetabata.menuUtama.PaketData.VoucherGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.c.dompetabata.R;

public class produkVoucherGame extends AppCompatActivity {

    EditText inputprodukvouchergame,inputnomorvouchergame;
    TextView tujukaraktervouchergame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_voucher_game);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Voucher Game <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        inputprodukvouchergame = findViewById(R.id.inputprodukvouchergame);
        inputprodukvouchergame.setFocusable(false);
        inputnomorvouchergame = findViewById(R.id.inputnomorvouchergame);
        tujukaraktervouchergame = findViewById(R.id.tujukaraktervouchergame);

        inputprodukvouchergame.setOnClickListener(v -> {

            Intent intent = getIntent();
            String id = intent.getStringExtra("id");

            ModalVoucherGame modalVoucherGame = new ModalVoucherGame(id);
            modalVoucherGame.show(getSupportFragmentManager(),"Modal Voucher");

        });

        inputnomorvouchergame.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(inputnomorvouchergame.length() >= 7){
                    tujukaraktervouchergame.setVisibility(View.INVISIBLE);
                } else {
                    tujukaraktervouchergame.setVisibility(View.VISIBLE);
                }

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