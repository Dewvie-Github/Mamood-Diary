package com.example.mamooddiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_name ="Mydatabase.dB";
    public static final String table_name ="Mytable";
    public static final String col_1 ="id";
    public static final String col_2 ="day";
    public static final String col_3 ="month";
    public static final String col_4 ="year";
    public static final String col_5 ="message";
    public static final String col_6 ="mood";
    public SQLiteDatabase db = this.getWritableDatabase();





    public DBHelper(@Nullable Context context) {
        super(context, DB_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Mytable(id INTEGER PRIMARY KEY AUTOINCREMENT,day TEXT,month  TEXT,year TEXT,message TEXT,mood TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(db);
    }

    public boolean AddData(String day,String month,String year,String message,String mood){

        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2,day);
        contentValues.put(col_3,month);
        contentValues.put(col_4,year);
        contentValues.put(col_5,message);
        contentValues.put(col_6,mood);

        long result = db.insert(table_name,null,contentValues);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }

    public String  selectMood(String day,String month,String year){

        String selectmood = "SELECT mood FROM Mytable WHERE day = ? AND month = ? AND year = ?";
        String mood = "";

        String[] args = new String[] { String.valueOf(day), String.valueOf(month), String.valueOf(year) };
        Cursor cursor = db.rawQuery(selectmood, args);

        if (cursor.moveToFirst()) {
            mood = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return mood;
    }
    public String[] selectMessage(String day,String month,String year){

        String selectmood = "SELECT mood FROM Mytable WHERE day = ? AND month = ? AND year = ?";
        String message = "";

        String[] args = new String[] { String.valueOf(day), String.valueOf(month), String.valueOf(year) };
        Cursor cursor = db.rawQuery(selectmood, args);

        if (cursor.moveToFirst()) {
            message = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return new String[] {message} ;
    }

    public boolean UpdateData(String id,String day,String month,String year){
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2,day);
        contentValues.put(col_3,month);
        contentValues.put(col_4,year);
      long result = db.update(table_name,new ContentValues(),"id=?",new String[]{id});
      if (result == 1){
          return false;
      }else {
          return true;
      }
    }
}
