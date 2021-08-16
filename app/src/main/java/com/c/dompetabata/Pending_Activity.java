package com.c.dompetabata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;

import com.c.dompetabata.Modal.OTPinsert;
import com.c.dompetabata.sharePreference.Preference;

public class Pending_Activity extends AppCompatActivity {

    Button lanjut, keluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Register<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        lanjut = findViewById(R.id.LanjutRegister);
        keluar = findViewById(R.id.KeluarRegister);

        lanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = Preference.getTrackRegister(getApplicationContext());
                if (code.equals("1")) {
                    Intent intent = new Intent(Pending_Activity.this, OTPsend.class);
                    startActivity(intent);

                } else if (code.equals("2")) {
                    Intent intent = new Intent(Pending_Activity.this, OTPinsert.class);
                    startActivity(intent);
                }else if (code.equals("3")) {
                    Intent intent = new Intent(Pending_Activity.this, RegisterFoto_activity.class);
                    startActivity(intent);
                }else if (code.equals("4")) {
                    Intent intent = new Intent(Pending_Activity.this, syaratdanketentuan_activity.class);
                    startActivity(intent);
                }


            }
        });

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preference.setTrackRegister(getApplicationContext(), "");
                Intent intent = new Intent(Pending_Activity.this, Login_Activity.class);
                startActivity(intent);
                finish();
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