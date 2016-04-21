package com.example.haungsn.finalprojectapi15;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;


/**
 * Created by Nathan on 3/22/2016.
 */
public class DBManager extends SQLiteOpenHelper{

    public static final String DOMAIN = "http://easeton.dennisiscool.tech";
    public static final String IMGDIR = "cards";

    public static final String DB_NAME = "seton_application";

    public static final String CARD_TABLE_NAME = "cards";
    public static final String CARD_ID = "id";
    public static final String CARD_NAME = "name";
    public static final String CARD_IMGSRC = "imgsrc";
    public static final String CARD_DESCRIPTION = "description";
    public static final String CARD_BIRTH = "birth";
    public static final String CARD_DEATH = "death";
    public static final String CARD_CREATED = "created_at";

    public static final String EVENT_TABLE_NAME = "events";
    public static final String EVENT_ID = "id";
    public static final String EVENT_DATE = "date";
    public static final String EVENT_LOCATION = "location";
    public static final String EVENT_DESCRIPTION = "description";
    public static final String EVENT_LINK = "link";

    public static final String MC_TABLE_NAME = "mc_questions";
    public static final String MC_ID = "id";
    public static final String MC_QUESTION = "question";
    public static final String MC_ANSWER = "correct_answer";
    public static final String MC_WRONG1 = "wrong_answer1";
    public static final String MC_WRONG2 = "wrong_answer2";
    public static final String MC_WRONG3 = "wrong_answer3";

    public static final String TF_TABLE_NAME = "tf_questions";
    public static final String TF_ID = "id";
    public static final String TF_QUESTION = "question";
    public static final String TF_IS_TRUE = "is_true";

    public static final String UPDATE_TABLE_NAME = "updates";
    public static final String UPDATE_ID = "id";
    public static final String UPDATE_DESC = "description";
    public static final String UPDATE_DATE = "last_updated";

    private Context context;
    private int requestPending = 0;
    private SQLiteDatabase db;


    public DBManager(Context context){
        super(context,
                /*db name=*/ DB_NAME,
                /*cursorFactory=*/ null,
                /*db version=*/23);
        this.context = context;
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("db", "onCreate");

        //create cards table
        String sql = "CREATE TABLE " + CARD_TABLE_NAME
                + " (" + CARD_ID + " INTEGER, "
                + CARD_NAME + " TEXT,"
                + CARD_IMGSRC + " TEXT,"
                + CARD_DESCRIPTION + " TEXT,"
                + CARD_BIRTH + " DATE,"
                + CARD_DEATH + " DATE,"
                + CARD_CREATED + " DATE DEFAULT CURRENT_DATE,"
                + " PRIMARY KEY (" + CARD_ID + "));";
        db.execSQL(sql);

        //create events table
        sql = "CREATE TABLE " + EVENT_TABLE_NAME
                + " (" + EVENT_ID + " INTEGER, "
                + EVENT_DESCRIPTION + " TEXT,"
                + EVENT_DATE + " DATE,"
                + EVENT_LOCATION + " TEXT,"
                + EVENT_LINK + " TEXT,"
                + " PRIMARY KEY (" + EVENT_ID + "));";
        db.execSQL(sql);

        //create mc_questions table
        sql = "CREATE TABLE " + MC_TABLE_NAME
                + " (" + MC_ID + " INTEGER, "
                + MC_QUESTION + " TEXT,"
                + MC_ANSWER + " TEXT,"
                + MC_WRONG1 + " TEXT,"
                + MC_WRONG2 + " TEXT,"
                + MC_WRONG3 + " TEXT,"
                + " PRIMARY KEY (" + MC_ID + "));";
        db.execSQL(sql);

        //create tf_qeustions table
        sql = "CREATE TABLE " + TF_TABLE_NAME
                + " (" + TF_ID + " INTEGER, "
                + TF_QUESTION + " TEXT,"
                + TF_IS_TRUE + " TEXT,"
                + " PRIMARY KEY (" + TF_ID + "));";
        db.execSQL(sql);

        //create updates table
        sql = "CREATE TABLE " + UPDATE_TABLE_NAME
                + " (" + UPDATE_ID + " INTEGER, "
                + UPDATE_DESC + " TEXT,"
                + UPDATE_DATE + "  DATE DEFAULT CURRENT_DATE,"
                + " PRIMARY KEY (" + UPDATE_ID + "));";
        db.execSQL(sql);
        //insert old initial date to get all updates on first check
        sql="INSERT INTO " + UPDATE_TABLE_NAME + " ("+ UPDATE_DESC +"," + UPDATE_DATE + ") values ('used to get all updates on frist check','2015-01-01 19:44:34');";
        db.execSQL(sql);
    }

