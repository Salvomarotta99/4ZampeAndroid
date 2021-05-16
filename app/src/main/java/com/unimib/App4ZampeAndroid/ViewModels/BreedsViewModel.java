package com.unimib.App4ZampeAndroid.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.unimib.App4ZampeAndroid.Models.Breed;

import java.util.List;

public class BreedsViewModel extends ViewModel {

    private MutableLiveData<List<Breed>> breedsList;
    //private BreedsRepository breedsRepository;

    /*public void queryRepo(String userId) {
        breedsRepository = BreedsRepository.getInstance();
    }*/

    public MutableLiveData<List<Breed>> getBreedsList() {
        return breedsList;
    }
}
