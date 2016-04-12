package com.example.haungsn.finalprojectapi15;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created by User on 11/28/2015.
 */

public class MemoryLogic {
    public final Bitmap backBitmap;
    private int cardSelectedCounter;
    private int score;
    private int firstCardImageID; /*Resource ID of the selected card*/
    private int secondCardImageID;
    private Card firstCard;
    private boolean busy;
    private Card secondCard;
    private int canvasWidth, canvasHeight;
    private Context context;
    private ArrayList<Card> cardTable;
    private ArrayList<Integer> cardImageIDs;
    private int cardCount = 18;
    private int cardsRemoved;
    private boolean gameOver = false;
    private Random randomCard;
    private int randomCardSelection;
    private ArrayList<MemoryGameItem> memoryGameItems;
    public MemoryLogic(Context context,int width,int height,int screenSize){
        this.context = context;
        this.busy = false;
        this.score = 0;
        this.cardsRemoved = 0;
        this.cardSelectedCounter = 0;
        this.canvasWidth = width;
        this.canvasHeight = height;
        this.backBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.card_design_back, null);
        cardTable = new ArrayList<>();
        cardImageIDs = new ArrayList<>();
        cardImageIDs.add(R.drawable.card_design_front_one);
        cardImageIDs.add(R.drawable.card_design_front_two);
        cardImageIDs.add(R.drawable.card_design_front_three);
        cardImageIDs.add(R.drawable.card_design_front_four);
        cardImageIDs.add(R.drawable.card_design_front_five);
        cardImageIDs.add(R.drawable.card_design_front_six);
        cardImageIDs.add(R.drawable.card_design_front_seven);
        cardImageIDs.add(R.drawable.card_design_front_eight);
        cardImageIDs.add(R.drawable.card_design_front_nine);
        /*Shuffle code is courtesy of StackOverflow user Michael Borgwardt:
        * http://stackoverflow.com/questions/4228975/how-to-randomize-arraylist */
        long seed = System.nanoTime();
        Collections.shuffle(cardImageIDs, new Random(seed));
        /*Screen size code is courtesy of StackOverflow:
        * http://stackoverflow.com/questions/11252067/how-do-i-get-the-screensize-programmatically-in-android */
        /*Screen size code is courtesy of StackOverflow:
        * http://stackoverflow.com/questions/11252067/how-do-i-get-the-screensize-programmatically-in-android */
        double xScale = 0.0;
        double yScale = 0.0;
        if(screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE){
            xScale = 0.7;
            yScale = 0.7;
        }
        else if(screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE){
            xScale = 0.5;
            yScale = 0.5;
        }
        else if(screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL){
            xScale = 0.4;
            yScale = 0.4;
        }
        else if(screenSize == Configuration.SCREENLAYOUT_SIZE_SMALL){
            xScale = 0.3;
            yScale = 0.3;
        }
        for(int i = 0;i<cardCount;i++){
            if((i % cardImageIDs.size()) == 0){
                Collections.shuffle(cardImageIDs,new Random(seed));
            }
            cardTable.add(new Card(
                    context,
                    this.context.getResources(),
                    cardImageIDs.get(i % cardImageIDs.size()),
                    R.drawable.card_design_back,
                    (i % 9) * (canvasWidth / 9),
                    (i / 9) * (canvasHeight / 2),
                    xScale,
                    yScale,
                    canvasWidth,
                    canvasHeight,
                    "A card",
                    "This is a card."));
        }

    }

    public void draw(Canvas canvas){
        for(int i =0;i<cardTable.size();i++){
            cardTable.get(i).draw(canvas);
        }
    }

    public boolean onTouch(int x, int y){
        for(int i = 0;i<cardTable.size();i++){
            Card temp = cardTable.get(i);
            if(temp.intersects(x,y)&&!temp.getFaceDown() && temp.isVisible()&& !busy){
                if(cardSelectedCounter == 0){
                    firstCard = temp;
                    firstCardImageID = temp.getFrontImageID();
                    firstCard.flip();
                    cardSelectedCounter++;
                    Log.i("matchcounter","First card selected.");
                }
                else if(cardSelectedCounter == 1){
                    Log.i("matchcounter","Second card selected.");
                    secondCard = temp;
                    secondCardImageID = temp.getFrontImageID();
                    secondCard.flip();
                    busy = true;
                    new CountDownTimer(1000,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            busy = false;
                            if(firstCardImageID == secondCardImageID){
                                cardTable.remove(firstCard);
                                cardTable.remove(secondCard);
                                /*firstCard.setIsVisible(false);
                                secondCard.setIsVisible(false);*/
                                score += 100;
                                cardsRemoved+=2;
                                cardSelectedCounter = 0;
                                gameOver = cardsRemoved == cardCount;
                                /*new AlertDialog.Builder(context).setTitle("You made a match!")
                                        .setMessage(firstCard.getName() + "\n" +
                                                firstCard.getDescription())
                                        .setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                    }
                                                })
                                        .create()
                                        .show();*/

                            }
                            else{
                                firstCard.flip();
                                secondCard.flip();
                                cardSelectedCounter = 0;
                            }

                            if(gameOver){
                                Intent intent = new Intent(context,GameOverActivity.class);
                                intent.putExtra("score", getScore());
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                                ((Activity)context).finish();
                            }
                        }
                    }.start();
                }
                return true;
            }
        }
        return false;
    }

    public int getCardSelectedCounter() {
        return cardSelectedCounter;
    }

    public boolean getGameOver(){
        return this.gameOver;
    }

    public int getScore() {
        return score;
    }
}
