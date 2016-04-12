package com.example.haungsn.finalprojectapi15;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by gibsond on 9/17/2015.
 */
public class ImageSprite extends Sprite implements Target
{
    private Bitmap image;
    private boolean isVisible;
    private Resources resources;
    private double xScale;
    private double yScale;
   // private boolean isScaledRelativeToCanvas;
    private Canvas canvas;
    private double radians;
    private int viewWidth;
    private int viewHeight;



    public ImageSprite(Context context, Resources resources, int resourceId,
                       int x, int y, double scaleX, double scaleY)
    {
        super(x, y, 0, 0);
        this.resources = resources;
        this.canvas = null;

        image = BitmapFactory.decodeResource(resources, resourceId, null);

        this.xScale = scaleX;
        this.yScale = scaleY;
        this.radians = 0;
        this.viewWidth = 0;
        this.viewHeight = 0;
        this.image = image.copy(image.getConfig(), true);
        /*Picasso code is courtesy of square.github.io and StackOverflow
        * http://square.github.io/picasso/
          http://stackoverflow.com/questions/33472916/get-bitmap-image-using-picasso-library
          http://square.github.io/picasso/2.x/picasso/ */
        Picasso.with(context).load(resourceId).into(this);
        this.setWidth(this.image.getWidth());
        this.setHeight(this.image.getHeight());
        this.isVisible = true;

    }

    public ImageSprite(Resources resources, int resourceId, int x, int y)
    {
        super(x, y, 0, 0);
        this.resources = resources;
        this.canvas = null;

        image = BitmapFactory.decodeResource(resources, resourceId, null);
        this.xScale = 1;
        this.yScale = 1;
        this.radians = 0;
        this.viewWidth = 0;
        this.viewHeight = 0;

        //  isScaledRelativeToCanvas = false;

        this.setWidth(this.image.getWidth());
        this.setHeight(this.image.getHeight());
        //  this.scaledImage = Bitmap.createScaledBitmap(this.image, this.getWidth(),this.getHeight(), true);
        this.image = image.copy(image.getConfig(),true);

        this.isVisible = true;

    }

    public ImageSprite(Resources resources, Bitmap bitmap, int x, int y)
    {
        super(x, y, 0, 0);
        this.resources = resources;
        this.canvas = null;

        image = bitmap;
        this.xScale = 1;
        this.yScale = 1;
        this.radians = 0;
        this.viewWidth = 0;
        this.viewHeight = 0;

        //  isScaledRelativeToCanvas = false;

        this.setWidth(this.image.getWidth());
        this.setHeight(this.image.getHeight());
        //  this.scaledImage = Bitmap.createScaledBitmap(this.image, this.getWidth(),this.getHeight(), true);
        this.image = image.copy(image.getConfig(),true);

        this.isVisible = true;

    }

    public void setImage(int id)
    {
        image = BitmapFactory.decodeResource(resources, id, null);
        this.setWidth((int)(this.image.getWidth()*xScale));
        this.setHeight((int) (this.image.getHeight() * yScale));
        this.image = Bitmap.createScaledBitmap(this.image, this.getWidth(),this.getHeight(), true);
    }
    public void setImageScaledRelativeToCanvas(int id, double xScale, double yScale, int viewWidth, int viewHeight)  //can only be called after view has been created
    {
        image = BitmapFactory.decodeResource(resources, id, null);
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.xScale = xScale;
        this.yScale = yScale;
        this.setWidth( (int)(viewWidth * xScale));
        this.setHeight( (int)(viewHeight * yScale));
        this.image = Bitmap.createScaledBitmap(this.image, this.getWidth(), this.getHeight(), true);
    }

    protected Bitmap getImage()
    {
        return  image.copy(this.image.getConfig(), true);
    }

    public double getAngle()
    {
        return Math.toDegrees(this.radians);
    }
    public void setAngle(double degrees)
    {
        this.radians = Math.toRadians(degrees);
    }

    public boolean isVisible()
    {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible)
    {
        this.isVisible = isVisible;
    }

    public void setXScale(double xScale)
    {
        this.xScale = xScale;
        this.setWidth((int) (this.image.getWidth() * this.xScale));
        this.image = Bitmap.createScaledBitmap(this.image, (int)(this.image.getWidth()*xScale),(int)(this.image.getHeight()*yScale), true);
    }

    public void setYScale(double yScale)
    {
        this.yScale = yScale;
        this.setHeight((int) (this.image.getHeight() * this.yScale));
        this.image = Bitmap.createScaledBitmap(this.image, (int)(this.image.getWidth()*xScale),(int)(this.image.getHeight()*yScale), true);
    }

    public void setScale(double xScale, double yScale)
    {
        this.xScale = xScale;
        this.yScale = yScale;
        this.setWidth((int) (this.image.getWidth() * this.xScale));
        this.setHeight((int) (this.image.getHeight() * this.yScale));
        this.image = Bitmap.createScaledBitmap(this.image, (int)(this.image.getWidth()*xScale),(int)(this.image.getHeight()*yScale), true);
    }

    public void setScaleRelativeToCanvas(double xScale, double yScale,int viewWidth, int viewHeight)  //scale of 1 is same size as canvas width and height
    {

        this.xScale = xScale;
        this.yScale = yScale;
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;
        this.setWidth( (int)(viewWidth * xScale));
        this.setHeight( (int)(viewHeight * yScale));
        this.image = Bitmap.createScaledBitmap(this.image, this.getWidth(), this.getHeight(), true);

    }

    public void draw(Canvas canvas)
    {
        super.draw(canvas);

        if (this.isVisible())
        {
            canvas.save();
            canvas.drawBitmap(image, this.getX(), this.getY(), this.getPaint());
            canvas.restore();
        }

    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        this.image = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * xScale),
                (int) (bitmap.getHeight() * yScale), true);
        this.setWidth(this.image.getWidth());
        this.setHeight(this.image.getHeight());
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
