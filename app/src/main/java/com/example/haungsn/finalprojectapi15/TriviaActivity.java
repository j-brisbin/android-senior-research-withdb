package com.example.haungsn.finalprojectapi15;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class TriviaActivity extends AppCompatActivity {

    private TriviaLogic triviaLogic;
    private ArrayList<DBMCQuestion> multipleChoiceQuestions;
    private ArrayList<DBTFQuestion> trueFalseQuestions;

    private ArrayList<CardView> triviaCardViews;
    private ArrayList<TextView> triviaTextViews;

    private DBMCQuestion currentMCQuestion;
    private DBTFQuestion currentTFQuestion;

    private Typeface robotoLight;
    private Typeface robotoBlack;

    private DBManager dbManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dbManager = new DBManager(this.getApplicationContext());
        /*Initializes the Typeface variables to be used for the TextViews*/
        robotoBlack = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Black.ttf");
        robotoLight = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
        triviaTextViews = new ArrayList<>();
        triviaCardViews = new ArrayList<>();
        /*Initializes the Typeface variables to be used for the TextViews*/
        robotoBlack = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Black.ttf");
        robotoLight = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Light.ttf");
        /*Initializes all the TextView elements*/
        triviaTextViews.add((TextView)findViewById(R.id.question_text));
        triviaTextViews.add((TextView)findViewById(R.id.choice1_text));
        triviaTextViews.add((TextView)findViewById(R.id.choice2_text));
        triviaTextViews.add((TextView)findViewById(R.id.choice3_text));
        triviaTextViews.add((TextView)findViewById(R.id.choice4_text));
        triviaTextViews.add((TextView)findViewById(R.id.scoreTextView));
        triviaTextViews.add((TextView)findViewById(R.id.statusTextView));

        /*Initializes all CardView elements*/
        triviaCardViews.add((CardView)findViewById(R.id.question_card));
        triviaCardViews.add((CardView)findViewById(R.id.choice1_card));
        triviaCardViews.add((CardView)findViewById(R.id.choice2_card));
        triviaCardViews.add((CardView)findViewById(R.id.choice3_card));
        triviaCardViews.add((CardView)findViewById(R.id.choice4_card));
        /*Ties the Typeface variables to the TextViews*/
        for(int i = 1;i<5;i++){
            triviaTextViews.get(i).setTypeface(robotoBlack);
        }
        triviaTextViews.get(0).setTypeface(robotoLight);
        triviaTextViews.get(5).setTypeface(robotoLight);
        triviaTextViews.get(6).setTypeface(robotoLight);
        /*Initializes necessary logic for the trivia game, including the necessary ArrayLists
        * for the Multiple Choice and True/False Questions and the test questions.*/
        multipleChoiceQuestions = dbManager.fetchNMCs(10);
        trueFalseQuestions = dbManager.fetchNTFs(10);
        /*Initialize Logic for the Trivia Game.*/
        triviaLogic = new TriviaLogic(trueFalseQuestions,multipleChoiceQuestions);
        /*Stores the current multiple choice question from the TriviaLogic class, then
        * sets up the relevant TextViews for that question.*/
        triviaTextViews.get(5).setText("Score: " + triviaLogic.getScore());
        hideCardsAndText();



        for(int i = 1;i<=4;i++){
            final int cardCount = i;
            triviaCardViews.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     /*Checks the current question type then compares the selected answer to the
                * stored correct answer for the current multiple choice or true/false question.*/
                    if(triviaLogic.getCurrentQuestionType().equals("MC")){
                        checkSelectedMCAnswer(triviaTextViews.get(cardCount).getText() + "");
                    }
                    else if(triviaLogic.getCurrentQuestionType().equals("T/F")){
                        checkSelectedTFAnswer(triviaTextViews.get(cardCount).getText() + "");
                    }
                    triviaTextViews.get(5).setText("Score: " + triviaLogic.getScore());
                }
            });
        }

        triviaTextViews.get(5).setText("Score: " + triviaLogic.getScore());
        /*Portrait orientation and bools.xml files are courtesy of StackOverflow:
        * http://stackoverflow.com/questions/9627774/android-allow-portrait-and-landscape-for-tablets-but-force-portrait-on-phone */
        if(getResources().getBoolean(R.bool.portrait_only)){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void hideCardsAndText(){
        /*Code is courtesy of StackOverflow:
        http://stackoverflow.com/questions/5052288/how-can-i-hide-show-an-element-when-a-button-is-pressed */
        for(int i = 0; i< triviaCardViews.size(); i++){
            triviaCardViews.get(i).setVisibility(View.GONE);
        }
        for(int i = 0;i<5;i++){
            triviaTextViews.get(i).setVisibility(View.GONE);
        }
        /*Shows the cards and text depending on the randomly selected question type from the
        * trivia game logic.*/
        String questionStatus = triviaLogic.questionStatus();
        if(questionStatus.equals("MC")){
            currentMCQuestion = triviaLogic.getCurrentMultipleChoiceQuestion();
            showCardsAndTextMC();
        }
        else if(questionStatus.equals("T/F")){
            currentTFQuestion = triviaLogic.getCurrentTrueFalseQuestion();
            showCardsAndTextTF();
        }
        else if(questionStatus.equals("Done")){
            new AlertDialog.Builder(TriviaActivity.this).setTitle("Game Completed!")
                    .setMessage("You finished the game!\nYour score is: " + triviaLogic.getScore())
                    .setPositiveButton("Restart Game",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    triviaLogic.restartGame();
                                    hideCardsAndText();
                                }
                            })
                    .create()
                    .show();
        }
        triviaTextViews.get(6).setText("Question: " + triviaLogic.getCurrentQuestionCount() + " / " +
                triviaLogic.getTotalQuestionCount());
        triviaTextViews.get(5).setText("Score: " + triviaLogic.getScore());
    }

    public void showCardsAndTextMC(){
        triviaTextViews.get(0).setText(currentMCQuestion.getQuestion());
        for(int i = 1;i<=4;i++){
            triviaTextViews.get(i).setText(currentMCQuestion.getChoices().get(i-1));
        }
        /*Code is courtesy of StackOverflow:
        http://stackoverflow.com/questions/5052288/how-can-i-hide-show-an-element-when-a-button-is-pressed */
        for(int i = 0; i< triviaCardViews.size(); i++){
            triviaCardViews.get(i).setVisibility(View.VISIBLE);
        }
        for(int i = 0;i<5;i++){
            triviaTextViews.get(i).setVisibility(View.VISIBLE);
        }
    }

    public void showCardsAndTextTF(){
        triviaTextViews.get(0).setText(currentTFQuestion.getQuestion());
        triviaTextViews.get(1).setText(currentTFQuestion.getChoices().get(0));
        triviaTextViews.get(2).setText(currentTFQuestion.getChoices().get(1));
        /*Code is courtesy of StackOverflow:
        http://stackoverflow.com/questions/5052288/how-can-i-hide-show-an-element-when-a-button-is-pressed */
        triviaCardViews.get(0).setVisibility(View.VISIBLE);
        triviaCardViews.get(1).setVisibility(View.VISIBLE);
        triviaCardViews.get(2).setVisibility(View.VISIBLE);
        triviaTextViews.get(0).setVisibility(View.VISIBLE);
        triviaTextViews.get(1).setVisibility(View.VISIBLE);
        triviaTextViews.get(2).setVisibility(View.VISIBLE);
    }

    public void checkSelectedMCAnswer(String answer){
        currentMCQuestion.setSelectedAnswer(answer);
        if (currentMCQuestion.checkAnswer()) {
            showQuestionCorrectAlertDialog();
            triviaLogic.setScore(triviaLogic.getScore() + 10);
            hideCardsAndText();
        } else {
            showQuestionIncorrectAlertDialog();
            triviaLogic.setScore(triviaLogic.getScore() - 10);
        }
        triviaTextViews.get(5).setText("Score: " + triviaLogic.getScore());
    }

    public void checkSelectedTFAnswer(String answer){
        currentTFQuestion.setSelectedAnswer(answer);
        if (currentTFQuestion.checkAnswer()){
            showQuestionCorrectAlertDialog();
            triviaLogic.setScore(triviaLogic.getScore() + 10);
            hideCardsAndText();
        }
        else {
            showQuestionIncorrectAlertDialog();
            triviaLogic.setScore(triviaLogic.getScore() - 10);
        }
    }

    public void showQuestionCorrectAlertDialog(){
        if(triviaLogic.getCurrentQuestionCount() < triviaLogic.getTotalQuestionCount()){
            /*AlertDialog is generated, corresponding to the user answering a question correctly.*/
            new AlertDialog.Builder(TriviaActivity.this).setTitle("Correct!")
                    .setMessage("You answered this question correctly!\nCongratulations!")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                    .create()
                    .show();
        }

    }

    public void showQuestionIncorrectAlertDialog(){
        if(triviaLogic.getCurrentQuestionCount() < triviaLogic.getTotalQuestionCount()){
            /*AlertDialog is generated, corresponding to the user answering a question incorrectly.*/
            new AlertDialog.Builder(TriviaActivity.this).setTitle("Incorrect")
                    .setMessage("That answer is not correct.\nPlease try again.")
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                    .create()
                    .show();
        }
    }

}
