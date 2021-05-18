package com.unimib.App4ZampeAndroid;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.unimib.App4ZampeAndroid.Models.Question;

import java.util.ArrayList;
import java.util.List;

public class DogActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView txtQuest;
    private TextView txtTimer;
    private TextView txtNQuest;
    private TextView txtScore;
    private Button opt1,opt2,opt3,opt4;

    private int questNum;
    private CountDownTimer countDown;
    private List<Question> questionList;

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

        opt1.setOnClickListener(this);
        opt2.setOnClickListener(this);
        opt3.setOnClickListener(this);
        opt4.setOnClickListener(this);


        getQuestionList();

    }

    private void getQuestionList() {
        questionList = new ArrayList<>();

        questionList.add(new Question("Question","A","B","C","D",3));
        questionList.add(new Question("Question 2","A","B","C","D",1));
        questionList.add(new Question("Question 3","A","B","C","D",2));
        questionList.add(new Question("Question 4","A","B","C","D",4));
        questionList.add(new Question("Question 5","A","B","C","D",3));
        questionList.add(new Question("Question 6","A","B","C","D",1));
        questionList.add(new Question("Question 7","A","B","C","D",2));
        questionList.add(new Question("Question 8","A","B","C","D",4));


        setQuestion();



    }

    private void setQuestion() {
        txtTimer.setText(String.valueOf(10));
        txtQuest.setText(questionList.get(0).getQuestion());
        opt1.setText(questionList.get(0).getOpt1());
        opt2.setText(questionList.get(0).getOpt2());
        opt3.setText(questionList.get(0).getOpt3());
        opt4.setText(questionList.get(0).getOpt4());

        startTimer();

        questNum = 0;


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
        if(questNum < questionList.size() - 1) {
            questNum++;
            playAnim(txtQuest,0,0);
            playAnim(opt1,0,1);
            playAnim(opt2,0,2);
            playAnim(opt3,0,3);
            playAnim(opt4,0,4);

            txtNQuest.setText(String.valueOf(questNum) + " di 5");

            txtTimer.setText(String.valueOf(10));
            startTimer();
        }else{
            //Passaggio ad activity score

        }
    }

    private void playAnim(View view, final int value,int viewNum) {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if(value == 0) {
                            switch(viewNum) {
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
                                default:
                            }
                           playAnim(view,1,viewNum);
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

            case R.id.bt1 :
                selectedOption = 1;
                break;
            case R.id.bt2 :
                selectedOption = 2;
                break;
            case R.id.bt3 :
                selectedOption = 3;
                break;
            case R.id.bt4 :
                selectedOption = 4;
                break;

            default:

        }
        countDown.cancel();
        checkAnswer(selectedOption, v) ;
    }


    private void checkAnswer(int selectedOption, View view) {
        if(selectedOption == questionList.get(questNum).getCorrAnswer()) {
            switch(selectedOption) {
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
        }else{

            switch(selectedOption) {
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

            switch(questionList.get(questNum).getCorrAnswer()) {
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
        },2000);


    }
}