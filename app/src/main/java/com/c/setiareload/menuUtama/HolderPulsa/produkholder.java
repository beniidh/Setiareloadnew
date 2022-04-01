package com.c.setiareload.menuUtama.HolderPulsa;

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

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.LoadingPrimer;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.R;
import com.c.setiareload.sharePreference.Preference;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class produkholder extends AppCompatActivity {

    EditText inputnomorproduk;
    RecyclerView ReyProdukHolder;
    AdapterProdukHolder adapterProdukHolder;
    ArrayList<ResponProdukHolder.Mdata> produk = new ArrayList<>();
    ImageView getContact;
    private static final int CONTACT_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produkholder);
        Preference.setNo(getApplicationContext(),"");
        String produkname = getIntent().getStringExtra("name");

        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 + "'><b>" + produkname + "<b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        inputnomorproduk = findViewById(R.id.inputnomorproduk);
        ReyProdukHolder = findViewById(R.id.ReyProdukHolder);

        registerForContextMenu(inputnomorproduk);
        adapterProdukHolder = new AdapterProdukHolder(getApplicationContext(), produk, inputnomorproduk.getText().toString());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        ReyProdukHolder.setLayoutManager(mLayoutManager);
        ReyProdukHolder.setAdapter(adapterProdukHolder);
        String id = getIntent().getStringExtra("id");
        String jenis = getIntent().getStringExtra("jenis");
        if (jenis.equals("sub")) {
            getProdukSub(id);
        } else {
            getProduk(id);
        }


        getContact = findViewById(R.id.getContact);
        getContact.setOnClickListener(view -> {
            if (checkContactPermission()) {
                openYourActivity();
            } else {
                requestContactPermission();
            }
        });


        inputnomorproduk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Preference.setNo(getApplicationContext(), inputnomorproduk.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void getProdukSub(String id) {
        LoadingPrimer loadingPrimer = new LoadingPrimer(produkholder.this);
        loadingPrimer.startDialogLoading();

        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukHolder> call = api.getProdukHolderSub(token, id);
        call.enqueue(new Callback<ResponProdukHolder>() {
            @Override
            public void onResponse(Call<ResponProdukHolder> call, Response<ResponProdukHolder> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    produk = response.body().getData();
                    adapterProdukHolder = new AdapterProdukHolder(getApplicationContext(), produk, Preference.getNo(getApplicationContext()));
                    ReyProdukHolder.setAdapter(adapterProdukHolder);

                    loadingPrimer.dismissDialog();

                } else {

                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                    loadingPrimer.dismissDialog();
                }

            }

            @Override
            public void onFailure(Call<ResponProdukHolder> call, Throwable t) {

                Toast.makeText(getApplicationContext(), "Koneksi tidak stabil,silahkan ulangi", Toast.LENGTH_LONG).show();
                loadingPrimer.dismissDialog();
            }
        });


    }

    private void getProduk(String id) {
        LoadingPrimer loadingPrimer = new LoadingPrimer(produkholder.this);
        loadingPrimer.startDialogLoading();

        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukHolder> call = api.getProdukHolder(token, id);
        call.enqueue(new Callback<ResponProdukHolder>() {
            @Override
            public void onResponse(Call<ResponProdukHolder> call, Response<ResponProdukHolder> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    produk = response.body().getData();
                    adapterProdukHolder = new AdapterProdukHolder(getApplicationContext(), produk, Preference.getNo(getApplicationContext()));
                    ReyProdukHolder.setAdapter(adapterProdukHolder);
                    loadingPrimer.dismissDialog();

                } else {

                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                    loadingPrimer.dismissDialog();
                }

            }

            @Override
            public void onFailure(Call<ResponProdukHolder> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi tidak stabil,silahkan ulangi", Toast.LENGTH_LONG).show();
                loadingPrimer.dismissDialog();
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
                                        nom = "0" + contactNumber.substring(3).replaceAll(" ", "");
                                        inputnomorproduk.setText(nom.replaceAll("-", ""));

                                    } else {
                                        inputnomorproduk.setText(nom.replaceAll("-", ""));
                                    }

                                    Preference.setNo(getApplicationContext(), nom.replaceAll("-", ""));


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
                String nom = "";
                if (nomor.substring(0, 3).equals("+62")) {
                    nom = "0" + nomor.substring(3, nomor.length());
                    inputnomorproduk.setText(nom);
                } else {
                    nom = nomor;
                    inputnomorproduk.setText(nom);
                }

                Intent intent = getIntent();
                Preference.setNo(getApplicationContext(), nom.replaceAll("-",""));
                String id = intent.getStringExtra("id");
                String jenis = intent.getStringExtra("jenis");
                if (jenis.equals("sub")) {
                    getProdukSub(id);
                } else {
                    getProduk(id);
                }


                break;
        }
        return true;
    }
}