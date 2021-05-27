package com.unimib.App4ZampeAndroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.unimib.App4ZampeAndroid.Views.BreedsFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        BottomNavigationView btnNav = findViewById(R.id.bottom_navigation);
        btnNav.setOnNavigationItemSelectedListener(navListener);

        //Setting Home fragment as main
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new BreedsFragment()).commit();

    }

    //Listener
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch(item.getItemId()){
                        case R.id.Razze:
                            selectedFragment = new BreedsFragment();
                            break;

                        case R.id.Donazioni:
                            selectedFragment = new DonazioniFragment();
                            break;

                        case R.id.Giochi:
                            selectedFragment = new CategorieFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_layout,
                                    selectedFragment).commit();
                    return true;
                }
            };

}