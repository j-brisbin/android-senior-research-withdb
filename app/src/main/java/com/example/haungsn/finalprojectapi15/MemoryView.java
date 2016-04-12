package com.example.haungsn.finalprojectapi15;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by User on 11/28/2015.
 */
public class MemoryView extends View {

    public static final String SCORE = "score";
    private MemoryLogic memoryLogic;
    private TextView scoreTextView;
    private Context context;
    private AssetManager assetManager;
    public MemoryView(Context context, TextView scoreTextView,
                      AssetManager assetManager){
        super(context);
        this.context = context;
        this.scoreTextView = scoreTextView;
        this.assetManager = assetManager;

    }

    public void initialize(){
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        Log.i("screensize", "Screen Size: " + screenSize);
        memoryLogic = new MemoryLogic(context,getWidth(),getHeight(),screenSize);
        super.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                invalidate();
                return memoryLogic.onTouch((int)event.getX(),(int)event.getY());
            }
        });
        new CountDownTimer(2000,2000){
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                invalidate();
            }
        }.start();

    }

    @Override
    public void onDraw(Canvas canvas){
        scoreTextView.setText("Score: " + memoryLogic.getScore());
        memoryLogic.draw(canvas);
        invalidate();
    }
}
