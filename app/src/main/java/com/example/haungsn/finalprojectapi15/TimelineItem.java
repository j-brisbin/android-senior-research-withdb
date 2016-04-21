package com.example.haungsn.finalprojectapi15;

/**
 * Created by User on 2/28/2016.
 */
public class TimelineItem {
    private int id;
    private String year;
    private String name;
    private String description;
    private String referenceLink;

    public TimelineItem(int id, String year, String name, String description, String referenceLink){
        this.id = id;
        this.year = year;
        this.name = name;
        this.description = description;
        this.referenceLink = referenceLink;
    }
}
