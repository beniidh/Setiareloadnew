package com.c.setiareload.menuUtama.UangElektronik;

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

public class ProdukUangElektronik extends AppCompatActivity implements ModalUangElektronik.BottomSheetListenerProduksms {

    EditText inputprodukuangelektronik, inputnomoruangelektronik;
    TextView tujukarakteruangelektronik;
    RecyclerView recyclerView;
    AdapterProdukUE adapterProdukUE;
    ArrayList<ResponProdukUE.VoucherData> vdata = new ArrayList<>();
    String type = "PRABAYAR";
    String idd;
    ImageView getContact;
    private static final int CONTACT_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_uang_elektronik);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Uang Elektronik <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        inputprodukuangelektronik = findViewById(R.id.inputprodukuangelektronik);
        inputprodukuangelektronik.setFocusable(false);
        inputnomoruangelektronik = findViewById(R.id.inputnomoruangelektronik);
        tujukarakteruangelektronik = findViewById(R.id.tujukarakteruangelektronik);

        getContact = findViewById(R.id.getContact);
        getContact.setOnClickListener(view -> {
            if (checkContactPermission()) {
                openYourActivity();
            } else {
                requestContactPermission();
            }
        });

        recyclerView = findViewById(R.id.ReyUangElektronik);
        adapterProdukUE = new AdapterProdukUE(getApplicationContext(), vdata, inputnomoruangelektronik.getText().toString(), type);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterProdukUE);

        String id = getIntent().getStringExtra("id");

        registerForContextMenu(inputnomoruangelektronik);
        inputnomoruangelektronik.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (inputnomoruangelektronik.length() >= 7) {
                    tujukarakteruangelektronik.setVisibility(View.INVISIBLE);
                    Preference.setNo(getApplicationContext(),inputnomoruangelektronik.getText().toString());


                } else {

                    tujukarakteruangelektronik.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                getProduk(getIdd(), inputnomoruangelektronik.getText().toString());

            }
        });

        inputprodukuangelektronik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ModalUangElektronik modalUangElektronik = new ModalUangElektronik();
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                modalUangElektronik.setArguments(bundle);
                modalUangElektronik.show(getSupportFragmentManager(), "Uang elektronik");
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
        inputprodukuangelektronik.setText(name);
        getProduk(id, inputnomoruangelektronik.getText().toString());
        setIdd(id);
    }

    public void getProduk(String id, String nomor) {
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukUE> call = api.getProdukUE(token, id);
        call.enqueue(new Callback<ResponProdukUE>() {
            @Override
            public void onResponse(Call<ResponProdukUE> call, Response<ResponProdukUE> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {
                    vdata = response.body().getData();
                } else {
                    vdata.clear();
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_SHORT).show();
                }
                adapterProdukUE = new AdapterProdukUE(getApplicationContext(), vdata, nomor, type);
                recyclerView.setAdapter(adapterProdukUE);
            }

            @Override
            public void onFailure(Call<ResponProdukUE> call, Throwable t) {

            }
        });


    }

    public String getIdd() {
        return idd;
    }

    public void setIdd(String idd) {
        this.idd = idd;
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
                    String nom = "0" + nomor.substring(3, nomor.length());
                    inputnomoruangelektronik.setText(nom);

                } else {
                    inputnomoruangelektronik.setText(nomor);
                }


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

                                    if (contactNumber.substring(0, 3).equals("+62")) {
                                        String nom = "0" + contactNumber.substring(3).replaceAll(" ","");
                                        inputnomoruangelektronik.setText(nom.replaceAll("-",""));

                                    } else {
                                        inputnomoruangelektronik.setText(contactNumber);
                                    }

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