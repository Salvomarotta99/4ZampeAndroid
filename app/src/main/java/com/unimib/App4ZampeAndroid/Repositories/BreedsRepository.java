package com.unimib.App4ZampeAndroid.Repositories;



import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.unimib.App4ZampeAndroid.Dao.BreedDao;
import com.unimib.App4ZampeAndroid.Database.BreedRoomDatabase;
import com.unimib.App4ZampeAndroid.Models.Breed;
import com.unimib.App4ZampeAndroid.Models.ImageBreed;
import com.unimib.App4ZampeAndroid.Services.BreedImageService;
import com.unimib.App4ZampeAndroid.Services.BreedsListService;
import com.unimib.App4ZampeAndroid.Services.ServiceLocator;
import com.unimib.App4ZampeAndroid.Utils.Costants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreedsRepository implements IBreedsRepository{

    private BreedsListService breedsListDogService;
    private BreedsListService breedsListCatService;
    private final BreedDao breedDaoDog;
    private final BreedDao breedDaoCat;
    private long lastUpdate;
    private MutableLiveData<List<Breed>> breedsDogLiveData;
    private MutableLiveData<List<Breed>> breedsCatLiveData;

    public BreedsRepository(Application application, long lastUpdate) {
        this.breedsListDogService = ServiceLocator.getInstance().getBreedsDogsWithRetrofit();
        this.breedsListCatService = ServiceLocator.getInstance().getBreedsCatsWithRetrofit();
        BreedRoomDatabase dbDog = ServiceLocator.getInstance().getBreedsDaoDog(application);
        this.breedDaoDog = dbDog.breedDao();
        BreedRoomDatabase dbCat = ServiceLocator.getInstance().getBreedsDaoCat(application);
        this.breedDaoCat = dbCat.breedDao();
        this.lastUpdate = lastUpdate;
        this.breedsDogLiveData = new MutableLiveData<>();
        this.breedsCatLiveData = new MutableLiveData<>();
    }


    @Override
    public MutableLiveData<List<Breed>> fetchBreedsDog() {

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
                        breedsDogLiveData.postValue(response.body());

                    }
                }

                @Override
                public void onFailure(Call<List<Breed>> call, Throwable t) {
                    //Error
                }
            });
        } else {
            readDataFromDatabaseDog();
        }
        return breedsDogLiveData;
    }

    @Override
    public  MutableLiveData<List<Breed>> fetchBreedsCat() {

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
                        breedsCatLiveData.postValue(response.body());
                    }
                }

                @Override
                public void onFailure(Call<List<Breed>> call, Throwable t) {
                   //Error
                }
            });
        } else {
            readDataFromDatabaseCat();
        }
        return breedsCatLiveData;
    }

    private void readDataFromDatabaseDog() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<Breed> breedList = breedDaoDog.getAllBreed();
                breedsDogLiveData.postValue(breedList);
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
                List<Breed> breedList = breedDaoCat.getAllBreed();
                breedsCatLiveData.postValue(breedList);
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
