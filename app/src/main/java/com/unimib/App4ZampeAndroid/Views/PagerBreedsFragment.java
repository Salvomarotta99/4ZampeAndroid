package com.unimib.App4ZampeAndroid.Views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.unimib.App4ZampeAndroid.Adapters.PagerBreedsAdapter;
import com.unimib.App4ZampeAndroid.R;

public class PagerBreedsFragment extends Fragment {
    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection.
    PagerBreedsAdapter pagerBreedsAdapter;
    ViewPager2 viewPager;

    // tab titles
    private String[] titles = new String[]{"Cani", "Gatti"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pagerBreedsAdapter = new PagerBreedsAdapter(this);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(pagerBreedsAdapter);

        TabLayout tabLayout = view.findViewById(R.id.tabView);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(titles[position])
        ).attach();
    }
}

