package com.unimib.App4ZampeAndroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.unimib.App4ZampeAndroid.Repository.DBRepository;

public class SplashActivity extends AppCompatActivity {

    public static DBRepository dbrepo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        dbrepo = new DBRepository();

        dbrepo.fetchDogQuest();
        dbrepo.fetchCatQuest();

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               Intent i = new Intent(SplashActivity.this, LoginActivity.class);
               startActivity(i);

               finish();
           }

       },3000);


    }
}