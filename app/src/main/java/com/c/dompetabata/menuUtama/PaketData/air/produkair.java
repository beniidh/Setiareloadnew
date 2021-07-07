package com.c.dompetabata.menuUtama.PaketData.air;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.c.dompetabata.R;

public class produkair extends AppCompatActivity implements ModalAir.BottomSheetListenerProduksms{

    EditText inputprodukair, inputnomorair;
    TextView tujukarakterair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produkair);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Air <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        inputprodukair = findViewById(R.id.inputprodukair);
        inputprodukair.setFocusable(false);
        inputnomorair = findViewById(R.id.inputnomorair);
        tujukarakterair = findViewById(R.id.tujukarakterair);

        inputnomorair.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (inputnomorair.length() >= 7) {
                    tujukarakterair.setVisibility(View.INVISIBLE);
                } else {
                    tujukarakterair.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        inputprodukair.setOnClickListener(v -> {
            ModalAir modalAir = new ModalAir();
            modalAir.show(getSupportFragmentManager(), "Modal Air");
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
        inputprodukair.setText(name);

    }
}