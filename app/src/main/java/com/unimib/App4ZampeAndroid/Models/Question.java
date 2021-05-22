package com.unimib.App4ZampeAndroid.Models;


public class Question {


    private String id;
    String name;
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


}
