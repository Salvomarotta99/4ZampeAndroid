package com.unimib.App4ZampeAndroid.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unimib.App4ZampeAndroid.Models.Breed;
import com.unimib.App4ZampeAndroid.Models.Quiz;

import java.util.List;

public class BreedQuizRepository {

    private static BreedQuizRepository instance;

    public static BreedQuizRepository getInstance(){
        if(instance == null){
            instance = new BreedQuizRepository();
        }
        return instance;
    }

    //private WebService thedoapi = WebService.getInstance();


    public LiveData<List<Quiz>> getQuizList(String id) {

        final MutableLiveData<List<Quiz>> quizData =
                new MutableLiveData<>();
        quizData.setValue(
                thedogapi.getQuizList(id)
        );
        return quizData;
    }
}
