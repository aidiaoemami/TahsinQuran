package com.aidiaoemami.tahsinquran;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Random;

public class ModelQuran {
    SQLiteDatabase db;
    String TABLE_NAME = "quran";

    public ModelQuran(SQLiteDatabase db) {
        this.db = db;
    }

//    public void insert(String surat, Integer ayat, String lafadz, String transkripsi) {
//        String query = "insert into " + TABLE_NAME + " (surat, ayat, lafadz, transkripsi) values ('" + surat + "', " + ayat + ", '" + lafadz + "', '" + transkripsi + "')";
//        db.execSQL(query);
//    }

    public void update(int id, String surat, int ayat, String lafadz, String transkripsi) {
        String query = "update " + TABLE_NAME + " SET surat = '" + surat + "', ayat = " + ayat + ", lafadz = '" + lafadz + "', transkripsi = '"+transkripsi+"' where id = " + id + ";";
        db.execSQL(query);
    }

    public void delete(int id){
        String query = "delete from "+TABLE_NAME+" where id = "+id+" ";
        db.execSQL(query);
    }

    public ArrayList<String> select(){
        ArrayList<String> surat = new ArrayList<>();
        String query = "select * from " +TABLE_NAME+";";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
//            String nama_surat = cursor.getString(1);
            String lafadzh = cursor.getString(3);
//            String transkripsi = cursor.getString(4);
//            surat.add(nama_surat);
            surat.add(lafadzh);
//            surat.add(transkripsi);
        }
        cursor.close();
        return surat;
    }

    public Quran selectTahsinByID(int ID){
        Quran quran = null;
        String query = "select * from tahsin where id = "+ID+" ;";
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String hukum = cursor.getString(1);
            String lafadz = cursor.getString(2);
            String transkripsi = cursor.getString(3);
//            quran = new Quran(id, surat, ayat, lafadz, transkripsi);
//            quran = new Quran(id, );
        }
        return quran;
    }
}

