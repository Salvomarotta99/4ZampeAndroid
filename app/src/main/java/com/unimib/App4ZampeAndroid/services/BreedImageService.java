package com.unimib.App4ZampeAndroid.services;

import com.unimib.App4ZampeAndroid.Models.ImageBreed;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface BreedImageService {
    @GET("images/{image_id}")
    Call<ImageBreed> getImageBreed(@Header("Authorization") String api_key, @Path("image_id") String breed);
}
