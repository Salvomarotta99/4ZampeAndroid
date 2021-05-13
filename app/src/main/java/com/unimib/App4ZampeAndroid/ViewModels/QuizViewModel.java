package com.unimib.App4ZampeAndroid.ViewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unimib.App4ZampeAndroid.Models.Breed;
import com.unimib.App4ZampeAndroid.Models.Quiz;

import java.util.List;

public class QuizViewModel {
    private MutableLiveData<List<Quiz>> quizList ;

    public LiveData<List<Quiz>> getQuiz() {
        return quizList;
    }
}

