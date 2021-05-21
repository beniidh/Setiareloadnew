package com.c.dompetabata;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;

public class Login_Activity extends AppCompatActivity {
    EditText numberphone;
    Button login_button;
    TextView register;
    ImageView logologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        getSupportActionBar().hide();

        //ID Define
        numberphone = findViewById(R.id.numberphone);
        login_button = findViewById(R.id.login_button);
        register = findViewById(R.id.register);
        logologin = findViewById(R.id.logologin);
        setLogologin();

        // Event Onclick for register activity
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(Login_Activity.this, Register_activity.class);
                startActivity(register);
            }
        });

        // Event Onclick for login activity
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation(numberphone.getText().toString());
            }
        });
    }

    private void validation(String number) {
        if (number.isEmpty()) {
            StyleableToast.makeText(getApplicationContext(), "Nomor tidak boleh kosong", Toast.LENGTH_SHORT, R.style.mytoast).show();
        }

    }

    private void setLogologin() {
        logologin.setImageDrawable(getDrawable(R.drawable.logoabata));
    }


}
