package com.c.dompetabata.Api;

import android.renderscript.Sampler;

import com.c.dompetabata.Helper.Respon;
import com.c.dompetabata.Helper.ResponK;
import com.c.dompetabata.Helper.ResponKe;
import com.c.dompetabata.Helper.ResponPost;
import com.c.dompetabata.Helper.Responkel;
import com.c.dompetabata.Model.MOtpVerif;
import com.c.dompetabata.Model.MRegisData;
import com.c.dompetabata.Model.MRegister;
import com.c.dompetabata.Model.Mlogin;
import com.c.dompetabata.Model.MsetPIN;
import com.c.dompetabata.Model.Responphoto;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface Api {

    @Headers("Content-Type: application/json")
    @POST("signin")
    Call<Mlogin> Login(@Body Mlogin mlogin);

    @Headers("Content-Type: application/json")
    @POST("signup")
    Call<MRegister> Register(@Body MRegister mRegister);

    @Headers("Content-Type: application/json")
    @POST("otp-email")
    Call<MRegisData> SendOTP(@Body MRegisData mRegisData);

    @Headers("Content-Type: application/json")
    @POST("otp-verify")
    Call<MOtpVerif> verifOTP(@Body MOtpVerif mOtpVerif);

    @Multipart
    @POST("ekyc-idcard")
    Call<Responphoto> uploadImage(@Part MultipartBody.Part image,@Part("id") RequestBody id);

    @Multipart
    @POST("ekyc-selfie")
    Call<Responphoto> uploadImageDiri(@Part MultipartBody.Part image,@Part("id") RequestBody id);

    @Multipart
    @POST("ekyc-idcardselfie")
    Call<Responphoto> uploadImageDiridanKTP(@Part MultipartBody.Part image,@Part("id") RequestBody id);

    @GET("all-province?next=39")
    Call<Respon> getAllProvinsi();

    @POST("set-pin")
    Call<MsetPIN> SetPIN(@Header("X-Signature")String token, @Body MsetPIN msetPIN);

    @GET("regencies/province/{id}")
    Call<ResponK> getAllKabupaten(@Path("id") long id);

    @GET("districts/regencies/{id}")
    Call<ResponKe> getAllKecamatan(@Path("id") long id);

    @GET("sub-districts/districts/{id}")
    Call<Responkel> getAllKelurahan(@Path("id") long id);

    @GET("postal-code/sub-districts/{id}")
    Call<ResponPost> getAllPost(@Path("id") long id);

}
