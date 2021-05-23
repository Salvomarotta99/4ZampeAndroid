package com.unimib.App4ZampeAndroid.Repositories;

import androidx.lifecycle.MutableLiveData;

import com.unimib.App4ZampeAndroid.Models.Breed;

import java.util.List;

public interface IBreedsRepository {

 MutableLiveData<List<Breed>> fetchBreedsDog();
 MutableLiveData<List<Breed>> fetchBreedsCat();
}
