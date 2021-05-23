package com.unimib.App4ZampeAndroid.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.unimib.App4ZampeAndroid.Repositories.IBreedsRepository;

public class BreedsViewModelFactory implements ViewModelProvider.Factory  {

    private final Application application;
    private final IBreedsRepository iBreedsRepository;

    public BreedsViewModelFactory(Application application, IBreedsRepository iBreedsRepository)
    {
        this.application = application;
        this.iBreedsRepository = iBreedsRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BreedsViewModel(application, iBreedsRepository);
    }
}
