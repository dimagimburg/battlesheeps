package com.example.dima.battlesheeps.BL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {
    //final String CREATE_TABLE = "CREATE TABLE myTable(...)";
    private static String DB_PATH = "/data/data/com.example.dima.battlesheeps.BL/databases/";
    private String DBName;
    private Context mContext;
    private int Difficulties=0;


    public DBHelper(Context context, String DBName, int Difficulties){
        super(context, DBName, null, 1);
        mContext = context;
        this.Difficulties=Difficulties;
        this.DBName=DBName+".db";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        for(int i=0;i<Difficulties;i++) {
            String sqlquery = "CREATE TABLE " + i + " (Name TEXT PRIMARY KEY,Tries INTEGER,Latitude NUM, Longitude NUM)";
            db.execSQL(sqlquery);
            db.close();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //проверяете какая версия сейчас и делаете апдейт
        //db.execSQL("DROP TABLE IF EXISTS tableName");
       // onCreate(db);

    }
    public void addPlayer(int diff,Player player) {
        //db.execSQL("INSERT INTO "+table+" VALUES ("+player.getName()+","+player.getTries()+","+player.getLatitude()+","+player.getLongitude()+")");
        String table=Integer.toString(diff);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", player.getName());
        values.put("Tries", player.getTries());
        values.put("Latitude", player.getLatitude());
        values.put("Longitude", player.getLongitude());
        db.insert(table, null, values);
        db.close();
    }

    public void deletelastPlayer(int diff,Player player) {
        String table=Integer.toString(diff);
        SQLiteDatabase dbw = getWritableDatabase();
        SQLiteDatabase dbr = getReadableDatabase();
        Cursor c=dbr.rawQuery("SELECT MAX(Tries) FROM "+table,null);
        dbw.delete(table,"Tries"+c.getInt(0),null);
        dbr.close();
        dbw.close();
       // db.execSQL("INSERT INTO "+table+" VALUES ("+player.getName()+","+player.getTries()+","+player.getLatitude()+","+player.getLongitude()+")");

    }

    public boolean isHighScore(int diff, Player player) {
        String table=Integer.toString(diff);
        SQLiteDatabase dbr = getReadableDatabase();
        Cursor c=dbr.rawQuery("SELECT MAX(Tries) FROM "+table,null);
        long rowcount=DatabaseUtils.queryNumEntries(dbr,table);
        dbr.close();
        if(rowcount<10 || c.getInt(0)<player.getTries()) {
            return true;
        }
        else return false;
    }
    public String[][] getScoresTable(int diff) {
        String table=Integer.toString(diff);
        SQLiteDatabase dbr = getReadableDatabase();
        Cursor cur=dbr.query(table,null,null,null,null,null,null,null);
        String[][] result=new String[4][];
        int j=0;
        do{
            for(int i=0;i<4;i++)
            result[j][i]=cur.getString(i);
            j++;
        }
        while (!cur.isLast());
        dbr.close();
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