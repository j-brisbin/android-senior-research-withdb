package com.example.haungsn.finalprojectapi15;

import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by User on 2/14/2016.
 */
public class TriviaLogic {
    private int score;
    private ArrayList<DBTFQuestion> trueFalseQuestions;
    private ArrayList<DBMCQuestion> multipleChoiceQuestions;
    private DBTFQuestion currentTrueFalseQuestion;
    private DBMCQuestion currentMultipleChoiceQuestion;
    private int currentQuestionCount;
    private int totalQuestionCount;
    private int questionCorrectCount;
    private String currentQuestionType;

    public TriviaLogic(ArrayList<DBTFQuestion> trueFalseQuestions,
                       ArrayList<DBMCQuestion> multipleChoiceQuestions){
        this.score = 0;
        this.currentQuestionCount = 0;
        this.questionCorrectCount = 0;
        this.totalQuestionCount = 10;
        this.trueFalseQuestions = trueFalseQuestions;
        this.multipleChoiceQuestions = multipleChoiceQuestions;
        this.currentQuestionType = "";
    }

    public void restartGame(){
        this.score = 0;
        this.currentQuestionCount = 0;
        this.currentQuestionType = "";
    }

    public int getScore() {
        Log.i("currentScore",score + "");
        return score;
    }

    public void setScore(int score) {
        Log.i("currentScore",score + "");
        this.score = score;
    }

    public DBTFQuestion RandomSelectTrueFalse(){
        Random r = new Random();
        int randomSelection = r.nextInt(this.trueFalseQuestions.size());
        Log.i("Random selection TF", randomSelection + "");
        this.currentTrueFalseQuestion =  this.trueFalseQuestions.get(randomSelection);
        return this.currentTrueFalseQuestion;
    }

    public DBMCQuestion RandomSelectMultipleChoice(){
        Random r = new Random();
        int randomSelection = r.nextInt(this.multipleChoiceQuestions.size());
        Log.i("Random selection MC", randomSelection + "");
        this.currentMultipleChoiceQuestion =  this.multipleChoiceQuestions.get(randomSelection);
        //currentMultipleChoiceQuestion.shuffleAnswers();
        return this.currentMultipleChoiceQuestion;
    }

    public String questionStatus(){
        if(currentQuestionCount<totalQuestionCount){
            Random r = new Random();
            int questionSelection = r.nextInt(2);
            Log.i("Question selection",questionSelection + "");
            if(questionSelection == 0){
                this.RandomSelectTrueFalse();
                currentQuestionCount++;
                this.currentQuestionType = "T/F";
                return "T/F";
            }
            else if(questionSelection == 1){
                this.RandomSelectMultipleChoice();
                currentQuestionCount++;
                this.currentQuestionType = "MC";
                return "MC";
            }
        }
        return "Done";
    }

    public int incrementCorrectQuestionCount(){
        if(currentMultipleChoiceQuestion.checkAnswer() ||
                currentTrueFalseQuestion.checkAnswer()){
            return questionCorrectCount++;
        }
        return questionCorrectCount;
    }

    public int getCurrentQuestionCount() {
        return currentQuestionCount;
    }

    public int getTotalQuestionCount() {
        return totalQuestionCount;
    }

    public int getQuestionCorrectCount() {
        return questionCorrectCount;
    }

    public String getCurrentQuestionType() {
        return currentQuestionType;
    }

    public DBTFQuestion getCurrentTrueFalseQuestion() {
        return currentTrueFalseQuestion;
    }

    public DBMCQuestion getCurrentMultipleChoiceQuestion() {
        return currentMultipleChoiceQuestion;
    }
}
