package com.dmcbproject.dailycollection.apicall;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public  class RetrofitClient {

    public  Retrofit getRetrofit(){
        Retrofit  retrofit = new Retrofit.Builder()
                .baseUrl("https://dmcbproject.com/demo_apps_api/api")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}
