package com.c.dompetabata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Api.Value;
import com.c.dompetabata.Model.Responphoto;
import com.c.dompetabata.sharePreference.Preference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterFoto_activity extends AppCompatActivity {

    ImageView uploadKTP;
    ProgressBar upload;
    Bitmap bitmap;
    String part_image;
    private int REQUEST_GALERY = 9544;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_foto_activity);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#4AB84E'><b>Profil Konter <b></font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);

        upload = findViewById(R.id.progresupload);
        uploadKTP = findViewById(R.id.uploadKTP);
        uploadKTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 0);;

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== 0 && resultCode==RESULT_OK && data!=null)
        {
            Uri path = data.getData();
//            Toast.makeText(getBaseContext(), (CharSequence) path,Toast.LENGTH_LONG).show();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                uploadKTP.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }



            //calling the upload file method after choosing the file



    private void uploadFile() {

        String idd = "0e7d77e0-1855-409f-8708-89d8af35a527";
        String image = BitMapToString(bitmap);


        Map<String, RequestBody> params = new HashMap<>();
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"),idd);
        params.put("idcardselfi", RequestBody.create(MediaType.parse("image/jpg"), image));


        File imagefile = new File("/storage/emulated/0/Download/Corrections 6.jpg");
        RequestBody reqBody = RequestBody.create(MediaType.parse("multipart/form-data"),imagefile);

        MultipartBody.Part partImage = MultipartBody.Part.createFormData("idcardselfi", imagefile.getName(),reqBody);



        //creating retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Value.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        upload.setVisibility(View.VISIBLE);
        Call<Responphoto> call = api.uploadImage(params);


        call.enqueue(new Callback<Responphoto>() {
            @Override
            public void onResponse(Call<Responphoto> call, Response<Responphoto> response) {
                String code = response.body().getError();
                upload.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),code,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Responphoto> call, Throwable t) {

            }
        });


    }
    private String convertToString()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
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

    public void SyaratKetentuan(View view){
        uploadFile();

//        Bundle ekstra =getIntent().getExtras();
//        Intent intent = new Intent(RegisterFoto_activity.this,syaratdanketentuan_activity.class);
////        intent.putExtra("user_id",ekstra.getString("user_id"));
////        intent.putExtra("user_code",ekstra.getString("user_code"));
////        intent.putExtra("phone",ekstra.getString("phone"));
////        intent.putExtra("otp_id",ekstra.getString("otp_id"));
//        Preference.getSharedPreference(getBaseContext());
//
//        StyleableToast.makeText(getApplicationContext(),Preference.getKeyUserId(getBaseContext()), Toast.LENGTH_SHORT).show();
//        StyleableToast.makeText(getApplicationContext(),Preference.getKeyOtpId(getBaseContext()), Toast.LENGTH_SHORT).show();
//
//        startActivity(intent);

    }


}