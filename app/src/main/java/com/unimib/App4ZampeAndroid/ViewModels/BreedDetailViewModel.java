package com.unimib.App4ZampeAndroid.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.unimib.App4ZampeAndroid.Models.Breed;

public class BreedDetailViewModel extends ViewModel {

    private MutableLiveData<Breed> breedDetail ;

    public LiveData<Breed> getBreedDetail() {
        return breedDetail;
    }
}
