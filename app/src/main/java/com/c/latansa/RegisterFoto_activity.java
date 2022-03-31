package com.c.latansa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.c.latansa.Api.Api;
import com.c.latansa.Helper.RetroClient;
import com.c.latansa.Model.Responphoto;
import com.c.latansa.sharePreference.Preference;
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

public class RegisterFoto_activity extends AppCompatActivity {

    ImageView uploadKTP, uploadDiri, uploadKTPdanDiri;
    ProgressBar upload, progresktp, progresselfie, progresktpdanselfie;
    String currentPhotoPath;
    private static final int INTENT_REQUEST_CODE = 100;
    private static final int INTENT_REQUEST_CODE_DIRI = 101;
    private static final int INTENT_REQUEST_CODE_DIRIKTP = 102;
    private Bitmap photo;
    String foto1 = "0";
    String foto2 = "0";
    String foto3 = "0";

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_foto_activity);
        String color = Integer.toHexString(getResources().getColor(R.color.green, null)).toUpperCase();
        String color2 = "#" + color.substring(1);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='" + color2 +"'><b>Profil Konter <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        CameraPermission();


        upload = findViewById(R.id.progresupload);
        uploadDiri = findViewById(R.id.uploadDiri);
        uploadKTP = findViewById(R.id.uploadKTP);
        uploadKTPdanDiri = findViewById(R.id.uploadDiridanKTP);

        progresktp = findViewById(R.id.ProgresKTP);
        progresselfie = findViewById(R.id.progressDiri);
        progresktpdanselfie = findViewById(R.id.progressDiridanKTP);

        uploadKTP.setOnClickListener(v -> {

            Intent intent = CropImage.activity()
                    .setAspectRatio(1, 1)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .getIntent(RegisterFoto_activity.this);

            startActivityForResult(intent, INTENT_REQUEST_CODE);

        });

        uploadDiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = CropImage.activity()
                        .setAspectRatio(1, 1)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .getIntent(RegisterFoto_activity.this);

                startActivityForResult(intent, INTENT_REQUEST_CODE_DIRI);

            }
        });

        uploadKTPdanDiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = CropImage.activity()
                        .setAspectRatio(1, 1)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .getIntent(RegisterFoto_activity.this);

                startActivityForResult(intent, INTENT_REQUEST_CODE_DIRIKTP);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri imageUri = result.getUri();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    photo = bitmap;
                    uploadKTP.setImageBitmap(photo);


                } catch (IOException e) {
                    e.printStackTrace();
                }
                uploadFile(photo);

            }
        } else if(requestCode == INTENT_REQUEST_CODE_DIRI){

            if (resultCode == RESULT_OK) {

                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri imageUri = result.getUri();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    photo = bitmap;
                    uploadDiri.setImageBitmap(photo);


                } catch (IOException e) {
                    e.printStackTrace();
                }
               uploadFotoDiri(photo);

            }

        } else if(requestCode == INTENT_REQUEST_CODE_DIRIKTP){
            if (resultCode == RESULT_OK) {

                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                Uri imageUri = result.getUri();
                try {

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    photo = bitmap;
                    uploadKTPdanDiri.setImageBitmap(photo);


                } catch (IOException e) {
                    e.printStackTrace();
                }
               uploadFotoDiridanKTP(photo);

            }



        }


        }



    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    private void uploadFotoDiri(Bitmap photo) {
        Preference.getSharedPreference(getBaseContext());
        String user_id = Preference.getKeyUserId(getBaseContext());

        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), getFileDataFromDrawable(photo));
        MultipartBody.Part body = MultipartBody.Part.createFormData("selfie", "image.jpg", requestFile);

        //creating retrofit object
        Api api = RetroClient.getApiServices();
        progresselfie.setVisibility(View.VISIBLE);
        Call<Responphoto> call = api.uploadImageDiri(body, id);
        call.enqueue(new Callback<Responphoto>() {
            @Override
            public void onResponse(Call<Responphoto> call, Response<Responphoto> response) {
                String code = response.body().getCode();
                progresselfie.setVisibility(View.INVISIBLE);

                if (code.equals("200")){

                    foto1 ="1";
                    StyleableToast.makeText(getApplicationContext(), "Foto Berhasil diupload", Toast.LENGTH_SHORT, R.style.mytoast).show();
                    uploadDiri.setImageDrawable(getDrawable(R.drawable.check));
                } else{

                    Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Responphoto> call, Throwable t) {
                progresselfie.setVisibility(View.GONE);
                StyleableToast.makeText(getApplicationContext(), "Yuk upload lagi,Koneksimu kurang baik", Toast.LENGTH_LONG,R.style.mytoast2).show();

            }
        });

    }

    private void uploadFotoDiridanKTP(Bitmap photo) {
        Preference.getSharedPreference(getBaseContext());
        String user_id = Preference.getKeyUserId(getBaseContext());

        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), user_id);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), getFileDataFromDrawable(photo));
        MultipartBody.Part body = MultipartBody.Part.createFormData("idcardselfie", "image.jpg", requestFile);

        //creating retrofit object
        Api api = RetroClient.getApiServices();
        progresktpdanselfie.setVisibility(View.VISIBLE);
        Call<Responphoto> call = api.uploadImageDiridanKTP(body, id);
        call.enqueue(new Callback<Responphoto>() {
            @Override
            public void onResponse(Call<Responphoto> call, Response<Responphoto> response) {
                String code = response.body().getCode();
                if(code.equals("200")){

                    foto3 = "1";
                    progresktpdanselfie.setVisibility(View.INVISIBLE);
                    StyleableToast.makeText(getApplicationContext(), "Foto Berhasil diupload", Toast.LENGTH_SHORT, R.style.mytoast).show();
                    uploadKTPdanDiri.setImageDrawable(getDrawable(R.drawable.check));
                } else{

                    Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Responphoto> call, Throwable t) {
                progresktpdanselfie.setVisibility(View.GONE);
                StyleableToast.makeText(getApplicationContext(), "Yuk upload lagi,Koneksimu kurang baik", Toast.LENGTH_LONG,R.style.mytoast).show();

            }
        });

    }

    private void uploadFile(Bitmap photo) {
        Preference.getSharedPreference(getBaseContext());
        String user_id = Preference.getKeyUserId(getBaseContext());

        String idd = user_id;
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), idd);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), getFileDataFromDrawable(photo));
        MultipartBody.Part body = MultipartBody.Part.createFormData("idcard", "image.jpg", requestFile);

        //creating retrofit object
        Api api = RetroClient.getApiServices();
        progresktp.setVisibility(View.VISIBLE);
        Call<Responphoto> call = api.uploadImage(body, id);
        call.enqueue(new Callback<Responphoto>() {
            @Override
            public void onResponse(Call<Responphoto> call, Response<Responphoto> response) {
                String code = response.body().getCode();
                progresktp.setVisibility(View.INVISIBLE);
                foto2 ="1";
                if(code.equals("200")){
                    StyleableToast.makeText(getApplicationContext(), "Foto Berhasil diupload", Toast.LENGTH_SHORT, R.style.mytoast).show();
                    uploadKTP.setImageDrawable(getDrawable(R.drawable.check));
                } else{

                    Toast.makeText(getApplicationContext(),response.body().getError(),Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Responphoto> call, Throwable t) {
                progresktp.setVisibility(View.GONE);
                StyleableToast.makeText(getApplicationContext(), "Yuk upload lagi,Koneksimu kurang baik", Toast.LENGTH_LONG,R.style.mytoast).show();


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

    public void SyaratKetentuan(View view) {

        if(foto1 =="1" && foto2 == "1" && foto3 == "1"){

            Intent intent = new Intent(RegisterFoto_activity.this, syaratdanketentuan_activity.class);
            Preference.setTrackRegister(getApplicationContext(),"4");
            startActivity(intent);
        }else {

            StyleableToast.makeText(getApplicationContext(), "Foto tidak boleh kosong", Toast.LENGTH_LONG,R.style.mytoast).show();

        }

    }

    public void CameraPermission() {
        if (ContextCompat.checkSelfPermission(RegisterFoto_activity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(RegisterFoto_activity.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }
    }


}