    private void insertCards(ArrayList<DBCard> cards){
        for(int i=0;i< cards.size();i++){
            String sql = "INSERT INTO " + CARD_TABLE_NAME + " VALUES " + cards.get(i).toSQL();
            db.execSQL(sql);
        }
    }

    private void insertEvents(ArrayList<DBEvent> events){
        for(int i=0;i<events.size();i++){
            String sql = "INSERT INTO " + EVENT_TABLE_NAME + " VALUES " + events.get(i).toSQL();
            db.execSQL(sql);
        }
        SQLiteDatabase db = this.getWritableDatabase();

    }

    private void insertTF(ArrayList<DBTFQuestion> questions){
        for(int i =0; i<questions.size();i++){
            String sql = "INSERT INTO " + TF_TABLE_NAME + " VALUES " + questions.get(i).toSQL();
            db.execSQL(sql);
        }
    }


    private void insertMC(ArrayList<DBMCQuestion> questions){
        for(int i =0; i<questions.size();i++){
            String sql = "INSERT INTO " + MC_TABLE_NAME + " VALUES " + questions.get(i).toSQL();
            db.execSQL(sql);
        }
    }

    public ArrayList<DBCard> fetchNCards(int n){
        String sql = "SELECT * FROM " + CARD_TABLE_NAME + " ORDER BY RANDOM() LIMIT " + n;
        Cursor c = db.rawQuery(sql, null);
        ArrayList<DBCard> cards = new ArrayList<>();
        Log.d("fetchNCards: ", "" + c.getCount());
        while(c.moveToNext()){
            cards.add(new DBCard(
                    c.getInt(0),//id
                    c.getString(1),//name
                    c.getString(2),//imgsrc
                    c.getString(3),//desc
                    c.getString(4),//birth
                    c.getString(5),//death
                    c.getString(6)//created_at
            ));
        }
        return cards;
    }

    public ArrayList<DBEvent> fetchNEvents(int n){
        String sql = "SELECT * FROM " + EVENT_TABLE_NAME + " ORDER BY RANDOM() LIMIT " + n;
        Cursor c = db.rawQuery(sql, null);
        ArrayList<DBEvent> events = new ArrayList<>();
        while(c.moveToNext()){
            events.add(new DBEvent(
                    c.getInt(0),//id
                    c.getString(1),//location
                    c.getString(2),//desc
                    c.getString(3),//link
                    c.getString(4)//date
            ));
        }
        
        return events;
    }

    public ArrayList<DBEvent> fetchAllEvents(){
        String sql = "SELECT * FROM " + EVENT_TABLE_NAME + " ORDER BY DATE(" + EVENT_DATE + ") ASC";
        Cursor c = db.rawQuery(sql,null);
        ArrayList<DBEvent> events = new ArrayList<>();
        while(c.moveToNext()){
            events.add(new DBEvent(
                    c.getInt(0),//id
                    c.getString(1),//location
                    c.getString(2),//desc
                    c.getString(3),//link
                    c.getString(4)//date
            ));
        }

        return events;
    }

    public ArrayList<DBMCQuestion> fetchNMCs(int n){
        String sql = "SELECT * FROM " + MC_TABLE_NAME + " ORDER BY RANDOM() LIMIT " + n;
        Cursor c = db.rawQuery(sql, null);
        ArrayList<DBMCQuestion> questions = new ArrayList<>();
        while(c.moveToNext()){
            questions.add(new DBMCQuestion(
                    c.getInt(0),//id
                    c.getString(1),//question
                    c.getString(2),//correct_answer
                    c.getString(3),//wrong_answer
                    c.getString(4),//wrong_answer2
                    c.getString(5)//wrong_answer3
            ));
        }
        return questions;
    }

