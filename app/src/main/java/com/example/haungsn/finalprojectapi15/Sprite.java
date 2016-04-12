package com.example.haungsn.finalprojectapi15;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;


public class Sprite
{
    public enum Direction{
        LEFT, UP, RIGHT, DOWN
    }

    private int x,y;
    private int lastX,lastY;
    private int width, height;
    private Paint paint;
    private boolean isSpriteVisible;
    private Rect rect;

    public Sprite(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.lastX = x;
        this.lastY = y;
        this.paint = new Paint();
        this.paint.setColor(Color.RED);
        this.paint.setStrokeWidth(2f);
        this.paint.setStyle(Paint.Style.STROKE);
        this.isSpriteVisible = false;
        this.rect = new Rect(x,y,x+width,y+height);

    }
    public Sprite()
    {
        this(50,50,50,50);

    }

    public Rect getBounds()
    {
        return this.rect;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.lastX = this.x;
        this.x = x;
        this.rect = new Rect(x,y,x+width,y+height);
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.lastY = this.y;
        this.y = y;
        this.rect = new Rect(x,y,x+width,y+height);
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
        this.rect = new Rect(x,y,x+width,y+height);
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
        this.rect = new Rect(x,y,x+width,y+height);
    }

    public int getLastX()
    {
        return lastX;
    }

    public int getLastY()
    {
        return lastY;
    }

    public boolean isSpriteVisible()
    {
        return isSpriteVisible;
    }

    public void setIsSpriteVisible(boolean isSpriteVisible)
    {
        this.isSpriteVisible = isSpriteVisible;
    }

    public Paint getPaint()
    {
        return paint;
    }

    public void setPaint(Paint paint)
    {
        this.paint = paint;
    }

    public void moveTo(int x,int y)
    {
        this.lastX = this.x;
        this.x = x;
        this.lastY = this.y;
        this.y = y;
        this.rect = new Rect(x,y,x+width,y+height);
    }
    public void move(int xDistance, int yDistance)
    {
        int x = this.x + xDistance;
        int y = this.y + yDistance;
        this.moveTo(x, y);
    }
    public void moveLeft(int distance)
    {
        int x = this.x - distance;
        this.setX(x);
    }
    public void moveRight(int distance)
    {
        int x = this.x  + distance;
        this.setX(x);
    }
    public void moveUp(int distance)
    {
        int y = this.y - distance;
        this.setY(y);
    }
    public void moveDown(int distance)
    {
        int y = this.y + distance;
        this.setY(y);
    }
    public void move(Direction direction, int distance)
    {
        if( direction == Direction.LEFT)
        {
            moveLeft(distance);
        }
        else if(direction == Direction.UP)
        {
            moveUp(distance);
        }
        else if(direction == Direction.RIGHT)
        {
            moveRight(distance);
        }
        else if(direction == Direction.DOWN)
        {
            moveDown(distance);
        }
    }

    public boolean intersects(Sprite sprite)
    {
        Rect r = sprite.getBounds();
        return this.rect.intersect(r);
    }
    public boolean intersects(int x,int y)
    {
        return this.rect.contains(x,y);
    }
    public boolean intersects(Rect rect)
    {
        return this.rect.intersect(rect);
    }

    public void draw(Canvas canvas)
    {
        if(isSpriteVisible)
        {
            canvas.drawRect(this.rect, this.paint);
        }


    }


}
