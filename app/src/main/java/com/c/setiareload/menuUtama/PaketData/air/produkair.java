package com.c.setiareload.menuUtama.PaketData.air;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.R;
import com.c.setiareload.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class produkair extends AppCompatActivity implements ModalAir.BottomSheetListenerProduksms{

    EditText inputprodukair, inputnomorair;
    TextView tujukarakterair;
    AdapterProdukAir adapterProdukAir;
    ArrayList<ResponProdukAir.VoucherData> mAir = new ArrayList<>();
    RecyclerView recyclerView;
    String type ="PASCABAYAR";
    String idd;

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
        recyclerView = findViewById(R.id.ReyProdukAAir);

        adapterProdukAir = new AdapterProdukAir(getApplicationContext(), mAir, inputnomorair.getText().toString(), type);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterProdukAir);

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
                getProdukk(getIdd(),inputnomorair.getText().toString());
            }
        });

        inputprodukair.setOnClickListener(v -> {

            String id = getIntent().getStringExtra("id");
            ModalAir modalAir = new ModalAir();
            Bundle bundle = new Bundle();
            bundle.putString("id",id);
            modalAir.setArguments(bundle);
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
        getProdukk(id,inputnomorair.getText().toString());
        setIdd(id);

    }

    public void getProdukk(String id,String nomor){
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukAir> call = api.getProdukAir(token,id);
        call.enqueue(new Callback<ResponProdukAir>() {
            @Override
            public void onResponse(Call<ResponProdukAir> call, Response<ResponProdukAir> response) {
                String code = response.body().getCode();
                if (code.equals("200")){

                    mAir = response.body().getData();
                    adapterProdukAir = new AdapterProdukAir(produkair.this, mAir, nomor, type);
                    recyclerView.setAdapter(adapterProdukAir);

                }else {

                    Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponProdukAir> call, Throwable t) {

            }
        });


    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }
}