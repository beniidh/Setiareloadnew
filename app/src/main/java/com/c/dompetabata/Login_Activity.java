package com.c.dompetabata;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Api.Value;
import com.c.dompetabata.Model.Mlogin;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Login_Activity extends AppCompatActivity {
    EditText numberphone;
    Button login_button;
    TextView register;
    ImageView logologin;
    Mlogin coba;
    ProgressBar progressBar;

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
        progressBar = findViewById(R.id.progressbutton);
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
        } else {
            progressBar.setVisibility(View.VISIBLE);
            login_button.setText("");
            if(isOnline()){

                Login(number,"rahasia123");
            }else {
                progressBar.setVisibility(View.INVISIBLE);
                login_button.setText("Masuk");

                StyleableToast.makeText(getApplicationContext(), "Periksa sambungan internet", Toast.LENGTH_SHORT, R.style.mytoast).show();
            }

//
//            Intent optionOTP = new Intent(Login_Activity.this,OTPsend.class);
//            startActivity(optionOTP);

        }

    }

    private void setLogologin() {
        logologin.setImageDrawable(getDrawable(R.drawable.logoabata));
    }

    private void Login(String telepon,String password){

//        Interceptor interceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request().newBuilder()
//                        .addHeader("Accept", "application/json")
//                        .addHeader("authorization", "oojiiii")
//                        .build();
//                return chain.proceed(request);
//            }
//        };

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Value.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        Value value = new Value(telepon,password);

        Api api = retrofit.create(Api.class);
        Call<Value> call = api.Login(value);
        call.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                progressBar.setVisibility(View.INVISIBLE);
                login_button.setText("Masuk");

                String code = response.body().getMessage();
                StyleableToast.makeText(getApplicationContext(),code,Toast.LENGTH_SHORT, R.style.mytoast).show();


            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                login_button.setText("Masuk");
                StyleableToast.makeText(getApplicationContext(),"Periksa Sambungan internet",Toast.LENGTH_SHORT, R.style.mytoast).show();

            }
        });


    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }


}
