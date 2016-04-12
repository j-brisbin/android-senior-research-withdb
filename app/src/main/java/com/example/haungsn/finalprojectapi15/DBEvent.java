package com.example.haungsn.finalprojectapi15;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Nathan on 3/19/2016.
 */
public class DBEvent {

    private int id;
    private String location, description, link, date;

    public DBEvent(int id, String location, String description, String link, String date) {
        this.id = id;
        this.location = location;
        this.description = description;
        this.link = link;
        this.date = date;
    }

    public static ArrayList<DBEvent> deserialize(JSONArray eventJSON) throws JSONException {
        ArrayList<DBEvent> events = new ArrayList<>();
        for (int i = 0; i < eventJSON.length(); i++) {
            JSONObject temp = eventJSON.getJSONObject(i);
            events.add(new DBEvent(
                    temp.getInt("id"),
                    temp.getString("location"),
                    temp.getString("description"),
                    temp.getString("link"),
                    temp.getString("date")

            ));
        }
        return events;
    }

    public String toSQL() {
        String s = "(" +
                this.id + ",'" +
                this.location.replace("'","''") + "','" +
                this.description.replace("'","''") + "','" +
                this.link.replace("'","''") + "','" +
                this.date + "'" + ")";
        return s;
    }

    public static String toSQL(ArrayList<DBEvent> events) {
        String s = "";
        for (int i = 0; i < events.size(); i++) {
            s += events.get(i).toSQL();
            if (i != events.size() - 1) {
                s += ",";
            }
        }
        return s;
    }

    public String toString(){
        return "Location: " + this.location +
                ",Description: "+ this.description+
                ",Birth: "+this.link+
                ",Death: "+this.date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
