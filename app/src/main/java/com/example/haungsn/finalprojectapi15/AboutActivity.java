package com.example.haungsn.finalprojectapi15;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView logoImageView = (ImageView)findViewById(R.id.logo_image_view);
        /*Screen size code is courtesy of StackOverflow:
        * http://stackoverflow.com/questions/11252067/how-do-i-get-the-screensize-programmatically-in-android */
        /*Screen size code is courtesy of StackOverflow:
        * http://stackoverflow.com/questions/11252067/how-do-i-get-the-screensize-programmatically-in-android */
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        Log.i("screensize","Screen Size: " + screenSize);
        if(screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE){
            Picasso.with(this.getApplicationContext()).
                    load(R.drawable.soc_logo_normal).into(logoImageView);
        }
        else if(screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE){
            Picasso.with(this.getApplicationContext()).
                    load(R.drawable.soc_logo_normal).into(logoImageView);
        }
        else if(screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL){
            Picasso.with(this.getApplicationContext()).
                    load(R.drawable.soc_logo_small).into(logoImageView);
        }
        else if(screenSize == Configuration.SCREENLAYOUT_SIZE_SMALL){
            Picasso.with(this.getApplicationContext()).
                    load(R.drawable.soc_logo_small).into(logoImageView);
        }

    }

}
