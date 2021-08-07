package com.c.dompetabata.MarkUP;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;

import com.c.dompetabata.R;

public class markup extends AppCompatActivity implements ModalMarUp.BottomSheetListenerKabupaten {

    EditText markupEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markup);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Markup <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        markupEditText = findViewById(R.id.markupEditText);
        markupEditText.setOnClickListener(v -> {
            ModalMarUp modalMarUp = new ModalMarUp();
            modalMarUp.show(getSupportFragmentManager(),"ModalMarkUp");

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
        markupEditText.setText(name);
    }
}