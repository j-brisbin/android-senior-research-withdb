package com.example.haungsn.finalprojectapi15;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Nathan on 3/19/2016.
 */
public class DBCard {

    private int id;
    private String name, imgsrc,description, birth, death, created_at;

    public DBCard(int id, String name, String imgsrc, String description, String birth, String death, String created_at) {
        this.id = id;
        this.name = name;
        this.imgsrc = imgsrc;
        this.description = description;
        this.birth = birth;
        this.death = death;
        this.created_at = created_at;
    }

    public static ArrayList<DBCard> deserialize(JSONArray cardJSON) throws JSONException {
        ArrayList<DBCard> cards = new ArrayList<>();
        for(int i=0;i<cardJSON.length();i++){
            JSONObject temp = cardJSON.getJSONObject(i);
            cards.add(new DBCard(
                    temp.getInt("id"),
                    temp.getString("name"),
                    temp.getString("imgsrc"),
                    temp.getString("description"),
                    temp.getString("birth"),
                    temp.getString("death"),
                    temp.getString("created_at")

            ));
        }
        return cards;
    }

    public String toSQL(){
        String s = "("+
                this.id + ",'" +
                this.name.replace("'","''") + "','" +
                this.imgsrc.replace("'","''") + "','" +
                this.description.replace("'","''") + "','" +
                this.birth + "','" +
                this.death + "','" +
                this.created_at + "'" + ")";
        return s;
    }

    public static String toSQL(ArrayList<DBCard> cards){
        String s = "";
        for(int i=0;i< cards.size();i++){
            s += cards.get(i).toSQL();
            if(i != cards.size()-1){
                s+=",";
            }
        }
        return s;
    }

    public String toString(){
        return "Name: " + this.name +
                ",Imgsrc: " +this.imgsrc+
                ",Description: "+ this.description+
                ",Birth: "+this.birth+
                ",Death: "+this.death+
                ",Created_at"+this.created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getDeath() {
        return death;
    }

    public void setDeath(String death) {
        this.death = death;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

}
