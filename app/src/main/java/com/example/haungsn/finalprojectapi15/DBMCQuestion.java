package com.example.haungsn.finalprojectapi15;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Nathan on 3/19/2016.
 */
public class DBMCQuestion extends Question{

    private int id;
    private String question, correct_answer, wrong_answer1, wrong_answer2, wrong_answer3;

    public DBMCQuestion(int id, String question, String correct_answer, String wrong_answer1, String wrong_answer2, String wrong_answer3) {
        this.id = id;
        this.question = question;
        this.correct_answer = correct_answer;
        this.wrong_answer1 = wrong_answer1;
        this.wrong_answer2 = wrong_answer2;
        this.wrong_answer3 = wrong_answer3;
        super.setQuestion(question);
        super.addChoice(correct_answer);
        super.addChoice(wrong_answer1);
        super.addChoice(wrong_answer2);
        super.addChoice(wrong_answer3);
        super.setCorrectAnswer(correct_answer);
    }

    public static ArrayList<DBMCQuestion> deserialize(JSONArray mcJSON) throws JSONException {
        ArrayList<DBMCQuestion> questions = new ArrayList<>();
        for (int i = 0; i < mcJSON.length(); i++) {
            JSONObject temp = mcJSON.getJSONObject(i);
            questions.add(new DBMCQuestion(
                    temp.getInt("id"),
                    temp.getString("question"),
                    temp.getString("correct_answer"),
                    temp.getString("wrong_answer1"),
                    temp.getString("wrong_answer2"),
                    temp.getString("wrong_answer3")

            ));
        }
        return questions;
    }

    public String toSQL() {
        String s = "(" +
                this.id + ",'" +
                this.question.replace("'","''") + "','" +
                this.correct_answer.replace("'","''") + "','" +
                this.wrong_answer1.replace("'","''") + "','" +
                this.wrong_answer2.replace("'","''") + "','" +
                this.wrong_answer3.replace("'","''") + "'" + ")";
        return s;
    }

    public String toString(){
        return "Question: " + this.question +
                ",Correct Answer: " +this.correct_answer+
                ",Wrong 1: "+ this.wrong_answer1+
                ",Wrong 2: "+this.wrong_answer2+
                ",Wrong 3: "+this.wrong_answer3;
    }

    public static String toSQL(ArrayList<DBMCQuestion> questions) {
        String s = "";
        for (int i = 0; i < questions.size(); i++) {
            s += questions.get(i).toSQL();
            if (i != questions.size() - 1) {
                s += ",";
            }
        }
        return s;
    }

    public void shuffleAnswers(){
        /*Shuffle code is courtesy of StackOverflow user Michael Borgwardt:
        * http://stackoverflow.com/questions/4228975/how-to-randomize-arraylist */
        long seed = System.nanoTime();
        Collections.shuffle(this.getChoices(),new Random(seed));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }

    public String getWrong_answer1() {
        return wrong_answer1;
    }

    public void setWrong_answer1(String wrong_answer1) {
        this.wrong_answer1 = wrong_answer1;
    }

    public String getWrong_answer2() {
        return wrong_answer2;
    }

    public void setWrong_answer2(String wrong_answer2) {
        this.wrong_answer2 = wrong_answer2;
    }

    public String getWrong_answer3() {
        return wrong_answer3;
    }

    public void setWrong_answer3(String wrong_answer3) {
        this.wrong_answer3 = wrong_answer3;
    }
}
