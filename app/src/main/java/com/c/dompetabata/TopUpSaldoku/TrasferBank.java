package com.c.dompetabata.TopUpSaldoku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.c.dompetabata.Helper.utils;
import com.c.dompetabata.R;
import com.c.dompetabata.RegisterFoto_activity;
import com.c.dompetabata.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;

public class TrasferBank extends AppCompatActivity {
    TextView saldokubank;
    Button oktransaksi,uploadBuktiTBSaldo;
    int up =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trasfer_bank);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Pembayaran Saldoku <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        saldokubank = findViewById(R.id.saldokubank);
        uploadBuktiTBSaldo = findViewById(R.id.uploadBuktiTBSaldo);

        uploadBuktiTBSaldo.setOnClickListener(v -> {
            Intent intent = CropImage.activity()
                    .setAspectRatio(1, 1)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .getIntent(TrasferBank.this);

            startActivityForResult(intent, 1001);

        });

        saldokubank.setText(utils.ConvertRP(Preference.getSaldoku(getApplicationContext())));
        oktransaksi = findViewById(R.id.oktransaksi);
        oktransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(up==1){

                Bundle bundle = new Bundle();
                bundle.putString("kode","bank");
                ModalPinTopUpSaldoku modalPinTopUpSaldoku = new ModalPinTopUpSaldoku();
                modalPinTopUpSaldoku.setArguments(bundle);
                modalPinTopUpSaldoku.show(getSupportFragmentManager(),"topupsaldoku");
                } else {

                    StyleableToast.makeText(getApplicationContext(),"Bukti belum diupload", Toast.LENGTH_LONG,R.style.mytoast2).show();
                }
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1001) {

            if (resultCode == RESULT_OK) {

                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri imageUri = result.getUri();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                   Bitmap photo = bitmap;
                up = 1;


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }
}