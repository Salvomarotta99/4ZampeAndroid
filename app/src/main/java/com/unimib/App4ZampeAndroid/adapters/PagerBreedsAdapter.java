package com.unimib.App4ZampeAndroid.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.unimib.App4ZampeAndroid.views.fragment.BreedsCatsFragment;
import com.unimib.App4ZampeAndroid.views.fragment.BreedsFragment;

public class PagerBreedsAdapter extends FragmentStateAdapter {
    public PagerBreedsAdapter(Fragment fragment) {
        super(fragment);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        switch (position) {
            case 0:
                return new BreedsFragment();
            case 1:
                return new BreedsCatsFragment();
        }
        return  new BreedsFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}