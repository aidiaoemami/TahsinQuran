package com.aidiaoemami.tahsinquran;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DataModel {

    SQLiteDatabase db;
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
            int hukum = cursor.getInt(1);
            String lafadz = cursor.getString(2);
            String transkripsi = cursor.getString(3);
            tahsin = new Tahsin(id, hukum, lafadz, transkripsi);
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

    public List<Tahsin> getAllTahsin(){
        List <Tahsin> data = new ArrayList<Tahsin>();
        String query = "select * from tahsin";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){
            do {
                Tahsin data_tahsin = new Tahsin(cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3));
                data.add(data_tahsin);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return data;
    }
}
