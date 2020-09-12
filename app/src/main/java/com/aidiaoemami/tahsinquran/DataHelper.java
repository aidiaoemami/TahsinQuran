package com.aidiaoemami.tahsinquran;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tahsinquran.db";
//    private static final String DATABASE_TABLE = "quran";
    private static final int DATABASE_VERSION = 1;
    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table quran(id integer primary key, surat string, ayat integer, lafadz text, transskripsi text);";
        //Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);

        ModelQuran modelQuran = new ModelQuran(db);
//        modelQuran.insert("alfatihah", 1, "bismillahirrahmanirrahim", "bismillahirrahmanirrahim");

        //sql = "INSERT INTO biodata (no, nama, tgl, jk, alamat) VALUES ('1', 'Darsiwan', '1996-07-12', 'Laki-laki','Indramayu');";
        //db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
//        if (newVersion > oldVersion) { // Upgrade
//            switch (oldVersion) {
//                case 1: // Upgrade from version 1 to 2.
//                    try {
//                        Log.i(TAG, "upgrade 1->2: adding new column");
//                        db.execSQL("ALTER TABLE " + DATABASE_TABLE + " ADD COLUMN transkripsi text;");
//                        onCreate(db);
//                    } catch (SQLException e) {
//                        Log.e(TAG, "Error executing SQL: ", e); // "duplicate column name"
//                        // is ok
//                    } // fall through for further upgrades
//                    break;
//                default:
//                    Log.w(TAG, "Unknown version " + oldVersion + ". Creating new database.");
//                    db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
//                    onCreate(db);
//            }
//        } else { // newVersion <= oldVersion: assume backwards compatibility
//            Log.w(TAG, "Don't know how to downgrade. Will not touch database and hope they are compatible.");
//        }
    }

    public Cursor allDataTahsin(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from tahsin", null);
        return cursor;
    }
}