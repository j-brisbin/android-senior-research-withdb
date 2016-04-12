package com.example.haungsn.finalprojectapi15;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

/**
 * Created by User on 11/28/2015.
 */
public class Card extends ImageSprite{
    private boolean faceDown;
    private String description;
    private String name;
    private int frontImageID;
    private int backImageID;
    private int viewWidth;
    private int viewHeight;
    private Resources resources;
    private int x;
    private int y;
    public Card(Context context, Resources resources, int frontImageID,
                int backImageID, int x, int y, int viewWidth,
                int viewHeight, String name, String description){
        super(context,resources,backImageID,x,y,0.4,0.4);
        this.frontImageID = frontImageID;
        this.backImageID = backImageID;
        this.x = x;
        this.y = y;
        this.resources = resources;
        this.name = name;
        this.description = description;
        this.faceDown = false;
        this.viewHeight = viewHeight;
        this.viewWidth = viewWidth;
    }

    public Card(Context context, Resources resources, int frontImageID,
                int backImageID, int x, int y,
                double xScale, double yScale, int viewWidth,
                int viewHeight, String name, String description){
        super(context,resources,backImageID,x,y,xScale,yScale);
        this.frontImageID = frontImageID;
        this.backImageID = backImageID;
        this.x = x;
        this.y = y;
        this.resources = resources;
        this.name = name;
        this.description = description;
        this.faceDown = false;
        this.viewHeight = viewHeight;
        this.viewWidth = viewWidth;
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
    }

    public boolean flip(){
        if(faceDown){
            this.setImage(backImageID);
            faceDown = false;
        }
        else{
            this.setImage(frontImageID);
            faceDown = true;
        }
        return faceDown;
    }

    public boolean getFaceDown(){
        return faceDown;
    }

    public void setFaceDown(boolean faceDown) {
        this.faceDown = faceDown;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFrontImageID() {
        return frontImageID;
    }

    public void setFrontImageID(int frontImageID) {
        this.frontImageID = frontImageID;
    }

    public int getBackImageID() {
        return backImageID;
    }

    public void setBackImageID(int backImageID) {
        this.backImageID = backImageID;
    }

    public int getViewWidth() {
        return viewWidth;
    }

    public void setViewWidth(int viewWidth) {
        this.viewWidth = viewWidth;
    }

    public int getViewHeight() {
        return viewHeight;
    }

    public void setViewHeight(int viewHeight) {
        this.viewHeight = viewHeight;
    }
}
