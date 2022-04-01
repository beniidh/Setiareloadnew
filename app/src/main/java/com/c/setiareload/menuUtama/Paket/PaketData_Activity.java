package com.c.setiareload.menuUtama.Paket;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.menuUtama.PulsaPrabayar.modelPasca;
import com.c.setiareload.R;
import com.c.setiareload.Respon.ResponSubCategory;
import com.c.setiareload.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaketData_Activity extends AppCompatActivity {

    EditText nomorbelidata;
    private String url;
    ImageView iconproduk;
    modelPasca modelPascaa;
    String idproduk;
    ImageView getContact;
    private static final int CONTACT_PERMISSION_CODE = 1;



    RecyclerView recyclerView;
    AdapterPaketData adapterPaketData;
    String nomor;
    ArrayList<MProdukPaketData> mProdukPaketData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket_data_);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Paket Data <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        iconproduk = findViewById(R.id.iconprodukPaketData);
        nomorbelidata = findViewById(R.id.nomorbelipulsaPaketDat);
        recyclerView = findViewById(R.id.ReyPaketData);

        adapterPaketData = new AdapterPaketData(getApplicationContext(), mProdukPaketData,nomor,getUrl());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterPaketData);
        registerForContextMenu(nomorbelidata);

        getContact = findViewById(R.id.getContact);
        getContact.setOnClickListener(view -> {
            if (checkContactPermission()) {
                openYourActivity();
            } else {
                requestContactPermission();
            }
        });


        nomorbelidata.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (nomorbelidata.length() >= 4) {
                    String provider = nomorbelidata.getText().toString().substring(0,4);
                    Intent intent = getIntent();
                    Preference.setNo(getApplicationContext(),nomorbelidata.getText().toString());
                    String id = intent.getStringExtra("id");
                    getSubCategory(provider,id);

                }else{
                    ArrayList<MProdukPaketData> mProdukPaketDataa = new ArrayList<>();
                    adapterPaketData = new AdapterPaketData(getApplicationContext(),mProdukPaketDataa,nomorbelidata.getText().toString(),getUrl());
                    setUrl("http//");


                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                Picasso.get().load(getUrl()).into(iconproduk);
                if(nomorbelidata.length()>= 4) {

                  getPaketDatabyID(getIdproduk(),nomorbelidata.getText().toString());
                }
            }
        });




    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void getSubCategory(String prefix,String id) {

        String token = "Bearer "+ Preference.getToken(getApplicationContext());

        Api api = RetroClient.getApiServices();
        Call<ResponSubCategory> call = api.getSubPrdoductByPrefix(token,prefix,id);
        call.enqueue(new Callback<ResponSubCategory>() {
            @Override
            public void onResponse(Call<ResponSubCategory> call, Response<ResponSubCategory> response) {
                String code = response.body().getCode();
                String message = response.body().getMessage();
                if (code.equals("200")) {

                    setUrl(response.body().getData().getIcon());
                    setIdproduk(response.body().getData().getId());
                    getPaketDatabyID(response.body().getData().getId(),nomorbelidata.getText().toString());
                    Picasso.get().load(response.body().getData().getIcon()).into(iconproduk);
                } else {

                    setUrl(null);
                }

            }

            @Override
            public void onFailure(Call<ResponSubCategory> call, Throwable t) {

            }
        });


    }

    public void getPaketDatabyID(String id,String nomorr){

        String token = "Bearer " +Preference.getToken(getApplicationContext());

        Api api = RetroClient.getApiServices();
        Call<ResponPaketData> call = api.getPaketDataProduk(token,id);
        call.enqueue(new Callback<ResponPaketData>() {
            @Override
            public void onResponse(Call<ResponPaketData> call, Response<ResponPaketData> response) {
                String code = response.body().getCode();
                if(code.equals("200")){
                    mProdukPaketData = response.body().getData();
                    adapterPaketData = new AdapterPaketData(getApplicationContext(),mProdukPaketData,nomorr,getUrl());
                    recyclerView.setAdapter(adapterPaketData);

                }else {
                    ArrayList<MProdukPaketData> mProdukPaketDataa = new ArrayList<>();
                    adapterPaketData = new AdapterPaketData(getApplicationContext(),mProdukPaketDataa,nomorr,getUrl());
                    recyclerView.setAdapter(adapterPaketData);

                    StyleableToast.makeText(getApplicationContext(),"Produk Tidak ditemukan",Toast.LENGTH_LONG,R.style.mytoast2).show();
                }
            }

            @Override
            public void onFailure(Call<ResponPaketData> call, Throwable t) {

            }
        });

    }

    public String getIdproduk() {
        return idproduk;
    }
    public void setIdproduk(String idproduk) {
        this.idproduk = idproduk;
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
        String teks = "";
        ClipData clip = clipboard.getPrimaryClip();
        ClipData.Item itema = clip.getItemAt(0);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.paste:
                String nomor = itema.getText().toString();
                if (nomor.substring(0, 3).equals("+62")) {
                    nomor = "0" + nomor.substring(3);
                    nomorbelidata.setText(nomor);

                } else {
                    nomorbelidata.setText(nomor);
                }

                String provider = nomor.substring(0,4);
                Intent intent = getIntent();
                Preference.setNo(getApplicationContext(),nomor);
                String id = intent.getStringExtra("id");
                getSubCategory(provider,id);


                break;
        }
        return true;
    }

    //Chek Contact Permision
    private boolean checkContactPermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    //permintaan koneksi
    private void requestContactPermission() {
        String[] permision = {Manifest.permission.READ_CONTACTS};
        ActivityCompat.requestPermissions(this, permision, CONTACT_PERMISSION_CODE);

    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Cursor cursor1, cursor2;
                        Uri uri = data.getData();

                        cursor1 = getContentResolver().query(uri, null, null, null, null);
                        if (cursor1.moveToFirst()) {
                            @SuppressLint("Range") String contacid = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));
                            @SuppressLint("Range") String contactName = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            @SuppressLint("Range") String idResult = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                            int idResultHold = Integer.parseInt(idResult);

                            if (idResultHold == 1) {
                                cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.
                                                Phone.CONTENT_URI, null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " +
                                                contacid, null, null);

                                while (cursor2.moveToNext()) {

                                    @SuppressLint("Range") String contactNumber = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    String nom = contactNumber;
                                    if (contactNumber.substring(0, 3).equals("+62")) {
                                       nom = "0" + contactNumber.substring(3).replaceAll(" ","");
                                        nomorbelidata.setText(nom.replaceAll("-",""));

                                    } else {
                                        nomorbelidata.setText(nom.replaceAll("-",""));
                                    }
                                    String provider = nom.substring(0,4);
                                    Intent intent = getIntent();
                                    Preference.setNo(getApplicationContext(),nom);
                                    String id = intent.getStringExtra("id");
                                    getSubCategory(provider,id);

                                }

                                cursor2.close();


                            }

                            cursor1.close();
                        }


                    }
                }
            });

    public void openYourActivity() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        launchSomeActivity.launch(intent);
    }

}