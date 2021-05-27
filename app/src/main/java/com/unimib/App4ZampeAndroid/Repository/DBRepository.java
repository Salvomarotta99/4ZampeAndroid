package com.unimib.App4ZampeAndroid.Repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.unimib.App4ZampeAndroid.Models.Question;

import java.util.ArrayList;
import java.util.List;

public class DBRepository {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private List<Question> dogList;
    private List<Question> catList;
    private List<Question> ausListD;
    private List<Question> ausListC;

    public DBRepository() {
        this.dogList = new ArrayList<>();
        this.catList = new ArrayList<>();
    }


    public void fetchDogQuest() {

        String TAG = "DogActivityFirebaseEr";
        ausListD = new ArrayList<>();
        db.collection("QuestionDog")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int i = 0;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ausListD.add(new Question(document.getString("question"),
                                        document.getString("A"),
                                        document.getString("B"),
                                        document.getString("C"),
                                        document.getString("D"),
                                        document.getString("imgSrc"),
                                        Integer.valueOf(document.getString("answerCor"))));

                            }
                            setDogQuestList(ausListD);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void setDogQuestList(List<Question> ausListD) {
        this.dogList.addAll(ausListD);
    }


    public void fetchCatQuest() {

        String TAG = "DogActivityFirebaseEr";
        ausListC = new ArrayList<>();
        db.collection("QuestionCat")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        int i = 0;
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ausListC.add(new Question(document.getString("question"),
                                        document.getString("A"),
                                        document.getString("B"),
                                        document.getString("C"),
                                        document.getString("D"),
                                        document.getString("imgSrc"),
                                        Integer.valueOf(document.getString("answerCor"))));

                            }
                            setCatQuestList(ausListC);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private void setCatQuestList(List<Question> ausListC) {
        this.catList.addAll(ausListC);
    }

    public List<Question> getDogList() {
        return dogList;
    }

    public List<Question> getCatList() {
        return catList;
    }

}
