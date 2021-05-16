package com.unimib.App4ZampeAndroid.Services;

import com.unimib.App4ZampeAndroid.Utils.Costants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceLocator {

    private static ServiceLocator instance = null;

    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if (instance == null) {
            synchronized(ServiceLocator.class) {
                instance = new ServiceLocator();
            }
        }
        return instance;
    }

    public BreedsListService getBreedsWithRetrofit()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Costants.THEDOG_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(BreedsListService.class);
    }
}
