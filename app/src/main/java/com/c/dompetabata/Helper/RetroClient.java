package com.c.dompetabata.Helper;

import com.c.dompetabata.Api.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {

    private static Retrofit retrofit = null;
    private static final String base_url = "https://api-mobile-staging.abatapulsa.com/" ;


    private static Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
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
