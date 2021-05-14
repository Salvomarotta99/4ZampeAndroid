package com.unimib.App4ZampeAndroid.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unimib.App4ZampeAndroid.Models.Breed;

import java.util.List;

public class BreedsRepository {

    private static BreedsRepository instance;

    public static BreedsRepository getInstance(){
        if(instance == null){
            instance = new BreedsRepository();
        }
        return instance;
    }

    //private WebService thedoapi = WebService.getInstance();
/*
    public LiveData<List<Breed>> getBreedsList(String id) {

        final MutableLiveData<List<Breed>> breedsData =
                new MutableLiveData<>();
        breedsData.setValue(
               // thedogapi.getBreedsList(id)
        );
        return breedsData;
    }*/
}
