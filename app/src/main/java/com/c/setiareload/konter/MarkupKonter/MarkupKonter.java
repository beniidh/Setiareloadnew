package com.c.setiareload.konter.MarkupKonter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.R;
import com.c.setiareload.sharePreference.Preference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarkupKonter extends AppCompatActivity {

    EditText nominalMarkup;
    Button buttonMarkupKonter;
    TextView namakirim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markup_konter);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 + "'><b>Markup Konter <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        namakirim = findViewById(R.id.namakirim);
        namakirim.setText("Markup Konter : "+getIntent().getStringExtra("namakonter"));
        nominalMarkup = findViewById(R.id.nominalMarkup);
        buttonMarkupKonter = findViewById(R.id.buttonMarkupKonter);
        buttonMarkupKonter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMarkUp(Integer.parseInt(nominalMarkup.getText().toString()));
            }
        });

    }

    private void sendMarkUp(int markup) {

        String id = getIntent().getStringExtra("id");
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        mMarkKonter MMarkKonter = new mMarkKonter(markup);
        Call<responMUK> call = api.markupKonter(token, MMarkKonter,id );
        call.enqueue(new Callback<responMUK>() {
            @Override
            public void onResponse(Call<responMUK> call, Response<responMUK> response) {
                if(response.body().getCode().equals("200")){
                    Toast.makeText(getApplicationContext(),"Berhasil Merubah MarkUp",Toast.LENGTH_LONG).show();
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<responMUK> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();

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