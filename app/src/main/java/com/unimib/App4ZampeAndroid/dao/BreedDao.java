package com.unimib.App4ZampeAndroid.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.unimib.App4ZampeAndroid.Models.Breed;

import java.util.List;

@Dao
public interface BreedDao {

    @Insert
    void insertBreeds(List<Breed> breeds);

    @Query("SELECT * FROM breeds")
    List<Breed> getAllBreed();

    @Query("DELETE FROM breeds")
    void deleteAll();
}
