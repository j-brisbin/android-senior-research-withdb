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
    private TextView scoreTextView;
    private TextView statusTextView;

    private CardView questionCard;
    private CardView choice1Card;
    private CardView choice2Card;
    private CardView choice3Card;
    private CardView choice4Card;

    private TextView questionText;
    private TextView choice1Text;
    private TextView choice2Text;
    private TextView choice3Text;
    private TextView choice4Text;

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
        /*Initializes all the TextView elements*/
        questionText = (TextView)findViewById(R.id.question_text);
        choice1Text = (TextView)findViewById(R.id.choice1_text);
        choice2Text = (TextView)findViewById(R.id.choice2_text);
        choice3Text = (TextView)findViewById(R.id.choice3_text);
        choice4Text = (TextView)findViewById(R.id.choice4_text);
        scoreTextView = (TextView)findViewById(R.id.scoreTextView);
        statusTextView = (TextView)findViewById(R.id.statusTextView);
        /*Initializes all CardView elements*/
        questionCard = (CardView)findViewById(R.id.question_card);
        choice1Card = (CardView)findViewById(R.id.choice1_card);
        choice2Card = (CardView)findViewById(R.id.choice2_card);
        choice3Card = (CardView)findViewById(R.id.choice3_card);
        choice4Card = (CardView)findViewById(R.id.choice4_card);
        /*Ties the Typeface variables to the TextViews*/
        questionText.setTypeface(robotoLight);
        choice1Text.setTypeface(robotoBlack);
        choice2Text.setTypeface(robotoBlack);
        choice3Text.setTypeface(robotoBlack);
        choice4Text.setTypeface(robotoBlack);
        scoreTextView.setTypeface(robotoLight);
        statusTextView.setTypeface(robotoLight);
        /*Initializes necessary logic for the trivia game, including the necessary ArrayLists
        * for the Multiple Choice and True/False Questions. Grabs questions from the local
        * database.*/
        multipleChoiceQuestions = dbManager.fetchNMCs(10);
        trueFalseQuestions = dbManager.fetchNTFs(10);
        /*Initialize Logic for the Trivia Game.*/
        triviaLogic = new TriviaLogic(trueFalseQuestions,multipleChoiceQuestions);
        /*Stores the current multiple choice question from the TriviaLogic class, then
        * sets up the relevant TextViews for that question.*/
        scoreTextView.setText("Score: " + triviaLogic.getScore());
        hideCardsAndText();



        choice1Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Checks the current question type then compares the selected answer to the
                * stored correct answer for the current multiple choice or true/false question.*/
                if(triviaLogic.getCurrentQuestionType().equals("MC")){
                    checkSelectedMCAnswer(choice1Text.getText() + "");
                }
                else if(triviaLogic.getCurrentQuestionType().equals("T/F")){
                    checkSelectedTFAnswer(choice1Text.getText() + "");
                }
                scoreTextView.setText("Score: " + triviaLogic.getScore());
            }
        });


        choice2Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Checks the current question type then compares the selected answer to the
                * stored correct answer for the current multiple choice or true/false question.*/
                if(triviaLogic.getCurrentQuestionType().equals("MC")){
                    checkSelectedMCAnswer(choice2Text.getText() + "");
                }
                else if(triviaLogic.getCurrentQuestionType().equals("T/F")){
                    checkSelectedTFAnswer(choice2Text.getText() + "");
                }

                scoreTextView.setText("Score: " + triviaLogic.getScore());
            }
        });

        choice3Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Takes the text from the companion TextView then checks it against the
                * correct answer.*/
                checkSelectedMCAnswer(choice3Text.getText() + "");
            }
        });

        choice4Card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Takes the text from the companion TextView then checks it against the
                * correct answer.*/
                checkSelectedMCAnswer(choice4Text.getText() + "");
            }
        });

        scoreTextView.setText("Score: " + triviaLogic.getScore());
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
        questionCard.setVisibility(View.GONE);
        choice1Card.setVisibility(View.GONE);
        choice2Card.setVisibility(View.GONE);
        choice3Card.setVisibility(View.GONE);
        choice4Card.setVisibility(View.GONE);
        questionText.setVisibility(View.GONE);
        choice1Text.setVisibility(View.GONE);
        choice2Text.setVisibility(View.GONE);
        choice3Text.setVisibility(View.GONE);
        choice4Text.setVisibility(View.GONE);
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
        statusTextView.setText("Question: " + triviaLogic.getCurrentQuestionCount() + " / " +
                triviaLogic.getTotalQuestionCount());
        scoreTextView.setText("Score: " + triviaLogic.getScore());
    }

    public void showCardsAndTextMC(){
        currentMCQuestion.shuffleAnswers();
        questionText.setText(currentMCQuestion.getQuestion());
        choice1Text.setText(currentMCQuestion.getChoices().get(0));
        choice2Text.setText(currentMCQuestion.getChoices().get(1));
        choice3Text.setText(currentMCQuestion.getChoices().get(2));
        choice4Text.setText(currentMCQuestion.getChoices().get(3));
        /*Code is courtesy of StackOverflow:
        http://stackoverflow.com/questions/5052288/how-can-i-hide-show-an-element-when-a-button-is-pressed */
        questionCard.setVisibility(View.VISIBLE);
        choice1Card.setVisibility(View.VISIBLE);
        choice2Card.setVisibility(View.VISIBLE);
        choice3Card.setVisibility(View.VISIBLE);
        choice4Card.setVisibility(View.VISIBLE);
        questionText.setVisibility(View.VISIBLE);
        choice1Text.setVisibility(View.VISIBLE);
        choice2Text.setVisibility(View.VISIBLE);
        choice3Text.setVisibility(View.VISIBLE);
        choice4Text.setVisibility(View.VISIBLE);
    }

    public void showCardsAndTextTF(){
        questionText.setText(currentTFQuestion.getQuestion());
        choice1Text.setText(currentTFQuestion.getChoices().get(0));
        choice2Text.setText(currentTFQuestion.getChoices().get(1));
        /*Code is courtesy of StackOverflow:
        http://stackoverflow.com/questions/5052288/how-can-i-hide-show-an-element-when-a-button-is-pressed */
        questionCard.setVisibility(View.VISIBLE);
        choice1Card.setVisibility(View.VISIBLE);
        choice2Card.setVisibility(View.VISIBLE);
        questionText.setVisibility(View.VISIBLE);
        choice1Text.setVisibility(View.VISIBLE);
        choice2Text.setVisibility(View.VISIBLE);
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
        scoreTextView.setText("Score: " + triviaLogic.getScore());
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
