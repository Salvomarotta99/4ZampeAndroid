package com.unimib.App4ZampeAndroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.unimib.App4ZampeAndroid.Models.Question;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DogActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txtQuest;
    private TextView txtTimer;
    private TextView txtNQuest;
    private TextView txtScore;
    private Button opt1, opt2, opt3, opt4;



    private ImageView imgView;

    private int questNum;
    private int score;

    private int[] PlaceholderList = new int[]{R.drawable.placeholderdog1,
                                              R.drawable.placeholderdog2,R.drawable.placeholderdog3};



    private CountDownTimer countDown;
    private List<Question> questionListTOT;
    private List<Question> questionList;
    private int imageId = R.drawable.mrdog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog);

        txtQuest = findViewById(R.id.txtQuest);
        txtTimer = findViewById(R.id.txtTimer);
        txtNQuest = findViewById(R.id.txtNQuest);
        txtScore = findViewById(R.id.txtScore);

        opt1 = findViewById(R.id.bt1);
        opt2 = findViewById(R.id.bt2);
        opt3 = findViewById(R.id.bt3);
        opt4 = findViewById(R.id.bt4);


        imgView = findViewById(R.id.QTimg);

        opt1.setOnClickListener(this);
        opt2.setOnClickListener(this);
        opt3.setOnClickListener(this);
        opt4.setOnClickListener(this);


        getQuestionList();

    }
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private void getQuestionList() {
        questionListTOT = new ArrayList<>();
        questionList  = new ArrayList<>();
        questionListTOT.addAll(SplashActivity.dbrepo.getDogList());
        Collections.shuffle(questionListTOT);
        for (int i = 0;i<5;i++) {
            questionList.add(questionListTOT.get(i));
        }
        setQuestion();

    }






    private void setQuestion() {

        txtTimer.setText(String.valueOf(10));
        txtQuest.setText(questionList.get(0).getQuestion());
        opt1.setText(questionList.get(0).getOpt1());
        opt2.setText(questionList.get(0).getOpt2());
        opt3.setText(questionList.get(0).getOpt3());
        opt4.setText(questionList.get(0).getOpt4());

        //Picasso.get().load(R.drawable.mrdog).into(imgView);
        if(questionList.get(0).getImgSrc().contentEquals("nullD")){
            final int random = new Random().nextInt(3);
            Picasso.get().load(PlaceholderList[random]).into(imgView);
        }else{
            Picasso.get().load(questionList.get(0).getImgSrc()).into(imgView);

        }
        startTimer();

        questNum = 0;
        score = 0;
        txtScore.setText("Score: " + score);
        txtNQuest.setText(String.valueOf(questNum + 1) + " di " + questionList.size());


    }

    private void startTimer() {
        countDown = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtTimer.setText(String.valueOf(millisUntilFinished / 1000));

            }

            @Override
            public void onFinish() {
                changeQuestion();
            }
        };
        countDown.start();
    }

    private void changeQuestion() {
        if (questNum < questionList.size() - 1) {
            questNum++;

            opt1.setClickable(true);
            opt2.setClickable(true);
            opt3.setClickable(true);
            opt4.setClickable(true);

            playAnim(txtQuest, 0, 0);
            playAnim(opt1, 0, 1);
            playAnim(opt2, 0, 2);
            playAnim(opt3, 0, 3);
            playAnim(opt4, 0, 4);
            playAnim(imgView, 0, 5);

            txtNQuest.setText(String.valueOf(questNum + 1) + " di " + questionList.size());


            txtTimer.setText(String.valueOf(10));
            startTimer();
        } else {
            Intent intent = new Intent(DogActivity.this, ScoreActivity.class);
            intent.putExtra("SCORE", String.valueOf(score));
            intent.putExtra("from", "Dog");
            startActivity(intent);
            DogActivity.this.finish();

        }
    }

    private void playAnim(View view, final int value, int viewNum) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (value == 0) {
                            switch (viewNum) {
                                case 0:
                                    txtQuest.setText(questionList.get(questNum).getQuestion());
                                    break;
                                case 1:
                                    opt1.setText(questionList.get(questNum).getOpt1());
                                    break;
                                case 2:
                                    opt2.setText(questionList.get(questNum).getOpt2());
                                    break;
                                case 3:
                                    opt3.setText(questionList.get(questNum).getOpt3());
                                    break;
                                case 4:
                                    opt4.setText(questionList.get(questNum).getOpt4());
                                    break;
                                case 5:
                                   if(questionList.get(questNum).getImgSrc().contentEquals("nullD")){
                                       final int random = new Random().nextInt(3);
                                       Picasso.get().load(PlaceholderList[random]).into(imgView);
                                }else{
                                       Picasso.get().load(questionList.get(questNum).getImgSrc()).into(imgView);

                                }
                                    break;

                                default:
                            }
                            playAnim(view, 1, viewNum);
                        }
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

    }


    @Override
    public void onClick(View v) {
        int selectedOption = 0;
        switch (v.getId()) {

            case R.id.bt1:
                selectedOption = 1;
                break;
            case R.id.bt2:
                selectedOption = 2;
                break;
            case R.id.bt3:
                selectedOption = 3;
                break;
            case R.id.bt4:
                selectedOption = 4;
                break;

            default:

        }
        countDown.cancel();
        checkAnswer(selectedOption, v);
    }


    private void checkAnswer(int selectedOption, View view) {
        opt1.setClickable(false);
        opt2.setClickable(false);
        opt3.setClickable(false);
        opt4.setClickable(false);
        if (selectedOption == questionList.get(questNum).getCorrAnswer()) {

            score++;
            txtScore.setText("Score: " + score);
            switch (selectedOption) {
                case 1:
                    opt1.setBackgroundColor(Color.GREEN);
                    break;
                case 2:
                    opt2.setBackgroundColor(Color.GREEN);
                    break;
                case 3:
                    opt3.setBackgroundColor(Color.GREEN);
                    break;
                case 4:
                    opt4.setBackgroundColor(Color.GREEN);
                    break;
                default:
            }
        } else {

            switch (selectedOption) {
                case 1:
                    opt1.setBackgroundColor(Color.RED);
                    break;
                case 2:
                    opt2.setBackgroundColor(Color.RED);
                    break;
                case 3:
                    opt3.setBackgroundColor(Color.RED);
                    break;
                case 4:
                    opt4.setBackgroundColor(Color.RED);
                    break;
                default:
            }

            switch (questionList.get(questNum).getCorrAnswer()) {
                case 1:
                    opt1.setBackgroundColor(Color.GREEN);
                    break;
                case 2:
                    opt2.setBackgroundColor(Color.GREEN);
                    break;
                case 3:
                    opt3.setBackgroundColor(Color.GREEN);
                    break;
                case 4:
                    opt4.setBackgroundColor(Color.GREEN);
                    break;
                default:
            }


        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                opt1.setBackgroundColor(Color.WHITE);
                opt2.setBackgroundColor(Color.WHITE);
                opt3.setBackgroundColor(Color.WHITE);
                opt4.setBackgroundColor(Color.WHITE);
                changeQuestion();

            }
        }, 2000);


    }

}


