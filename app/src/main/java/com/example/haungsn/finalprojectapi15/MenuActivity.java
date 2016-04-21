package com.example.haungsn.finalprojectapi15;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {

    private CardView memoryGameCard;
    private CardView timelineCard;
    private CardView triviaGameCard;
    private CardView aboutCard;

    private TextView memoryText;
    private TextView timelineText;
    private TextView triviaText;
    private TextView aboutText;

    private Typeface robotoLight;

    private AlertDialog.Builder loadingDialogBuilder;
    private AlertDialog loadingDialog;

    /*Variables are courtesy of Nathan Haungs*/
    private DBManager dbManager;
    private Context c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        memoryGameCard = (CardView) findViewById(R.id.memory_game_card);
        timelineCard = (CardView) findViewById(R.id.timeline_card);
        triviaGameCard = (CardView) findViewById(R.id.trivia_game_card);
        aboutCard = (CardView) findViewById(R.id.about_card);

        memoryText = (TextView) findViewById(R.id.memory_game_text);
        timelineText = (TextView) findViewById(R.id.timeline_text);
        triviaText = (TextView) findViewById(R.id.trivia_game_text);
        aboutText = (TextView) findViewById(R.id.about_text);

        robotoLight = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

        memoryText.setTypeface(robotoLight);
        timelineText.setTypeface(robotoLight);
        triviaText.setTypeface(robotoLight);
        aboutText.setTypeface(robotoLight);

        memoryGameCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MemoryGameActivity.class);
                startActivity(intent);
            }
        });

        timelineCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TimelineActivity.class);
                startActivity(intent);
            }
        });

        triviaGameCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TriviaActivity.class);
                startActivity(intent);
            }
        });

        aboutCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AboutActivity.class);
                startActivity(intent);
            }
        });

        /*Portrait orientation and bools.xml files are courtesy of StackOverflow:
        * http://stackoverflow.com/questions/9627774/android-allow-portrait-and-landscape-for-tablets-but-force-portrait-on-phone */
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        /*Builds the loadingDialog, then creates it, to be passed into CountDownTimers */
        loadingDialogBuilder = new AlertDialog.Builder(this);
        loadingDialogBuilder.setTitle("Updating Content")
                .setMessage("Application content is being updated.\nPlease wait...");
        loadingDialog = loadingDialogBuilder.create();

        /*Database code is courtesy of Nathan Haungs*/
        c = this;
        dbManager = new DBManager(c);
        dbManager.fetchUpdates();
        /*Additional timer created for fetching the images.*/
        final CountDownTimer timer = new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                loadingDialog.show();
                if (dbManager.getRequestPending() == 0) { //all requests are done
                    this.cancel();
                    this.onFinish();
                }
            }

            public void onFinish() {
                if (dbManager.getRequestPending() > 0) { // requests not done yet
                    Log.d("Activity:", "Fetch updates still working");
                    new AlertDialog.Builder(MenuActivity.this).setTitle("Content Update")
                            .setMessage("Content updates are taking longer than expected." +
                                    "\nPlease wait...")
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                            .create()
                            .show();
                    this.start();
                }
                else if (dbManager.getRequestPending() < 0) { //requests have error
                    Log.d("Activity:", "Fetch updates error and done!");
                    new AlertDialog.Builder(MenuActivity.this).setTitle("Error Updating Content")
                            .setMessage("There was an error updating the content.\nSome content" +
                                    "may not display and the application may not function properly.\n" +
                                    "Make sure your device is connected to the Internet.")
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
                loadingDialog.dismiss();
            }
        };
        /*Initial anonymous CountDownTimer for getting content updates.*/
        new CountDownTimer(3000, 1000) {
            public void onTick(long millisUntilFinished) {
                loadingDialog.show();
                if (dbManager.getRequestPending() == 0) { //all requests are done
                    this.cancel();
                    this.onFinish();
                }
            }

            public void onFinish() {
                if (dbManager.getRequestPending() > 0) { // requests not done yet
                    Log.d("Activity:", "Fetch updates still working");
                    new AlertDialog.Builder(MenuActivity.this).setTitle("Content Update")
                            .setMessage("Content updates are taking longer than expected." +
                                    "\nPlease wait...")
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                            .create()
                            .show();
                    this.start();
                } else if (dbManager.getRequestPending() < 0) { //requests have error
                    Log.d("Activity:", "Fetch updates error and done!");
                    new AlertDialog.Builder(MenuActivity.this).setTitle("Error Updating Content")
                            .setMessage("There was an error updating the content.\nSome content" +
                                    "may not display and the application may not function properly.\n" +
                                    "Make sure your device is connected to the Internet.")
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                            .create()
                            .show();
                    //error
                } else {
                    Log.d("Activity:", "Fetch updates success and done!");
                    dbManager.fetchCardImages(); //must be called after all inserts finish
                    timer.start(); //now go to check for image updates
                }
            }
        }.start();
    }

}
