package com.c.setiareload.menuUtama.PaketData.air;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.LoadingPrimer;
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


        registerForContextMenu(inputnomorair);
//        Toast.makeText(getApplicationContext(),String.valueOf(item),Toast.LENGTH_SHORT).show();

//        inputnomorair.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//
//                inputnomorair.setText(item.getText());
//                return true;
//            }
//        });
        adapterProdukAir = new AdapterProdukAir(produkair.this,getApplicationContext(), mAir, inputnomorair.getText().toString(), type);
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
                getProdukAir(getIntent().getStringExtra("id"));
            }
            @Override
            public void afterTextChanged(Editable s) {


            }
        });

//        inputprodukair.setOnClickListener(v -> {
//
//            String id = getIntent().getStringExtra("id");
//            ModalAir modalAir = new ModalAir();
//            Bundle bundle = new Bundle();
//            bundle.putString("id",id);
//            modalAir.setArguments(bundle);
//            modalAir.show(getSupportFragmentManager(), "Modal Air");
//        });

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        String teks ="";
        ClipData clip = clipboard.getPrimaryClip();
        ClipData.Item itema = clip.getItemAt(0);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.paste:
                String nomor = itema.getText().toString();
                if(nomor.substring(0,3).equals("+62")){
                    String nom = "0"+ nomor.substring(3,nomor.length());
                    inputnomorair.setText(nom);

                }else {
                    inputnomorair.setText(nomor);
                }
                break;
        }
        return true;
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


    }

    public void getProdukk(String id){

        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukAir> call = api.getProdukAir(token,id);
        call.enqueue(new Callback<ResponProdukAir>() {
            @Override
            public void onResponse(Call<ResponProdukAir> call, Response<ResponProdukAir> response) {
                String code = response.body().getCode();
                if (code.equals("200")){

                    mAir = response.body().getData();
                    adapterProdukAir = new AdapterProdukAir(produkair.this,produkair.this, mAir, inputnomorair.getText().toString(), type);
                    recyclerView.setAdapter(adapterProdukAir);


                }else {

                    Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponProdukAir> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.toString(),Toast.LENGTH_LONG).show();

            }
        });


    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
    }

    private void getProdukAir(String id){

        String token = "Bearer "+ Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponAir> call = api.getProdukCategoryAir(token,id);
        call.enqueue(new Callback<ResponAir>() {
            @Override
            public void onResponse(Call<ResponAir> call, Response<ResponAir> response) {
                String id1 = response.body().getData().get(0).getId();
                getProdukk(id1);

            }
            @Override
            public void onFailure(Call<ResponAir> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();
            }
        });

    }



}