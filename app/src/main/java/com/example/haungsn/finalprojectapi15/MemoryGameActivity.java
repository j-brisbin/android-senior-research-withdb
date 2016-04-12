package com.example.haungsn.finalprojectapi15;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MemoryGameActivity extends AppCompatActivity {

    //todo padding around canvas edge
    //todo randomly place images

    private MemoryView memoryView;
    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView)findViewById(R.id.textView);
        memoryView = new MemoryView(this,textView,getAssets());
        relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
        relativeLayout.addView(memoryView);
        memoryView.post(new Runnable() {
            @Override
            public void run() {
                memoryView.initialize();
            }
        });

    }


}
