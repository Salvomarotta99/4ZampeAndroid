package com.unimib.App4ZampeAndroid.Models;

@TABLE = "question"
public class Question {

    @Entity
    @Column = "id"
    private String id;

    private ImageBreed image;
    private Answer correct_answer;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

}
