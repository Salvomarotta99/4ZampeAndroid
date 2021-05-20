package com.unimib.App4ZampeAndroid.Database;

import android.widget.ImageView;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.unimib.App4ZampeAndroid.Models.ImageBreed;

import java.lang.reflect.Type;

public class Converters {

    @TypeConverter
    public static Object fromString(String value) {
        Type object = new TypeToken<Object>() {}.getType();
        return new Gson().fromJson(value, object);
    }
    @TypeConverter
    public static String fromObject(Object object) {
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
