package com.c.dompetabata.Helper;

import com.c.dompetabata.Api.Api;
import com.c.dompetabata.Api.Value;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static Retrofit retrofit = null;

    private static Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Value.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

            return retrofit;

    }

    public static Api getApiServices()
    {
        return  getRetrofit().create(Api.class);
    }
}
