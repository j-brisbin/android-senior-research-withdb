package com.example.haungsn.finalprojectapi15;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by jbrisbin32 on 2/16/16.
 */
public class Question {

    private int id;
    private String question;
    private String correctAnswer;
    private ArrayList<String> choices;
    private String selectedAnswer;

    public Question(){
        this.question = "Default Question?";
        this.correctAnswer = "Default Correct Answer";
        this.choices = new ArrayList<>();
        this.selectedAnswer = "Default Selected Answer";
    }

    public Question(int id, String question, String correctAnswer, ArrayList<String> choices){
        this.id = id;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.choices = choices;
    }

    public Question(String question){
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        Log.i("selectedAnswer",selectedAnswer);
        this.selectedAnswer = selectedAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addChoice(String choice){
        this.choices.add(choice);
    }

    public boolean checkAnswer(){
        if(selectedAnswer.equals(correctAnswer)){
            Log.i("correct", "Answer correct!");
            return true;
        }
        else{
            Log.i("incorrect", "Answer incorrect!");
            return false;
        }
    }
}
