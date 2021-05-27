package com.unimib.App4ZampeAndroid.Services;

import com.unimib.App4ZampeAndroid.Models.Breed;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface BreedsListService {
    @GET("breeds")
    Call<List<Breed>> getBreedsList(@Header("Authorization") String api_key );
}
