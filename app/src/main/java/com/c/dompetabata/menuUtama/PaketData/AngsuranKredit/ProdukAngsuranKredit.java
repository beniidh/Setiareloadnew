package com.c.dompetabata.menuUtama.PaketData.AngsuranKredit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.c.dompetabata.R;

public class ProdukAngsuranKredit extends AppCompatActivity implements ModalAngsuran.BottomSheetListenerProduksms {

    EditText inputprodukangsuran,inputnomorangsuran;
    TextView tujukarakterangsuran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_angsuran_kredit);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Angsuran Kredit <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        inputprodukangsuran = findViewById(R.id.inputprodukangsuran);
        inputprodukangsuran.setFocusable(false);

        inputprodukangsuran.setOnClickListener(v -> {
            ModalAngsuran modalAngsuran = new ModalAngsuran();
            modalAngsuran.show(getSupportFragmentManager(),"modal angsuran");
        });

        inputnomorangsuran = findViewById(R.id.inputnomorangsuran);
        tujukarakterangsuran = findViewById(R.id.tujukarakterangsuran);

        inputnomorangsuran.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(inputnomorangsuran.length() >= 7){
                    tujukarakterangsuran.setVisibility(View.INVISIBLE);
                } else {
                    tujukarakterangsuran.setVisibility(View.VISIBLE);
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
        inputprodukangsuran.setText(name);
    }
}