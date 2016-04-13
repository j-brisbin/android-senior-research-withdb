package com.example.haungsn.finalprojectapi15;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nathan on 3/19/2016.
 */
public class DBTFQuestion extends Question{

    private int id;
    boolean is_true;
    private String question;

    public DBTFQuestion(int id, Boolean is_true, String question) {
        this.id = id;
        this.is_true = is_true;
        this.question = question;
        super.setQuestion(question);
        super.addChoice(Boolean.toString(true));
        super.addChoice(Boolean.toString(false));
        super.setCorrectAnswer(Boolean.toString(is_true));
    }

    public static ArrayList<DBTFQuestion> deserialize(JSONArray tfJSON) throws JSONException {
        ArrayList<DBTFQuestion> questions = new ArrayList<>();
        for (int i = 0; i < tfJSON.length(); i++) {
            JSONObject temp = tfJSON.getJSONObject(i);
            boolean it = temp.getString("is_true").equals("1");
            questions.add(new DBTFQuestion(
                    temp.getInt("id"),
                    it,
                    temp.getString("question")

            ));
        }
        return questions;
    }

    public String toSQL() {
        int true_number = 0;
        if(this.is_true){
            true_number = 1;
        }
        String s = "(" +
                this.id + ",'" +
                true_number + "' ,'" +
                this.question.replace("'","''") + "'" + ")";
        return s;
    }

    public static String toSQL(ArrayList<DBTFQuestion> questions) {
        String s = "";
        for (int i = 0; i < questions.size(); i++) {
            s += questions.get(i).toSQL();
            if (i != questions.size() - 1) {
                s += ",";
            }
        }
        return s;
    }

    public String toString(){
        return "Question: " + this.question +
                ",True: " +this.is_true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean is_true() {
        return is_true;
    }

    public void setIs_true(boolean is_true) {
        this.is_true = is_true;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
