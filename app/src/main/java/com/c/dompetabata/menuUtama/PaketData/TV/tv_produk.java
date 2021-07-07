package com.c.dompetabata.menuUtama.PaketData.TV;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.c.dompetabata.R;

public class tv_produk extends AppCompatActivity implements ModalTV.BottomSheetListenerProduksms {
    EditText inputproduktv,inputnomortv;
    TextView tujukaraktertv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_produk);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>TV <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        inputnomortv = findViewById(R.id.inputnomortv);
        inputproduktv = findViewById(R.id.inputproduktv);
        inputproduktv.setFocusable(false);
        inputproduktv.setOnClickListener(v -> {
            ModalTV modalTV = new ModalTV();
            modalTV.show(getSupportFragmentManager(),"modal tv");
        });

        tujukaraktertv = findViewById(R.id.tujukaraktertv);

        inputnomortv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(inputnomortv.length() >= 7){
                    tujukaraktertv.setVisibility(View.INVISIBLE);
                } else {

                    tujukaraktertv.setVisibility(View.VISIBLE);
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
        inputproduktv.setText(name);
    }
}