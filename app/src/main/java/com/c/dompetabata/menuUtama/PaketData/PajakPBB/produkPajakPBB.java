package com.c.dompetabata.menuUtama.PaketData.PajakPBB;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.c.dompetabata.R;

public class produkPajakPBB extends AppCompatActivity implements ModalPajak.BottomSheetListenerProduksms {

    EditText inputprodukpajak, inputnomorpajak;
    TextView tujukarakterpajak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_pajak_p_b_b);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Pajak PBB <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        inputprodukpajak = findViewById(R.id.inputprodukpajak);
        inputprodukpajak.setFocusable(false);
        inputnomorpajak = findViewById(R.id.inputnomorpajak);
        tujukarakterpajak = findViewById(R.id.tujukarakterpajak);


        inputprodukpajak.setOnClickListener(v -> {
            ModalPajak modalPajak = new ModalPajak();
            modalPajak.show(getSupportFragmentManager(), "Modal pajak");
        });

        inputnomorpajak.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (inputnomorpajak.length() >= 7) {

                    tujukarakterpajak.setVisibility(View.INVISIBLE);
                } else {

                    tujukarakterpajak.setVisibility(View.VISIBLE);
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

    @Override
    public void onButtonClick(String name, String id) {
        inputprodukpajak.setText(name);
    }
}