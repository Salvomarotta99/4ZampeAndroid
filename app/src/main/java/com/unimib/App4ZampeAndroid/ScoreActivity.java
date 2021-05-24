package com.unimib.App4ZampeAndroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {
    Button btnReplay;
    Button btnDone;
    TextView txtScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        txtScore = findViewById(R.id.txtScoreV);
        btnDone = findViewById(R.id.btDone);
        btnReplay = findViewById(R.id.BTReplay);

        String score = getIntent().getStringExtra("SCORE");
        txtScore.setText("Punteggio: "+score);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this,MainActivity.class);
                ScoreActivity.this.startActivity(intent);
                ScoreActivity.this.finish();
            }
        });

        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getIntent().getStringExtra("from").equals("Cat")){
                    Intent intent = new Intent(ScoreActivity.this,CatActivity.class);
                    ScoreActivity.this.startActivity(intent);
                    ScoreActivity.this.finish();
                }else {
                    Intent intent = new Intent(ScoreActivity.this,DogActivity.class);
                    ScoreActivity.this.startActivity(intent);
                    ScoreActivity.this.finish();
                }

            }
        });

    }
}