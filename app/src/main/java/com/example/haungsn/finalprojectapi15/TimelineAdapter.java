package com.example.haungsn.finalprojectapi15;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jbrisbin32 on 2/22/16.
 */
/*Typeface code is courtesy of StackOverflow, Android Developers, and tutorialspoint:
* http://developer.android.com/reference/android/graphics/Typeface.html
* http://stackoverflow.com/questions/13539688/how-to-use-roboto-font-in-android-project
* http://www.tutorialspoint.com/android/android_custom_fonts.htm*/

/*Code is courtesy of Android Developers and tutsplus:
* http://developer.android.com/training/material/lists-cards.html
* http://code.tutsplus.com/tutorials/getting-started-with-recyclerview-and-cardview-on-android--cms-23465 */
public class TimelineAdapter  extends RecyclerView.Adapter<TimelineAdapter.TimelineViewHolder>{
    private ArrayList<DBEvent> timelineItems;
    private AssetManager timelineAssetManager;

    public static class TimelineViewHolder extends RecyclerView.ViewHolder{
        CardView mainCardView;
        public TextView timelineItemYear;
        public TextView timelineItemLocation;
        public TextView timelineItemDescription;
        public TextView timelineReferenceLink;
        public Typeface robotoThin;
        public Typeface robotoLight;
        public Typeface robotoRegular;
        public TimelineViewHolder(View v, AssetManager am){
            super(v);
            /*Declares Typeface variables to be used for TextViews.*/
            robotoThin = Typeface.createFromAsset(am,"fonts/Roboto-Thin.ttf");
            robotoLight = Typeface.createFromAsset(am,"fonts/Roboto-Light.ttf");
            robotoRegular = Typeface.createFromAsset(am,"fonts/Roboto-Regular.ttf");

            mainCardView = (CardView)v.findViewById(R.id.main_card_view);
            timelineItemYear = (TextView)v.findViewById(R.id.timeline_item_year);
            timelineItemLocation = (TextView)v.findViewById(R.id.timeline_item_location);
            timelineItemDescription = (TextView)v.findViewById(R.id.timeline_item_description);
            timelineReferenceLink = (TextView)v.findViewById(R.id.timeline_reference_link);




            /*Sets TextViews to the appropriate font.*/
            timelineItemYear.setTypeface(robotoThin);
            timelineItemLocation.setTypeface(robotoLight);
            timelineItemDescription.setTypeface(robotoRegular);
            timelineReferenceLink.setTypeface(robotoRegular);
        }
    }

    public TimelineAdapter(ArrayList<DBEvent> myDataset, AssetManager myAssetManager){
        this.timelineItems = myDataset;
        this.timelineAssetManager = myAssetManager;
    }

    @Override
    public TimelineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_item_layout,
                parent,false);
        TimelineViewHolder tvh = new TimelineViewHolder(v,timelineAssetManager);
        return tvh;
    }

    @Override
    public void onBindViewHolder(TimelineViewHolder timelineViewHolder, int i) {
        timelineViewHolder.timelineItemYear.setText(timelineItems.get(i).getDate());
        timelineViewHolder.timelineItemLocation.setText(timelineItems.get(i).getLocation());
        timelineViewHolder.timelineItemDescription.setText(timelineItems.get(i).getDescription());
        /*Makes reference link clickable, courtesy of StackOverflow:
            http://stackoverflow.com/questions/9290651/make-a-hyperlink-textview-in-android*/
        timelineViewHolder.timelineReferenceLink.setClickable(true);
        timelineViewHolder.timelineReferenceLink.setMovementMethod(LinkMovementMethod.getInstance());
        String hyperlink = "<a href='" + timelineItems.get(i).getLink()
                + "'>" + "Reference Link" + "</a>";
        timelineViewHolder.timelineReferenceLink.setText(Html.fromHtml(hyperlink));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return timelineItems.size();
    }
}
