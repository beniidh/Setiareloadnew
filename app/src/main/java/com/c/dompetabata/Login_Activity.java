package com.c.dompetabata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;

public class Login_Activity extends AppCompatActivity {
    EditText numberphone;
    Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        numberphone = findViewById(R.id.numberphone);
        login_button = findViewById(R.id.login_button);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberphone.getText().toString().isEmpty()){
                    StyleableToast.makeText(getApplicationContext(),"Nomor tidak boleh kosong", Toast.LENGTH_SHORT,R.style.mytoast).show();
                }
            }
        });
    }


}
