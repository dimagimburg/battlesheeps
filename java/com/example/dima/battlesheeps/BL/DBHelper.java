package com.example.dima.battlesheeps.BL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    //final String CREATE_TABLE = "CREATE TABLE myTable(...)";
    private static String DB_PATH = "/data/data/com.example.dima.battlesheeps/databases/";
    private String DBName;
    private Context mContext;
    private int Difficulties=0;
    private ArrayList<String> tables = new ArrayList<>();
    private final String TAG = "DBHelper";


    public DBHelper(Context context){
        super(context, "BattleShip", null, 1);
        mContext = context;
        //this.Difficulties=Difficulties;
       //this.DBName=DBName+".db";
        tables.add("Amateur");
        tables.add("Advanced");
        tables.add("Hard");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlquery;
        for (String t:tables) {
            sqlquery = "CREATE TABLE "+t+" (ID integer primary key autoincrement,Name TEXT,Score INTEGER,Latitude NUMERIC,Longitude NUMERIC)";
            db.execSQL(sqlquery);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //проверяете какая версия сейчас и делаете апдейт
        //db.execSQL("DROP TABLE IF EXISTS tableName");
       // onCreate(db);

    }
    public void addPlayer(int diff,Player player) {
        SQLiteDatabase dbw = getWritableDatabase();
        SQLiteDatabase dbr = getReadableDatabase();
        Cursor cur=dbr.query(tables.get(diff),new String[]{"ID"},null,null,null,null,null,null);

        if (cur.getCount()>9) {
            Cursor c = dbr.query(tables.get(diff), new String[] { "min(Score)" }, null, null,
                    null, null, null);
            c.moveToFirst();
            int min=c.getInt(0);
            //c = dbr.query(tables.get(diff), new String[] { "ID" }, null, null,
                   // null, null, null);
            c.close();
            c=dbr.query(tables.get(diff), new String[]{"ID"}, "Score=?", new String[] {Integer.toString(min)}, null, null, null);
            c.moveToFirst();
            //Log.e(TAG,Integer.toString(c.getInt(0)));
            dbw.delete(tables.get(diff), "ID=" + c.getInt(0), null);
        }
        ContentValues values = new ContentValues();
        values.put("Name", player.getName());
        values.put("Score", player.getScore());
        values.put("Latitude", player.getLatitude());
        values.put("Longitude", player.getLongitude());
        dbw.insert(tables.get(diff), null, values);
        dbr.close();
        dbw.close();
        cur.close();
    }

    public void deletelastPlayer(int diff,Player player){
        SQLiteDatabase dbw = getWritableDatabase();
        SQLiteDatabase dbr = getReadableDatabase();
        Cursor cur=dbr.query(tables.get(diff),new String[]{"ID"},null,null,null,null,null,null);
        if (cur.getColumnCount()>10) {
            Cursor c = dbr.rawQuery("SELECT MIN(Score) FROM " + tables.get(diff), null);
            dbw.delete(tables.get(diff), "Score" + c.getInt(0), null);
        }
        dbr.close();
        dbw.close();
       // db.execSQL("INSERT INTO "+table+" VALUES ("+player.getName()+","+player.getTries()+","+player.getLatitude()+","+player.getLongitude()+")");

    }

    public boolean isHighScore(int diff, Player player) {
        SQLiteDatabase dbr = getReadableDatabase();
        Cursor cur=dbr.query(tables.get(diff),new String[]{"ID"},null,null,null,null,null,null);
        int numOfRows=cur.getCount();
        Log.e(TAG,Integer.toString(numOfRows));
        cur.close();
        cur = dbr.query(tables.get(diff), new String[] { "min(Score)" }, null, null,
                null, null, null);
        cur.moveToFirst();
        dbr.close();
        if(numOfRows<10 || cur.getInt(0)<player.getScore()) {
            Log.e(TAG,"True");
            return true;
        }
        else {
            Log.e(TAG,"False");
            return false;
        }
    }
    public ArrayList<String> getScoresTable(int diff) {
        SQLiteDatabase dbr = getReadableDatabase();
        Cursor cur=dbr.query(tables.get(diff),new String[]{"Name","Score","Latitude","Longitude"},null,null,null,null,null,null);
        int rows=cur.getCount();
        int columns=cur.getColumnCount();
        ArrayList<String> result = new ArrayList<>();

        while(cur.moveToNext()){
            result.add(cur.getString(0));
            result.add(cur.getString(1));
            result.add(cur.getString(2));
            result.add(cur.getString(3));

        }
        dbr.close();
                Log.e(TAG,result.toString());

        return result;
    }


    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DBName;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        }catch(SQLiteException e){
            //database does't exist yet.
        }
        if(checkDB != null){
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

}