package com.unimib.App4ZampeAndroid.database;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unimib.App4ZampeAndroid.Models.BreedObject;
import com.unimib.App4ZampeAndroid.Models.ImageBreed;

import java.lang.reflect.Type;

public class Converters {

    @TypeConverter
    public static BreedObject fromString(String value) {
        Type object = new TypeToken<BreedObject>() {}.getType();
        return new Gson().fromJson(value, object);
    }
    @TypeConverter
    public static String fromObject(BreedObject object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    @TypeConverter
    public static ImageBreed fromStringImage(String value) {
        Type image = new TypeToken<ImageBreed>() {}.getType();
        return new Gson().fromJson(value, image);
    }
    @TypeConverter
    public static String fromImage(ImageBreed image) {
        Gson gson = new Gson();
        String json = gson.toJson(image);
        return json;
    }

}
