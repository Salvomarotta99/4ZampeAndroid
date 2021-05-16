package com.unimib.App4ZampeAndroid.Repositories;

import com.unimib.App4ZampeAndroid.Models.Breed;

import java.util.List;

public interface BreedsCallback {
    void onResponse(List<Breed> breedList);
    void onFailure(String msg);
}
