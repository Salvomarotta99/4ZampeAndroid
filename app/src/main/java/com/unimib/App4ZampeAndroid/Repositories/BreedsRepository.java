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

    private BreedsListService breedsListDogService;
    private BreedsListService breedsListCatService;
    private BreedsCallback breedsCallback;
    private final BreedDao breedDaoDog;
    private final BreedDao breedDaoCat;
    private long lastUpdate;

    public BreedsRepository(BreedsCallback breedsCallback, Application application) {
        this.breedsListDogService = ServiceLocator.getInstance().getBreedsDogsWithRetrofit();
        this.breedsListCatService = ServiceLocator.getInstance().getBreedsCatsWithRetrofit();
        this.breedsCallback = breedsCallback;
        BreedRoomDatabase dbDog = ServiceLocator.getInstance().getBreedsDaoDog(application);
        this.breedDaoDog = dbDog.breedDao();
        BreedRoomDatabase dbCat = ServiceLocator.getInstance().getBreedsDaoCat(application);
        this.breedDaoCat = dbCat.breedDao();
    }


    @Override
    public void fetchBreedsDog() {

        long currentTime = System.currentTimeMillis();

        if(currentTime - lastUpdate > 1) {

            Call<List<Breed>> call = breedsListDogService.getBreedsList(Costants.THEDOG_API_KEY);

            call.enqueue(new Callback<List<Breed>>() {
                @Override
                public void onResponse(Call<List<Breed>> call, Response<List<Breed>> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        lastUpdate = System.currentTimeMillis();
                        List<Breed> breedList = response.body();
                        saveDataInDatabaseDog(breedList);
                        breedsCallback.onResponse(response.body(), lastUpdate);
                    }
                }

                @Override
                public void onFailure(Call<List<Breed>> call, Throwable t) {
                    breedsCallback.onFailure(t.getMessage());
                }
            });
        } else {
            readDataFromDatabaseDog();
        }
    }

    @Override
    public void fetchBreedsCat() {

        long currentTime = System.currentTimeMillis();

        if(currentTime - lastUpdate > 1) {

            Call<List<Breed>> call = breedsListCatService.getBreedsList(Costants.THECAT_API_KEY);

            call.enqueue(new Callback<List<Breed>>() {
                @Override
                public void onResponse(Call<List<Breed>> call, Response<List<Breed>> response) {
                    if (response.body() != null && response.isSuccessful()) {
                        lastUpdate = System.currentTimeMillis();
                        List<Breed> breedList = response.body();
                        saveDataInDatabaseCat(breedList);
                        breedsCallback.onResponse(response.body(), lastUpdate);
                    }
                }

                @Override
                public void onFailure(Call<List<Breed>> call, Throwable t) {
                    breedsCallback.onFailure(t.getMessage());
                }
            });
        } else {
            readDataFromDatabaseCat();
        }
    }

    private void readDataFromDatabaseDog() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                breedsCallback.onResponse(breedDaoDog.getAllBreed(), lastUpdate);
            }
        };
        new Thread(runnable).start();
    }

    private void saveDataInDatabaseDog(List<Breed> breedList) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                breedDaoDog.deleteAll();
                breedDaoDog.insertBreeds(breedList);
            }
        };
        new Thread(runnable).start();
    }

    private void readDataFromDatabaseCat() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                breedsCallback.onResponse(breedDaoDog.getAllBreed(), lastUpdate);
            }
        };
        new Thread(runnable).start();
    }

    private void saveDataInDatabaseCat(List<Breed> breedList) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                breedDaoCat.deleteAll();
                breedDaoCat.insertBreeds(breedList);
            }
        };
        new Thread(runnable).start();
    }

}
