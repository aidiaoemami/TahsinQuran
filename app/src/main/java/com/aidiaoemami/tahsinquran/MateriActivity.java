package com.aidiaoemami.tahsinquran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Array;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import static com.aidiaoemami.tahsinquran.ResultActivity.distance;

public class MateriActivity extends AppCompatActivity {

    ImageView btnBack;
    ListView listView;
    SQLiteOpenHelper helper;
    DataHelper db;
    DataModel dataModel;
    Cursor cursor;
    ArrayList<String> listHukum;
    ArrayList<Integer> listId;
    List <Hukum> dataHukum;
    ArrayAdapter adapter;
    Hukum hukum;
    Result result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materi);

        btnBack = findViewById(R.id.btnBack);
        listView = findViewById(R.id.listView);

        db = new DataHelper(this);
        helper = new DataHelper(getApplicationContext());
        dataModel = new DataModel(helper.getReadableDatabase());
        listHukum = dataModel.selectHukum();
        adapter = new ArrayAdapter(this, R.layout.custom_textview, listHukum);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dataHukum = listHukum.get(position);
                ArrayList<String> huruf = new ArrayList<>();
                ArrayList<String> newLafadz = new ArrayList<>();
                ArrayList<String> polaTajwid = new ArrayList<>();
                ArrayList<String> contoh = new ArrayList<>();
                ArrayList<String> lafadz = new ArrayList<>();
                ArrayList<String> arab = new ArrayList<>();
                ArrayList<String> transkip = new ArrayList<>();
                ArrayList<String> audio = new ArrayList<>();
                hukum = dataModel.selectHukumByID(position+1);
                Cursor cursor = dataModel.getHuruf(hukum.getId());
                while (cursor.moveToNext()){
                    if (!cursor.getString(0).contains("Idgham")){
                        huruf.add(cursor.getString(0));
                    }
                }

                Cursor trans = db.allDataTahsin();
                while (trans.moveToNext()){
                    lafadz.add(trans.getString(1));
                    transkip.add(trans.getString(2));
                }

                for(int y =0 ; y<lafadz.size();y++){
                    String pattern = lafadz.get(y);
                    String newPattern = null;
                    int found = 0;
                    long startTime = System.nanoTime();
                    for (int x = 0; x < pattern.length(); x++) {
                        if (pattern.charAt(x) == 'ن' && pattern.charAt(x + 1) == 'ْ' && x > 0 && x < pattern.length() - 2) {
                            if (pattern.charAt(x + 2) == ' ')
                                newPattern = pattern.substring(x, x + 4);
                            else
                                newPattern = pattern.substring(x, x + 3);
                            found = 1;
                            Log.d("masuk if", "masuk if");
                        } else if (pattern.charAt(x) == 'ٌ' || pattern.charAt(x) == 'ٍ') {
                            newPattern = pattern.substring(x, x + 3);
                            found = 1;

                            Log.d("masuk else if", "masuk else if");
                        } else if (pattern.charAt(x) == 'ً') {
                            newPattern = pattern.substring(x, x + 4);
                            found = 1;
                            Log.d("masuk if", "masuk else if 2");
                        } else
                            newPattern = pattern;

                        if (found > 0)
                            break;
                    }
                    newLafadz.add(newPattern);
                }

                polaTajwid = dataModel.selectPola(hukum.getId());

                for (int z =0; z<newLafadz.size();z++){
                    for (int y=0; y<polaTajwid.size();y++){
                        int distance = distance(newLafadz.get(z), polaTajwid.get(y));
                        if (distance == 0){
                            Log.d("--Masuk---", lafadz.get(z)+"\n");
                            contoh.add(lafadz.get(z));
                            audio.add(transkip.get(z));
                            break;
                        }
                    }
                }


                Intent i = new Intent(MateriActivity.this, DetailMateriActivity.class);
                i.putExtra("id", position+1);
                i.putExtra("hukum", dataHukum);
                i.putExtra("huruf", huruf);
                i.putExtra("cara_baca", hukum.getCara_baca());
                i.putExtra("transli", contoh);
                i.putExtra("audio", audio);
                startActivity(i);


            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }



}
