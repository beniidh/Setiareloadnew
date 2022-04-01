package com.c.setiareload.SaldoServer;

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

import androidx.appcompat.app.AppCompatActivity;

import com.c.setiareload.Api.Api;
import com.c.setiareload.Helper.LoadingPrimer;
import com.c.setiareload.Helper.RetroClient;
import com.c.setiareload.Helper.utils;
import com.c.setiareload.R;
import com.c.setiareload.TopUpSaldoku.ResponTopUp;
import com.c.setiareload.sharePreference.Preference;
import com.muddzdev.styleabletoast.StyleableToast;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrasferBankServer extends AppCompatActivity implements ModalPinUPPServer.BottomSheetListeneridUpload {
    TextView saldokubank;
    Button oktransaksi, uploadBuktiTBSaldo;
    int up = 0;
    String primaryid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trasfer_bank_server);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Pembayaran Saldo Server <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        saldokubank = findViewById(R.id.saldokubank);
        uploadBuktiTBSaldo = findViewById(R.id.uploadBuktiTBSaldo);

        uploadBuktiTBSaldo.setOnClickListener(v -> {
            String idprimary = getPrimaryid();
            if(idprimary.isEmpty()){

                Toast.makeText(getApplicationContext(),"Lakukan Pengajuan terlebih dahulu",Toast.LENGTH_SHORT).show();
            }else {

                Intent intent = CropImage.activity()
                        .setAspectRatio(1, 1)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .getIntent(TrasferBankServer.this);

                startActivityForResult(intent, 1001);
            }

        });

        saldokubank.setText(utils.ConvertRP(Preference.getSaldoServer(getApplicationContext())));
        oktransaksi = findViewById(R.id.oktransaksi);
        oktransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString("kode", "bank");
                ModalPinUPPServer modalPinServer = new ModalPinUPPServer();
                modalPinServer.setArguments(bundle);
                modalPinServer.show(getSupportFragmentManager(), "topupsaldoku");

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
                    uploadBukti(photo);


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
    }

    @Override
    public void onButtonClickIdUpload(String id) {
        setPrimaryid(id);

        if (!id.isEmpty()) {
            oktransaksi.setText("Pengajuan Berhasil");
        }
    }

    private void uploadBukti(Bitmap photo) {
        Preference.getSharedPreference(getBaseContext());
        final LoadingPrimer loadingPrimerd = new LoadingPrimer(TrasferBankServer.this);
        loadingPrimerd.startDialogLoading();
        String typee = "proof_saldo_server";
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), typee);
        RequestBody primary_id = RequestBody.create(MediaType.parse("text/plain"), getPrimaryid());
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), getFileDataFromDrawable(photo));
        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", "image.jpg", requestFile);
        String token = "Bearer " + Preference.getToken(getApplicationContext());
        //creating retrofit object
        Api api = RetroClient.getApiServices();
//        progresktp.setVisibility(View.VISIBLE);
        Call<ResponTopUp> call = api.uploadBuktiBayar(token,body, type, primary_id);
        call.enqueue(new Callback<ResponTopUp>() {


            @Override
            public void onResponse(Call<ResponTopUp> call, Response<ResponTopUp> response) {
                String code = response.body().getCode();
//                progresktp.setVisibility(View.INVISIBLE);
//                foto2 ="1";
                if (code.equals("200")) {
                  Toast.makeText(getApplicationContext(), "Foto Berhasil diupload", Toast.LENGTH_SHORT).show();
                    loadingPrimerd.dismissDialog();
//                    uploadKTP.setImageDrawable(getDrawable(R.drawable.check));
                } else {
                    loadingPrimerd.dismissDialog();
                    Toast.makeText(getApplicationContext(), response.body().getError(), Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onFailure(Call<ResponTopUp> call, Throwable t) {
//                progresktp.setVisibility(View.GONE);
                StyleableToast.makeText(getApplicationContext(), "Yuk upload lagi,Koneksimu kurang baik", Toast.LENGTH_LONG, R.style.mytoast).show();
                loadingPrimerd.dismissDialog();

            }
        });


    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public String getPrimaryid() {
        return primaryid;
    }

    public void setPrimaryid(String primaryid) {
        this.primaryid = primaryid;
    }
}