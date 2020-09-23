package com.aidiaoemami.tahsinquran;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataModel {

    SQLiteDatabase db;
    SQLiteOpenHelper helper;
    String TABLE_TAHSIN = "tahsin";
    String TABLE_TAJWID = "tajwid";
    String TABLE_HUKUM = "hukum";

    public DataModel(SQLiteDatabase db) {
        this.db = db;
    }

    public Tahsin selectTahsinByID(int ID){
        Tahsin tahsin = null;
        String query = "select * from "+TABLE_TAHSIN+" where id = "+ID+" ;";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String transliterasi = cursor.getString(1);
            String lafadz = cursor.getString(2);
            String transkripsi = cursor.getString(3);
            tahsin = new Tahsin(id, transliterasi, lafadz, transkripsi);
        }
        return tahsin;
    }

    public Hukum selectHukumByID(int ID){
        Hukum hukum = null;
        String query = "select * from " +TABLE_HUKUM+" where id = "+ID+";";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String hukumBacaan = cursor.getString(1);
            hukum = new Hukum(id, hukumBacaan);
        }
        return hukum;
    }

    public Tajwid selectAllTajwidByID(int ID){
        Tajwid tajwid = null;
        String query = "select * from " +TABLE_TAJWID+" where id = "+ID+";";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String huruf = cursor.getString(1);
            String transliterasi = cursor.getString(2);
            String transkripsi = cursor.getString(3);
            int hukum = cursor.getInt(4);
            tajwid = new Tajwid(id, huruf, transliterasi, transkripsi, hukum);
        }
        return tajwid;
    }

}
