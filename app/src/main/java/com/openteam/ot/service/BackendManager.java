package com.openteam.ot.service;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zoz on 05/10/2016.
 */

public class BackendManager {

    public static BackendService getBackendService(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BackendService.ENDPOINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(BackendService.class);
    }
}
