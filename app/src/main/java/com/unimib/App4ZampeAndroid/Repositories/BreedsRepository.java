package com.unimib.App4ZampeAndroid.Repositories;



import android.app.Application;

import com.unimib.App4ZampeAndroid.Dao.BreedDao;
import com.unimib.App4ZampeAndroid.Database.BreedRoomDatabase;
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
    private final BreedDao breedDao;
    private long lastUpdate;

    public BreedsRepository(BreedsCallback breedsCallback, Application application) {
        this.breedsListService = ServiceLocator.getInstance().getBreedsWithRetrofit();
        this.breedsCallback = breedsCallback;
        BreedRoomDatabase db = ServiceLocator.getInstance().getBreedsDao(application);
        this.breedDao = db.breedDao();
    }


    @Override
    public void fetchBreeds() {

        long currentTime = System.currentTimeMillis();

        if(currentTime - lastUpdate > 1) {

            Call<List<Breed>> call = breedsListService.getBreedsList(Costants.THEDOG_API_KEY);

            call.enqueue(new Callback<List<Breed>>() {
                @Override
                public void onResponse(Call<List<Breed>> call, Response<List<Breed>> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        lastUpdate = System.currentTimeMillis();
                        List<Breed> breedList = response.body();
                        saveDataInDatabase(breedList);
                        breedsCallback.onResponse(response.body(), lastUpdate);
                    }
                }

                @Override
                public void onFailure(Call<List<Breed>> call, Throwable t) {
                    breedsCallback.onFailure(t.getMessage());
                }
            });
        } else {
            readDataFromDatabase();
        }
    }

    private void readDataFromDatabase() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                breedsCallback.onResponse(breedDao.getAllBreed(), lastUpdate);
            }
        };
        new Thread(runnable).start();
    }

    private void saveDataInDatabase(List<Breed> breedList) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                breedDao.deleteAll();
                breedDao.insertBreeds(breedList);
            }
        };
        new Thread(runnable).start();
    }

}
