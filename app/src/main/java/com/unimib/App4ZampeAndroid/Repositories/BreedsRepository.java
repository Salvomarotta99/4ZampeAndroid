package com.unimib.App4ZampeAndroid.Repositories;



import com.unimib.App4ZampeAndroid.Models.Breed;
import com.unimib.App4ZampeAndroid.Models.ImageBreed;
import com.unimib.App4ZampeAndroid.Services.BreedImageService;
import com.unimib.App4ZampeAndroid.Services.BreedsListService;
import com.unimib.App4ZampeAndroid.Services.ServiceLocator;
import com.unimib.App4ZampeAndroid.Utils.Costants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreedsRepository implements IBreedsRepository{

    private BreedsListService breedsListService;
    private BreedsCallback breedsCallback;

    public BreedsRepository(BreedsCallback breedsCallback) {
        this.breedsListService = ServiceLocator.getInstance().getBreedsWithRetrofit();
        this.breedsCallback = breedsCallback;
    }


    @Override
    public void fetchBreeds() {
        Call<List<Breed>> call = breedsListService.getBreedsList(Costants.THEDOG_API_KEY);

        call.enqueue(new Callback<List<Breed>>() {
            @Override
            public void onResponse(Call<List<Breed>> call, Response<List<Breed>> response) {
                if(response.body() != null && response.isSuccessful()) {
                    breedsCallback.onResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Breed>> call, Throwable t) {
                breedsCallback.onFailure(t.getMessage());
            }
        });
    }

}
