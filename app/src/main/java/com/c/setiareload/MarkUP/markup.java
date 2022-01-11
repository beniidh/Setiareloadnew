package com.c.setiareload.MarkUP;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Fragment.RiwayatTransaksi.TabAdapter;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.MarkUP.FragmentMarkUp.FragmentGlobal;
import com.c.setiareload.MarkUP.markupSpesifik.FragmenSpesifik;
import com.c.setiareload.MarkUP.markupSpesifik.ModalProdukDHS;
import com.c.setiareload.MarkUP.markupSpesifik.ModalSubProdukDHS;
import com.c.setiareload.R;
import com.c.setiareload.sharePreference.Preference;
import com.google.android.material.tabs.TabLayout;
import com.muddzdev.styleabletoast.StyleableToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class markup extends AppCompatActivity {

    EditText markupEditText, markupNilai;
    Button updateharga;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markup);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Markup <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        markupNilai = findViewById(R.id.markupNilai);
        updateharga = findViewById(R.id.markupButton);
        updateharga.setOnClickListener(view -> {

            if(!markupNilai.getText().toString().isEmpty()){
                int nominal = Integer.parseInt(markupNilai.getText().toString());
                MarkUP(nominal);

            }else {

                Toast.makeText(getApplicationContext(),"Jumlah tidak boleh kosong",Toast.LENGTH_SHORT).show();
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

    public void MarkUP(int nominal) {
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        sendMarkUP sendMarkUP = new sendMarkUP(nominal);

        Call<ResponMarkup> call = api.markup(token, sendMarkUP);
        call.enqueue(new Callback<ResponMarkup>() {
            @Override
            public void onResponse(Call<ResponMarkup> call, Response<ResponMarkup> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    StyleableToast.makeText(getApplicationContext(), "Berhasil", Toast.LENGTH_SHORT, R.style.mytoast).show();
//                    finish();
                    markupNilai.setText("");
                } else {
                    StyleableToast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT, R.style.mytoast2).show();
                }

            }

            @Override
            public void onFailure(Call<ResponMarkup> call, Throwable t) {

            }
        });

    }


}