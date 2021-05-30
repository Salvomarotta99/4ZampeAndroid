package com.unimib.App4ZampeAndroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.unimib.App4ZampeAndroid.Views.BreedDetailFragment;
import com.unimib.App4ZampeAndroid.Views.PagerBreedsFragment;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth  fAuth = FirebaseAuth.getInstance();
    FirebaseUser fUser = fAuth.getCurrentUser();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //back button in toolbar
        getSupportActionBar().setTitle("4Zampe");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        BottomNavigationView btnNav = findViewById(R.id.bottom_navigation);
        btnNav.setOnNavigationItemSelectedListener(navListener);

        //Setting Home fragment as main
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new PagerBreedsFragment()).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);


        return true;

    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem itemU = menu.findItem(R.id.action_user_profile);
        MenuItem itemLO = menu.findItem(R.id.action_logout);
        MenuItem itemLI = menu.findItem(R.id.action_login);
        MenuItem itemR = menu.findItem(R.id.action_registration);

        itemU.setVisible(false);
        itemLO.setVisible(false);
        itemLI.setVisible(false);
        itemR.setVisible(false);

        if (fUser!=null) {
            itemU.setVisible(true);
            itemLO.setVisible(true);
        }else{
            itemLI.setVisible(true);
            itemR.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_user_profile:
                startActivity(new Intent(getApplicationContext(), UserActivity.class));
                return true;

            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            case R.id.action_login:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                return true;

            case R.id.action_registration:

                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
                return true;

            //item for back button
            case android.R.id.home:

                Fragment selectedFragment = new PagerBreedsFragment();
                this.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_layout,
                            selectedFragment).commit();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //startActivity(new Intent(getApplicationContext(), UserActivity.class));


    //Listener
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch(item.getItemId()){
                        case R.id.Razze:
                            selectedFragment = new PagerBreedsFragment();
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