    public ArrayList<DBTFQuestion> fetchNTFs(int n){
        String sql = "SELECT * FROM " + TF_TABLE_NAME + " ORDER BY RANDOM() LIMIT " + n;
        Cursor c = db.rawQuery(sql, null);
        ArrayList<DBTFQuestion> questions = new ArrayList<>();
        while(c.moveToNext()){
            boolean is_true = c.getString(1).equals("1");
            questions.add(new DBTFQuestion(
                    c.getInt(0),//id
                    is_true,//is_true
                    c.getString(2)//question
            ));
        }
        return questions;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CARD_TABLE_NAME +";");
        db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE_NAME +";");
        db.execSQL("DROP TABLE IF EXISTS " + MC_TABLE_NAME +";");
        db.execSQL("DROP TABLE IF EXISTS " + TF_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + UPDATE_TABLE_NAME + ";");
    }

    public boolean fetchUpdates(){
        //if(isOnline()) {
            //remove current table data
            db.execSQL("DELETE FROM " + CARD_TABLE_NAME + " WHERE " + CARD_ID + " >= 0;");
            db.execSQL("DELETE FROM " + EVENT_TABLE_NAME + " WHERE " + EVENT_ID + " >= 0;");
            db.execSQL("DELETE FROM " + MC_TABLE_NAME + " WHERE " + MC_ID + " >= 0;");
            db.execSQL("DELETE FROM " + TF_TABLE_NAME + " WHERE " + TF_ID + " >= 0;");
            //get card JSON
            String url = DOMAIN + "/json/cards";
            JsonArrayRequest cjsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            //Log.d("Response", response.toString());
                            Log.d("fetchUpdates:","Card JSON retrieved");
                            try {
                                ArrayList<DBCard> cards = DBCard.deserialize(response);
                                insertCards(cards);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            requestPending--;
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Response", error.toString());
                            requestPending = -1;
                        }
                    });
            requestPending++;
            Requests.getInstance(context).addToRequestQueue(cjsonArrayRequest);
            //get event JSON

            url = DOMAIN + "/json/events";
            JsonArrayRequest ejsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            //Log.d("Response", response.toString());
                            Log.d("fetchUpdates:","Event JSON retrieved");
                            try {
                                ArrayList<DBEvent> events = DBEvent.deserialize(response);
                                insertEvents(events);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            requestPending--;
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Response", error.toString());
                            requestPending = -1;
                        }
                    });
            requestPending++;
            Requests.getInstance(context).addToRequestQueue(ejsonArrayRequest);
            //get tfquestion JSON
            url = DOMAIN + "/json/tfquestions";
            JsonArrayRequest tjsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            //Log.d("Response", response.toString());
                            Log.d("fetchUpdates:","TF JSON retrieved");
                            try {
                                ArrayList<DBTFQuestion> questions = DBTFQuestion.deserialize(response);
                                insertTF(questions);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            requestPending--;
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Response", error.toString());
                            requestPending = -1;
                        }
                    });
            requestPending++;
            Requests.getInstance(context).addToRequestQueue(tjsonArrayRequest);
            //get mcquestions JSON
            url = DOMAIN + "/json/mcquestions";
            JsonArrayRequest mjsonArrayRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            //Log.d("Response", response.toString());
                            Log.d("fetchUpdates:","MC JSON retrieved");
                            try {
                                ArrayList<DBMCQuestion> questions = DBMCQuestion.deserialize(response);
                                insertMC(questions);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            requestPending--;
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Response", error.toString());
                            requestPending = -1;
                        }
                    });
            requestPending++;
            Requests.getInstance(context).addToRequestQueue(mjsonArrayRequest);
            return true;

        //}
        /*else{
            return false;
        }*/
    }

    public int getRequestPending(){
        return requestPending;
    }

    private void addUpdate(){
        //can only have 1 record in the update table
        db.execSQL("DELETE FROM " + UPDATE_TABLE_NAME + " WHERE " + UPDATE_ID + " >= 0;");
        db.execSQL("INSERT INTO " + UPDATE_TABLE_NAME + " (" + UPDATE_DESC + ")" + " VALUES ('Updated woo!');");
        Log.i("addUpdate", "Updated!");
        
    }

    public void fetchCardImages(){
        //todo fix this to get images from the same day or based on time
        String q = "SELECT " + CARD_IMGSRC + " FROM " + CARD_TABLE_NAME + " INNER JOIN " + UPDATE_TABLE_NAME + " WHERE julianday(" + UPDATE_DATE + ") <= julianday(" + CARD_CREATED + ");";
        Cursor c = db.rawQuery(q,null);
        while(c.moveToNext()){
            final String imgsrc = c.getString(0);
            Log.i("Fetch image: ", imgsrc);
            String url = DOMAIN + "/images/cards/" + imgsrc;
            ImageRequest request = new ImageRequest(url,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            Log.d("Image retrieved:", imgsrc);
                            try{
                                InternalStorage.writeIMG(context,bitmap,IMGDIR,imgsrc);
                            }
                            catch(IOException e){
                                Log.e("fetchCardImages: ",e.getMessage());
                            }
                            requestPending--;
                        }
                    }, 0, 0, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Image error:", imgsrc + ": " + error.toString());
                            requestPending = -1;
                        }
                    });
            requestPending++;
            Requests.getInstance(context).addToRequestQueue(request);
            addUpdate();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
