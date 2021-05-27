package com.unimib.App4ZampeAndroid.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.unimib.App4ZampeAndroid.Dao.BreedDao;
import com.unimib.App4ZampeAndroid.Models.Breed;

@Database(entities = {Breed.class}, version = 1)

@TypeConverters({Converters.class})
public abstract class BreedRoomDatabase extends RoomDatabase {
    public static volatile BreedRoomDatabase INSTANCE;
    public abstract BreedDao breedDao();

    public static BreedRoomDatabase getDatabase(final Context context)
    {
        if(INSTANCE == null)
        {
            synchronized (BreedRoomDatabase.class){
                if(INSTANCE == null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BreedRoomDatabase.class, "breeds_database").build();
                }
            }
        }
        return INSTANCE;
    }


}
