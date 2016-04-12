package com.example.haungsn.finalprojectapi15;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

/*RecyclerView code is courtesy of Android Developers and tutsplus.com
* http://code.tutsplus.com/tutorials/getting-started-with-recyclerview-and-cardview-on-android--cms-23465
* http://developer.android.com/training/material/lists-cards.html*/
public class TimelineActivity extends AppCompatActivity {

    private RecyclerView timelineRecyclerView;
    private ArrayList<DBEvent> timelineItems;
    private LinearLayoutManager timelineLayoutManager;
    private TimelineAdapter timelineAdapter;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbManager = new DBManager(this.getApplicationContext());

        /*Gets DBEvent objects from the database.*/
        timelineItems = dbManager.fetchNEvents(20);

        /*Define RecyclerView with accompanying LayoutManager and custom adapter.*/
        timelineLayoutManager = new LinearLayoutManager(this.getApplicationContext());
        timelineAdapter = new TimelineAdapter(timelineItems,getAssets());
        timelineRecyclerView = (RecyclerView)findViewById(R.id.timeline_recycler_view);
        timelineRecyclerView.setLayoutManager(timelineLayoutManager);
        timelineRecyclerView.setAdapter(timelineAdapter);
    }

}
