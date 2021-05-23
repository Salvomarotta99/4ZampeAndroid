package com.unimib.App4ZampeAndroid.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.unimib.App4ZampeAndroid.Models.Breed;
import com.unimib.App4ZampeAndroid.Repositories.IBreedsRepository;

import java.util.List;

public class BreedsViewModel extends AndroidViewModel {

    private MutableLiveData<List<Breed>> breedsDogList;
    private MutableLiveData<List<Breed>> breedsCatList;
    private IBreedsRepository iBreedsRepository;
    private Application application;

    public BreedsViewModel(@NonNull Application application) {
        super(application);
    }

    public BreedsViewModel(@NonNull Application application, IBreedsRepository iBreedsRepository) {
        super(application);
        this.iBreedsRepository = iBreedsRepository;
    }
    //private BreedsRepository breedsRepository;

    /*public void queryRepo(String userId) {
        breedsRepository = BreedsRepository.getInstance();
    }*/

    public LiveData<List<Breed>> getBreedsListDog() {
        if(breedsDogList == null)
        {
         loadBreedsDog();
        }
        return breedsDogList;

    }

    private void loadBreedsDog()
    {
        breedsDogList = iBreedsRepository.fetchBreedsDog();
    }

    public LiveData<List<Breed>> getBreedsListCat() {
        if(breedsCatList == null)
        {
            loadBreedsCat();
        }
        return breedsCatList;

    }

    private void loadBreedsCat()
    {
        breedsCatList = iBreedsRepository.fetchBreedsCat();
    }
}
