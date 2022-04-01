package com.c.setiareload.menuUtama.PajakPBB;

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
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
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

public class produkPajakPBB extends AppCompatActivity implements ModalPajak.BottomSheetListenerProduksms {

    EditText inputprodukpajak, inputnomorpajak;
    TextView tujukarakterpajak;
    RecyclerView recyclerView;
    ArrayList<ResponProdukPBB.VoucherData> mData = new ArrayList<>();
    AdapterProdukPBB adapterProdukPBB;
    String type = "PASCABAYAR";
    String idd;
    ImageView getContact;
    private static final int CONTACT_PERMISSION_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk_pajak_p_b_b);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Pajak PBB <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        inputprodukpajak = findViewById(R.id.inputprodukpajak);
        inputprodukpajak.setFocusable(false);
        inputnomorpajak = findViewById(R.id.inputnomorpajak);
        tujukarakterpajak = findViewById(R.id.tujukarakterpajak);
        recyclerView = findViewById(R.id.ReyProdukPBB);

        adapterProdukPBB = new AdapterProdukPBB(getApplicationContext(), mData, inputnomorpajak.getText().toString(), type);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapterProdukPBB);

        inputprodukpajak.setOnClickListener(v -> {
            String id = getIntent().getStringExtra("id");
            Bundle bundle = new Bundle();
            ModalPajak modalPajak = new ModalPajak();
            bundle.putString("id", id);
            modalPajak.setArguments(bundle);
            modalPajak.show(getSupportFragmentManager(), "Modal pajak");
        });

        getContact = findViewById(R.id.getContact);
        getContact.setOnClickListener(view -> {
            if (checkContactPermission()) {
                openYourActivity();
            } else {
                requestContactPermission();
            }
        });


        inputnomorpajak.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (inputnomorpajak.length() >= 7) {

                    tujukarakterpajak.setVisibility(View.INVISIBLE);
                } else {

                    tujukarakterpajak.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                getProduk(getIdd(), inputnomorpajak.getText().toString());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void getProduk(String id, String nomor) {
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        Api api = RetroClient.getApiServices();
        Call<ResponProdukPBB> call = api.getProdukPBBSub(token, id);
        call.enqueue(new Callback<ResponProdukPBB>() {
            @Override
            public void onResponse(Call<ResponProdukPBB> call, Response<ResponProdukPBB> response) {
                String code = response.body().getCode();
                if (code.equals("200")) {

                    mData = response.body().getData();
                    adapterProdukPBB = new AdapterProdukPBB(getApplicationContext(), mData, nomor, type);
                    recyclerView.setAdapter(adapterProdukPBB);

                } else {

                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponProdukPBB> call, Throwable t) {

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
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onButtonClick(String name, String id) {
        inputprodukpajak.setText(name);
        setIdd(id);
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
                                        inputnomorpajak.setText(nom.replaceAll("-",""));

                                    } else {
                                        inputnomorpajak.setText(contactNumber);
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