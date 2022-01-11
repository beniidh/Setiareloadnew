package com.c.setiareload.pinNew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Api.Value;
import com.c.setiareload.Helper.GpsTracker;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Helper.utils;
import com.c.setiareload.Login_Activity;
import com.c.setiareload.Model.Mlogin;
import com.c.setiareload.R;
import com.c.setiareload.drawer_activity;
import com.c.setiareload.menuUtama.PaketData.PulsaPrabayar.KonfirmasiPembayaran;
import com.c.setiareload.menuUtama.PaketData.PulsaPrabayar.MTransaksiPraPulsa;
import com.c.setiareload.menuUtama.PaketData.PulsaPrabayar.ResponTransaksiPulsaPra;
import com.c.setiareload.menuUtama.PaketData.PulsaPrabayar.TransaksiPending;
import com.c.setiareload.sharePreference.Preference;
import com.google.firebase.messaging.FirebaseMessaging;
import com.muddzdev.styleabletoast.StyleableToast;
import com.oakkub.android.PinEditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pin_transaksi extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MYTAG";
    private Button num1, num2, num3, num4, num5, num6, num7, num8, num9, num0;
    private ImageButton backButton;
    private ImageView dot1, dot2, dot3, dot4, dot5, dot6;
    private TextView nameTextView;
    private Handler handler;
    PinEditText pin1;
    String telepon;
    GpsTracker gpsTracker;
    int salah = 0;
    TextView warningpinsalah;

    // Actual password
    private String actualPassword;
    //Number of digits entered by the user
    private int noOfDigits;
    // Store the digits entered by the user
    private ArrayList<Integer> passwordTryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_transaksi);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>PIN<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);


        noOfDigits = 0;
        actualPassword = getResources().getString(R.string.password_actual);
        handler = new Handler();

        initialiseWidgets();
        getLocation();

    }

    private void initialiseWidgets() {
        //Numpad widgets
        num1 = findViewById(R.id.btnNumpad1);
        num2 = findViewById(R.id.btnNumpad2);
        num3 = findViewById(R.id.btnNumpad3);
        num4 = findViewById(R.id.btnNumpad4);
        num5 = findViewById(R.id.btnNumpad5);
        num6 = findViewById(R.id.btnNumpad6);
        num7 = findViewById(R.id.btnNumpad7);
        num8 = findViewById(R.id.btnNumpad8);
        num9 = findViewById(R.id.btnNumpad9);
        num0 = findViewById(R.id.btnNumpad0);
        backButton = findViewById(R.id.btnNumpadBack);
        // Numpad widgets OnClickListeners
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        num5.setOnClickListener(this);
        num6.setOnClickListener(this);
        num7.setOnClickListener(this);
        num8.setOnClickListener(this);
        num9.setOnClickListener(this);
        num0.setOnClickListener(this);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOfDigits <= 6 && noOfDigits > 0) {
                    noOfDigits = 0;

                    clearDotsIndicator();
                    passwordTryList.clear();

                }
            }
        });

        //Dots widgets(imageViews)
        dot1 = findViewById(R.id.dot_one);
        dot2 = findViewById(R.id.dot_two);
        dot3 = findViewById(R.id.dot_three);
        dot4 = findViewById(R.id.dot_four);
        dot5 = findViewById(R.id.dot_five);
        dot6 = findViewById(R.id.dot_six);
        //Header
        nameTextView = findViewById(R.id.name_textview);
    }

    // Back button's listener is in initialiseWidgets(), not here
    @Override
    public void onClick(View v) {
        // Check if the number of digits already entered by the user is 0 to 3;
        // If so, register the user's click, else do nothing
        // Calls attemptUnlock() and passes noOfDigits after every listener trigger
        if (noOfDigits >= 0 && noOfDigits <= 5) {
            noOfDigits += 1;
            switch (v.getId()) {
                case R.id.btnNumpad1:
                    passwordTryList.add(1);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad2:
                    passwordTryList.add(2);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad3:
                    passwordTryList.add(3);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad4:
                    passwordTryList.add(4);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad5:
                    passwordTryList.add(5);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad6:
                    passwordTryList.add(6);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad7:
                    passwordTryList.add(7);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad8:
                    passwordTryList.add(8);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad9:
                    passwordTryList.add(9);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
                case R.id.btnNumpad0:
                    passwordTryList.add(0);
                    setDotsIndicator(noOfDigits);
                    attemptUnlock(noOfDigits);
                    break;
            }
        }
    }

    // Clears ALL the dots indicators to initial state - hollow circle
    private void clearDotsIndicator() {
        dot1.setImageResource(R.drawable.hollow_circle);
        dot2.setImageResource(R.drawable.hollow_circle);
        dot3.setImageResource(R.drawable.hollow_circle);
        dot4.setImageResource(R.drawable.hollow_circle);
        dot5.setImageResource(R.drawable.hollow_circle);
        dot6.setImageResource(R.drawable.hollow_circle);
    }

    // Sets the dots indicator from hollow to filled based on number of digits entered by user
    private void setDotsIndicator(int num) {
        clearDotsIndicator();
        if (num == 1) {
            dot1.setImageResource(R.drawable.filled_circle_default);
        } else if (num == 2) {
            dot1.setImageResource(R.drawable.filled_circle_default);
            dot2.setImageResource(R.drawable.filled_circle_default);
        } else if (num == 3) {
            dot1.setImageResource(R.drawable.filled_circle_default);
            dot2.setImageResource(R.drawable.filled_circle_default);
            dot3.setImageResource(R.drawable.filled_circle_default);
        } else if (num == 4) {
            dot1.setImageResource(R.drawable.filled_circle_default);
            dot2.setImageResource(R.drawable.filled_circle_default);
            dot3.setImageResource(R.drawable.filled_circle_default);
            dot4.setImageResource(R.drawable.filled_circle_default);
        } else if (num == 5) {
            dot1.setImageResource(R.drawable.filled_circle_default);
            dot2.setImageResource(R.drawable.filled_circle_default);
            dot3.setImageResource(R.drawable.filled_circle_default);
            dot4.setImageResource(R.drawable.filled_circle_default);
            dot5.setImageResource(R.drawable.filled_circle_default);
        } else if (num >= 6) {
            dot1.setImageResource(R.drawable.filled_circle_default);
            dot2.setImageResource(R.drawable.filled_circle_default);
            dot3.setImageResource(R.drawable.filled_circle_default);
            dot4.setImageResource(R.drawable.filled_circle_default);
            dot5.setImageResource(R.drawable.filled_circle_default);
            dot6.setImageResource(R.drawable.filled_circle_default);
        }
    }

    // Checks if the numOfDigits == 4, if so then compare the digits in the ArrayList to the actual password
    private void attemptUnlock(int num) {
        if (num == 6) {
            noOfDigits = 0;

            // Convert chars in ArrayList to a string
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < passwordTryList.size(); i++) {
                sb.append(passwordTryList.get(i));
            }
            String passwordTry = sb.toString();

            TransaksiPulsaPra(utils.hmacSha(passwordTry));

            passwordTryList.clear();

        }
    }

    // Sets the dots to greeb and sets succeed animation (vertical vibration)
    // At end of animation, resets the dots to hollow
    private void showSucceedVibration() {
        Animation succeedAnimation = AnimationUtils.loadAnimation(this, R.anim.vertical_vibrate_animation);

        dot1.setImageResource(R.drawable.filled_circle_succeed);
        dot2.setImageResource(R.drawable.filled_circle_succeed);
        dot3.setImageResource(R.drawable.filled_circle_succeed);
        dot4.setImageResource(R.drawable.filled_circle_succeed);
        dot5.setImageResource(R.drawable.filled_circle_succeed);
        dot6.setImageResource(R.drawable.filled_circle_succeed);

        dot1.setAnimation(succeedAnimation);
        dot2.setAnimation(succeedAnimation);
        dot3.setAnimation(succeedAnimation);
        dot4.setAnimation(succeedAnimation);
        dot5.setAnimation(succeedAnimation);
        dot6.setAnimation(succeedAnimation);

        final ImageView[] dotsArray = new ImageView[6];
        dotsArray[0] = dot1;
        dotsArray[1] = dot2;
        dotsArray[2] = dot3;
        dotsArray[3] = dot4;
        dotsArray[4] = dot5;
        dotsArray[5] = dot6;

        for (int i = 0; i < 6; i++) {
            dotsArray[i].startAnimation(succeedAnimation);
        }

        succeedAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clearDotsIndicator();
                    }
                }, 150);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

    }

    // Sets the dots to red and sets error animation (horizontal vibration)
    // At end of animation, resets the dots to hollow
    private void showErrorVibration() {
        Animation errorAnimation = AnimationUtils.loadAnimation(this, R.anim.horizontal_vibrate_animation);

        dot1.setImageResource(R.drawable.filled_circle_error);
        dot2.setImageResource(R.drawable.filled_circle_error);
        dot3.setImageResource(R.drawable.filled_circle_error);
        dot4.setImageResource(R.drawable.filled_circle_error);
        dot5.setImageResource(R.drawable.filled_circle_error);
        dot6.setImageResource(R.drawable.filled_circle_error);

        dot1.setAnimation(errorAnimation);
        dot2.setAnimation(errorAnimation);
        dot3.setAnimation(errorAnimation);
        dot4.setAnimation(errorAnimation);
        dot5.setAnimation(errorAnimation);
        dot6.setAnimation(errorAnimation);

        dot1.startAnimation(errorAnimation);
        dot2.startAnimation(errorAnimation);
        dot3.startAnimation(errorAnimation);
        dot4.startAnimation(errorAnimation);
        dot5.startAnimation(errorAnimation);
        dot6.startAnimation(errorAnimation);

        //At end of error animation, reset the dots
        errorAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clearDotsIndicator();
                    }
                }, 150);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private String getUserAgent() {

        String ua = new WebView(this).getSettings().getUserAgentString();
        return ua;
    }

    private String getIPaddress() {

        String IP = utils.getIPAddress(true);
        return IP;
    }

    public void getLocation() {
        gpsTracker = new GpsTracker(pin_transaksi.this);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

        } else {
            gpsTracker.showSettingsAlert();
        }
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

    public void TransaksiPulsaPra(String pin) {

        Bundle bundle = getIntent().getExtras();

        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        GpsTracker gpsTracker = new GpsTracker(getApplicationContext());
        String sku_code = bundle.getString("sku_code");
        String customer_code = bundle.getString("nomorr");
        String ref_id = bundle.getString("RefID");
        String type = bundle.getString("inquiry");
        String wallet_type = bundle.getString("wallettype");

        MTransaksiPraPulsa mTransaksiPraPulsa = new MTransaksiPraPulsa(sku_code, customer_code, ref_id, type, wallet_type, Value.getMacAddress(getApplicationContext()), Value.getIPaddress(), Value.getUserAgent(getApplicationContext()),
                pin, gpsTracker.getLongitude(), gpsTracker.getLatitude());

        Call<ResponTransaksiPulsaPra> call = api.transalsiPulsaPra(token, mTransaksiPraPulsa);
        call.enqueue(new Callback<ResponTransaksiPulsaPra>() {
            @Override
            public void onResponse(Call<ResponTransaksiPulsaPra> call, Response<ResponTransaksiPulsaPra> response) {

                if (response.body().getCode().equals("200")) {
                    Intent intent = new Intent(getApplicationContext(), TransaksiPending.class);
                    intent.putExtra("pesan", response.body().getData().getMessage());
                    intent.putExtra("hargatotal", response.body().getData().getTotal_price());
                    intent.putExtra("nomorcustomer", response.body().getData().getCustomer_no());
                    intent.putExtra("create_at", response.body().getData().getCreated_at());
                    intent.putExtra("transaksid", response.body().getData().getRef_id());
                    intent.putExtra("iconn", bundle.getString("iconn"));
                    showSucceedVibration();
                    startActivity(intent);
                    finish();

                } else {
                    StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG, R.style.mytoast2).show();
                    showErrorVibration();
                }

            }

            @Override
            public void onFailure(Call<ResponTransaksiPulsaPra> call, Throwable t) {

            }
        });


    }


}