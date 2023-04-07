package com.example.mamooddiary;

import android.content.ContentValues;
import android.content.Context;
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
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2,day);
        contentValues.put(col_3,month);
        contentValues.put(col_4,year);
        contentValues.put(col_5,message);
        contentValues.put(col_6,mood);

        long result = db.insert(table_name,null,contentValues);
        if(result == 1){
            return false;
        }else {
            return true;
        }
    }
}
