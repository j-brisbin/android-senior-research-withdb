package com.example.haungsn.finalprojectapi15;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private Button newGameButton;
    private Button mainMenuButton;
    private TextView gameOverText;
    private TextView scoreText;
    private Bundle b;
    private int score;
    private Typeface goudyBookletter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        /*Obtains the extras from the Intent in order to get the score obtained in the
        * memory game (MemoryLogic).*/
        b = getIntent().getExtras();
        score = b.getInt("score");
        /*Initializes the necessary elements.*/
        newGameButton = (Button)findViewById(R.id.newGameButton);
        mainMenuButton = (Button)findViewById(R.id.mainMenuButton);
        gameOverText = (TextView)findViewById(R.id.gameOverTextView);
        scoreText = (TextView)findViewById(R.id.scoreText);
        goudyBookletter = Typeface.createFromAsset(getAssets(),"fonts/goudy_bookletter_1911.ttf");
        /*Sets the Typeface for the TextView elements.*/
        gameOverText.setTypeface(goudyBookletter);
        scoreText.setTypeface(goudyBookletter);
        /*Sets the scoreText using the score obtained from the Intent.*/
        scoreText.setText("Your Score: " + score);
        /*Sets OnClickListener for the newGameButton, which starts a new game for the
        * user with the cards and score reset.*/
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this,MemoryGameActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                GameOverActivity.this.startActivity(intent);
                GameOverActivity.this.finish();
            }
        });
        /*Sets OnClickListener for the mainMenu button, which allows the user to go back to the
        * main menu of the application.*/
        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameOverActivity.this,MenuActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                GameOverActivity.this.startActivity(intent);
                GameOverActivity.this.finish();
            }
        });
    }
}
