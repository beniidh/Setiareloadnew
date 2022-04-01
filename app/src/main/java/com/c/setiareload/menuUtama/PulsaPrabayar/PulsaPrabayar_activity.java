package com.c.setiareload.menuUtama.PulsaPrabayar;

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

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.RetroClient;

import com.c.setiareload.R;
import com.c.setiareload.Respon.ResponSubCategory;
import com.c.setiareload.sharePreference.Preference;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PulsaPrabayar_activity extends AppCompatActivity {
    EditText nomorbelipulsa;
    private String url;
    String idproduk;
    ImageView iconproduk;
    modelPasca modelPascaa;
    RecyclerView reyPulsaPra;
    ArrayList<MPulsaPra> mPulsaPras = new ArrayList<>();
    AdapterPulsaPrabayar adapterPulsaPrabayar;
    String type;
    @SuppressLint("StaticFieldLeak")
    static Activity pulsapra;
    ImageView getContact;
    private static final int CONTACT_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulsa_prabayar_activity);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Pulsa Prabayar <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        iconproduk = findViewById(R.id.iconproduk);
        modelPascaa = new modelPasca();
        reyPulsaPra = findViewById(R.id.reyprodukPulsaPra);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        pulsapra = this;
        nomorbelipulsa = findViewById(R.id.nomorbelipulsa);
        registerForContextMenu(nomorbelipulsa);
        adapterPulsaPrabayar = new AdapterPulsaPrabayar(getApplicationContext(), mPulsaPras, nomorbelipulsa.getText().toString(), getUrl(), type);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        reyPulsaPra.setLayoutManager(mLayoutManager);
        reyPulsaPra.setAdapter(adapterPulsaPrabayar);
        getContact = findViewById(R.id.getContact);
        getContact.setOnClickListener(view -> {
            if (checkContactPermission()) {
                openYourActivity();
            } else {
                requestContactPermission();
            }
        });
        nomorbelipulsa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (nomorbelipulsa.length() >= 4) {
                    String provider = nomorbelipulsa.getText().toString().substring(0, 4);
                    Intent intent = getIntent();
                    Preference.setNo(getApplicationContext(),nomorbelipulsa.getText().toString());
                    String id = intent.getStringExtra("id");
                    getSubCategory(provider, id);

                } else {
                    ArrayList<MPulsaPra> mPulsaPrass = new ArrayList<>();
                    mPulsaPras = mPulsaPrass;
                    adapterPulsaPrabayar = new AdapterPulsaPrabayar(getApplicationContext(), mPulsaPras, nomorbelipulsa.getText().toString(), url, type);
                    reyPulsaPra.setAdapter(adapterPulsaPrabayar);
                    setUrl("http//");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                Picasso.get().load(getUrl()).into(iconproduk);
                if (nomorbelipulsa.length() >= 4) {
                    getProdukBysubID(getIdproduk(), nomorbelipulsa.getText().toString(), getUrl());
                }
            }
        });


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
                String nomor = itema.getText().toString().replaceAll("-","");
                if(nomor.substring(0,3).equals("+62")){
                    nomor = "0"+ nomor.substring(3);
                    nomorbelipulsa.setText(nomor);
                }else {
                    nomorbelipulsa.setText(nomor);
                }

                String provider = nomor.substring(0, 4);
                Intent intent = getIntent();
                Preference.setNo(getApplicationContext(),nomor);
                String id = intent.getStringExtra("id");
                getSubCategory(provider, id);
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


    public String getIdproduk() {
        return idproduk;
    }

    public void setIdproduk(String idproduk) {
        this.idproduk = idproduk;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public void getSubCategory(String prefix, String id) {

        String token = "Bearer " + Preference.getToken(getApplicationContext());

        Api api = RetroClient.getApiServices();
        Call<ResponSubCategory> call = api.getSubPrdoductByPrefix(token, prefix, id);
        call.enqueue(new Callback<ResponSubCategory>() {
            @Override
            public void onResponse(Call<ResponSubCategory> call, Response<ResponSubCategory> response) {
                String code = response.body().getCode();
                String message = response.body().getMessage();
                if (code.equals("200")) {

                    setUrl(response.body().getData().getIcon());
                    setIdproduk(response.body().getData().getId());
                    getProdukBysubID(response.body().getData().getId(),nomorbelipulsa.getText().toString(),response.body().getData().getIcon());
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

    public void getProdukBysubID(String id, String nomor, String url) {

        Api api = RetroClient.getApiServices();
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Call<ResponPulsaPra> call = api.getProdukPulsaPraById(token, id);
        call.enqueue(new Callback<ResponPulsaPra>() {
            @Override
            public void onResponse(Call<ResponPulsaPra> call, Response<ResponPulsaPra> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    mPulsaPras = (ArrayList<MPulsaPra>) response.body().getData();
                    adapterPulsaPrabayar = new AdapterPulsaPrabayar(getApplicationContext(), mPulsaPras, nomor, url, type);
                    reyPulsaPra.setAdapter(adapterPulsaPrabayar);

                }

            }

            @Override
            public void onFailure(Call<ResponPulsaPra> call, Throwable t) {

            }
        });

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
                                        nomorbelipulsa.setText(nom.replaceAll("-",""));


                                    } else {
                                        nomorbelipulsa.setText(nom.replaceAll("-",""));
                                    }

                                    String provider = nom.substring(0, 4);
                                    Intent intent = getIntent();
                                    Preference.setNo(getApplicationContext(),nom);
                                    String id = intent.getStringExtra("id");
                                    getSubCategory(provider, id);

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