package com.c.dompetabata.Api;

import android.renderscript.Sampler;

import com.c.dompetabata.Model.MRegister;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {

    @Headers("Content-Type: application/json")
    @POST("signin")
    Call<Value> Login(@Body Value value);

    @Headers("Content-Type: application/json")
    @POST("signup")
    Call<MRegister> Register(@Body MRegister mRegister);
}
