package com.unimib.App4ZampeAndroid.repositories;

import com.unimib.App4ZampeAndroid.Models.Breed;

import java.util.List;

public interface BreedsCallback {
    void onResponse(List<Breed> breedList, long lastUpdate);
    void onFailure(String msg);
}
