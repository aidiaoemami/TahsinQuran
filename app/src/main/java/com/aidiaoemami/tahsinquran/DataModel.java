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

    public void insert_hukum(String hukum, String cara_baca) {
        String query = "insert into hukum (hukum, cara_baca) values ('" +hukum + "', '" + cara_baca+ "')";
        db.execSQL(query);
    }

    public void insert_tahsin(String lafadz, String transkripsi) {
        String query = "insert into tahsin (lafadz, transkripsi) values ( '" +lafadz+ "', '"+transkripsi+"')";
        db.execSQL(query);
    }

    public void insert_tajwid(String huruf,  String pola, int hukum) {
        String query = "insert into tajwid (huruf, pola, hukum) values ('" +huruf+ "',  '"+pola+"', "+hukum+")";
        db.execSQL(query);
    }

    public Tahsin selectTahsinByID(int ID){
        Tahsin tahsin = null;
        String query = "select * from "+TABLE_TAHSIN+" where id = "+ID+" ;";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String transkripsi = cursor.getString(2);
            String lafadz = cursor.getString(1);
            tahsin = new Tahsin(id, lafadz, transkripsi);
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
            String cara_baca = cursor.getString(2);
            hukum = new Hukum(id, hukumBacaan, cara_baca);
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
            String pola = cursor.getString(2);
            int hukum = cursor.getInt(3);
            tajwid = new Tajwid(id, huruf, pola, hukum);
        }
        return tajwid;
    }



    public ArrayList<String> selectHukum(){
        ArrayList<String> list = new ArrayList<>();
        String query = "select * from hukum";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            String hukum = cursor.getString(1);
            list.add(hukum);
        }
        cursor.close();
        return list;
    }

    public Cursor getHuruf(int id){
        String query = "SELECT huruf FROM tajwid JOIN hukum ON tajwid.hukum = hukum.id WHERE hukum.id = "+id+" GROUP BY huruf";
        Cursor cursor=db.rawQuery(query, null);
        return cursor;
    }

    public Cursor allDataTahsin(){
        String query = "select * from tahsin";
        Cursor cursor=db.rawQuery(query, null);
        return cursor;
    }

    public ArrayList<String> selectPola(int id){
        ArrayList<String> list = new ArrayList<>();
        String query = "select pola from tajwid where hukum = "+id+";";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            String pola = cursor.getString(0);
            list.add(pola);
        }
        cursor.close();
        return list;
    }

}
