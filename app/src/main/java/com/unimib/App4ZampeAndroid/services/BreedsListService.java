package com.unimib.App4ZampeAndroid.services;

import com.unimib.App4ZampeAndroid.Models.Breed;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface BreedsListService {
    @GET("breeds")
    Call<List<Breed>> getBreedsList(@Header("Authorization") String api_key );
}
