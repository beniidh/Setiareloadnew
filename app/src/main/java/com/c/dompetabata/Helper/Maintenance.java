package com.c.dompetabata.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Maintenance {


    // izin
    //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
//                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                    100);
//
//            return;}



    //use file provider upload

    //                                        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                                            // Create the File where the photo should go
//                                            photoFile = null;
//                                            try {
//                                                photoFile = createImageFile();
//                                            } catch (IOException ex) {
//
//                                            }
//                                            // Continue only if the File was successfully created
//                                            if (photoFile != null) {
//                                                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
//                                                        "com.c.dompetabata.fileprovider",
//                                                        photoFile);
//                                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                                                startActivityForResult(takePictureIntent, INTENT_REQUEST_CODE);
//                                            }
//                                        }


    //take gallery


////                                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                                        intent.setType("image/jpeg");
//                                        startActivityForResult(intent, INTENT_REQUEST_CODE_GALERRY);

    //                photo = (Bitmap) data.getExtras().get("data");


    //input stream



//                try {
//                    InputStream is = getContentResolver().openInputStream(uri);
//
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
    //                Uri uri = getImageUri(getBaseContext(),photo);


//
//    public Uri getImageUri(Context inContext, Bitmap inImage) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }



//
//    public byte[] getBytes(InputStream is) throws IOException {
//        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
//
//        int buffSize = 1024;
//        byte[] buff = new byte[buffSize];
//
//        int len = 0;
//        while ((len = is.read(buff)) != -1) {
//            byteBuff.write(buff, 0, len);
//        }
//
//        return byteBuff.toByteArray();
//    }


//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        currentPhotoPath = image.getAbsolutePath();
//        return image;
//    }


}
