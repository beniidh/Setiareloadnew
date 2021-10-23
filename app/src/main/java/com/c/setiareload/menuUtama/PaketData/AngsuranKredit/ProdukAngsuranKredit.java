package com.c.setiareload.menuUtama.PaketData.AngsuranKredit;

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

public class ProdukAngsuranKredit extends AppCompatActivity implements ModalAngsuran.BottomSheetListenerProduksms {

    EditText inputprodukangsuran,inputnomorangsuran;
    TextView tujukarakterangsuran;
    AdapterProdukAngsuran adapterProdukAngsuran;
    RecyclerView recyclerView;
    ArrayList<ResponProdukAngsuran.VoucherData> mData = new ArrayList<>();
    String type ="PASCABAYAR";
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_angsuran_kredit);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Angsuran Kredit <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        recyclerView = findViewById(R.id.ReyProdukAngsuran);

        inputprodukangsuran = findViewById(R.id.inputprodukangsuran);
        inputprodukangsuran.setFocusable(false);

        inputnomorangsuran = findViewById(R.id.inputnomorangsuran);
        adapterProdukAngsuran = new AdapterProdukAngsuran(getApplicationContext(), mData, inputnomorangsuran.getText().toString(), type);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterProdukAngsuran);

        inputprodukangsuran.setOnClickListener(v -> {
            String id = getIntent().getStringExtra("id");
            Bundle bundle = new Bundle();
            ModalAngsuran modalAngsuran = new ModalAngsuran();
            bundle.putString("id",id);
            modalAngsuran.setArguments(bundle);
            modalAngsuran.show(getSupportFragmentManager(),"modal angsuran");
        });


        tujukarakterangsuran = findViewById(R.id.tujukarakterangsuran);

        inputnomorangsuran.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(inputnomorangsuran.length() >= 7){
                    tujukarakterangsuran.setVisibility(View.INVISIBLE);
                } else {
                    tujukarakterangsuran.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                getProduk(getId(),inputnomorangsuran.getText().toString());

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
        getProduk(id,inputnomorangsuran.getText().toString());
        inputprodukangsuran.setText(name);
        setId(id);
    }

    public void getProduk(String id,String nomor){
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukAngsuran> call = api.getProdukAngsuranSub(token,id);
        call.enqueue(new Callback<ResponProdukAngsuran>() {
            @Override
            public void onResponse(Call<ResponProdukAngsuran> call, Response<ResponProdukAngsuran> response) {
                String code = response.body().getCode();
                if (code.equals("200")){

                    mData = response.body().getData();
                    adapterProdukAngsuran = new AdapterProdukAngsuran(getApplicationContext(), mData, nomor, type);
                    recyclerView.setAdapter(adapterProdukAngsuran);

                }else {

                    Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponProdukAngsuran> call, Throwable t) {

            }
        });


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}