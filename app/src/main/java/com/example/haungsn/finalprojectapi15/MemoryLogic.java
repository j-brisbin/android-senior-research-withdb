package com.example.haungsn.finalprojectapi15;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.util.Log;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by User on 11/28/2015.
 */

public class MemoryLogic {
    private int cardSelectedCounter;
    private int score;
    private Bitmap firstCardBitmap; /*Resource ID of the selected card*/
    private Bitmap secondCardBitmap;
    private String firstCardName;
    private String secondCardName;
    private Card firstCard;
    private boolean busy;
    private Card secondCard;
    private int canvasWidth, canvasHeight;
    private Context context;
    private ArrayList<DBCard> cardInformation;
    private ArrayList<Card> cardTable;
    private int cardCount = 18;
    private int cardsRemoved;
    private boolean gameOver = false;
    private Random randomCard;
    private int randomCardSelection;
    private DBManager dbManager;

    public MemoryLogic(Context context, int width, int height, int screenSize) throws FileNotFoundException{
        this.context = context;
        this.busy = false;
        this.score = 0;
        this.cardsRemoved = 0;
        this.cardSelectedCounter = 0;
        this.canvasWidth = width;
        this.canvasHeight = height;
        dbManager = new DBManager(context);
        cardTable = new ArrayList<>();
        cardInformation = dbManager.fetchNCards(9);
        /*Shuffle code is courtesy of StackOverflow user Michael Borgwardt:
        * http://stackoverflow.com/questions/4228975/how-to-randomize-arraylist */
        long seed = System.nanoTime();
        Collections.shuffle(cardInformation, new Random(seed));
        /*Screen size code is courtesy of StackOverflow:
        * http://stackoverflow.com/questions/11252067/how-do-i-get-the-screensize-programmatically-in-android */
        double xScale = 0.0;
        double yScale = 0.0;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            xScale = 0.7;
            yScale = 0.7;
        }
        else if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            xScale = 0.5;
            yScale = 0.5;
        }
        else if (screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            xScale = 0.4;
            yScale = 0.4;
        }
        else if (screenSize == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            xScale = 0.3;
            yScale = 0.3;
        }
        for (int i = 0; i < cardCount; i++) {
            if ((i % cardInformation.size()) == 0) {
                Collections.shuffle(cardInformation, new Random(seed));
            }
            cardTable.add(new Card(
                    context,
                    this.context.getResources(),
                    InternalStorage.readImage(context, dbManager.IMGDIR,
                            cardInformation.get(i % cardInformation.size()).getImgsrc()),
                    R.drawable.card_design_back,
                    (i % 9) * (canvasWidth / 9),
                    (i / 9) * (canvasHeight / 2),
                    xScale,
                    yScale,
                    canvasWidth,
                    canvasHeight,
                    cardInformation.get(i % cardInformation.size()).getName(),
                    cardInformation.get(i % cardInformation.size()).getDescription()));
        }

    }

    public void draw(Canvas canvas) {
        for (int i = 0; i < cardTable.size(); i++) {
            cardTable.get(i).draw(canvas);
        }
    }

    public boolean onTouch(int x, int y) {
        for (int i = 0; i < cardTable.size(); i++) {
            Card temp = cardTable.get(i);
            if (temp.intersects(x, y) && !temp.getFaceDown() && temp.isVisible() && !busy) {
                if (cardSelectedCounter == 0) {
                    firstCard = temp;
                    firstCardBitmap = temp.getFrontImageBitmap();
                    firstCardName = temp.getName();
                    firstCard.flip();
                    cardSelectedCounter++;
                    Log.i("matchcounter", "First card selected.");
                }
                else if (cardSelectedCounter == 1) {
                    Log.i("matchcounter", "Second card selected.");
                    secondCard = temp;
                    secondCardBitmap = temp.getFrontImageBitmap();
                    secondCardName = temp.getName();
                    secondCard.flip();
                    busy = true;
                    new CountDownTimer(1000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            busy = false;
                            if (firstCardName.equals(secondCardName)) {
                                cardTable.remove(firstCard);
                                cardTable.remove(secondCard);
                                score += 100;
                                cardsRemoved += 2;
                                cardSelectedCounter = 0;
                                new AlertDialog.Builder(context).setTitle("You made a match!")
                                        .setMessage(firstCard.getName() + "\n" +
                                                firstCard.getDescription()/* + "\n" +
                                                "Birth: " + firstCard.getBirth() + "\n" +
                                                "Death: " + firstCard.getDeath()*/)
                                        .setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        gameOver = cardsRemoved == cardCount;
                                                        if (gameOver) {
                                                            Intent intent = new Intent(context, GameOverActivity.class);
                                                            intent.putExtra("score", getScore());
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            context.startActivity(intent);
                                                            ((Activity) context).finish();
                                                        }
                                                    }
                                                })
                                        .create()
                                        .show();

                            }
                            else {
                                firstCard.flip();
                                secondCard.flip();
                                cardSelectedCounter = 0;
                            }
                        }
                    }.start();
                }
                return true;
            }
        }
        return false;
    }

    public Context getContext() {
        return context;
    }

    public int getCardSelectedCounter() {
        return cardSelectedCounter;
    }

    public boolean getGameOver() {
        return this.gameOver;
    }

    public int getScore() {
        return score;
    }
}
