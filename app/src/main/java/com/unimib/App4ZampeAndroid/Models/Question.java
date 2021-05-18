package com.unimib.App4ZampeAndroid.Models;

public class Question {
    String question;
    String opt1;
    String opt2;
    String opt3;
    String opt4;
    int corrAnswer;

    public Question(String question, String opt1, String opt2, String opt3, String opt4, int corrAnswer) {
        this.question = question;
        this.opt1 = opt1;
        this.opt2 = opt2;
        this.opt3 = opt3;
        this.opt4 = opt4;
        this.corrAnswer = corrAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOpt1() {
        return opt1;
    }

    public void setOpt1(String opt1) {
        this.opt1 = opt1;
    }

    public String getOpt2() {
        return opt2;
    }

    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }

    public String getOpt3() {
        return opt3;
    }

    public void setOpt3(String opt3) {
        this.opt3 = opt3;
    }

    public String getOpt4() {
        return opt4;
    }

    public void setOpt4(String opt4) {
        this.opt4 = opt4;
    }

    public int getCorrAnswer() {
        return corrAnswer;
    }

    public void setCorrAnswer(int corrAnswer) {
        this.corrAnswer = corrAnswer;
    }
}
