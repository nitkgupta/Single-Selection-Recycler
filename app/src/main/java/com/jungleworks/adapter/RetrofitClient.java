package com.jungleworks.adapter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit retrofit;
    public static String url="https://jsonplaceholder.typicode.com";
    public static Retrofit getInstance(){
        if(retrofit==null){
            retrofit=new retrofit2.Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;

    }
}
