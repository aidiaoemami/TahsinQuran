package com.aidiaoemami.tahsinquran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static com.aidiaoemami.tahsinquran.ResultActivity.BoyerMoore;
import static com.aidiaoemami.tahsinquran.ResultActivity.distance;

public class ResultTajwidActivity extends AppCompatActivity {

    Button btnReload;
    TextView tvTajwid, tvPattern, tvKey, tvHuruf;
    private ArrayList<String> teks, key;
    ArrayList<Integer> valuedistance = null;
    SQLiteOpenHelper helper;
    DataHelper db;
    DataModel dataModel;
    Tajwid tajwid;
    Hukum hukum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_tajwid);

        btnReload = findViewById(R.id.reloadBtnTajwid);
        tvTajwid = findViewById(R.id.tvTajwid);
        tvPattern = findViewById(R.id.tvPattern);
        tvKey = findViewById(R.id.tvKey);
        tvHuruf = findViewById(R.id.tvSebab);

        Intent i = getIntent();
        teks = i.getStringArrayListExtra("text");
        String pattern = teks.get(0).toLowerCase().replaceAll("[^a-z]", "");
        String pattern2 = teks.get(0).toLowerCase().replaceAll("[^a-z]", "");

        //stemming
        String patternTajwid;
        char n = 'n';
        char m;
        int indexN=0, noWord=0;
        int length = pattern.length();
        for (int x = 0; x<length;x++){
            m = pattern.charAt(x);
            if (m == n && x>0){
                indexN = x-1;
                break;
            }
            noWord=0;
        }
        if (length-indexN > 4 && indexN>0)
            patternTajwid = pattern.substring(indexN, indexN+4);
        else if (noWord==0)
            patternTajwid = pattern;
        else
            patternTajwid = pattern.substring(indexN, length);

        db = new DataHelper(this);
        helper = new DataHelper(getApplicationContext());
        dataModel = new DataModel(helper.getReadableDatabase());

        StringBuilder stringBuilder = new StringBuilder();
        key = new ArrayList<>();
        valuedistance = new ArrayList<>();
        Cursor cursor = db.allDataTajwid();

        while (cursor.moveToNext()){
            key.add(cursor.getString(2));
            valuedistance.add(distance(patternTajwid,key.get(cursor.getPosition())));
                stringBuilder.append("jarak : "+valuedistance.get(cursor.getPosition())+" Key : "
                +key.get(cursor.getPosition())+"\n");
//                stringBuilder.append(cursor.getCount());
        }
        int min = valuedistance.get(0);
        int max = valuedistance.get(0);
        int minIndex = 1, maxIndex =0;
        for (int x = 1 ; x<valuedistance.size();x++){
            if (valuedistance.get(x)<min){
                min = valuedistance.get(x);
                minIndex = x+1;
            }

        }

        tajwid = dataModel.selectAllTajwidByID(minIndex);
        hukum = dataModel.selectHukumByID(tajwid.getHukum());

        tvTajwid.setText(hukum.getHukum());
//        tvKey.setText(stringBuilder);
//        tvPattern.setText(Integer.toString(minIndex));
        tvPattern.setText(pattern2);
        tvKey.setText(tajwid.getTranskripsi());
        tvHuruf.setText("Nun mati/Tanwin bertemu dengan huruf "+tajwid.getHuruf());
        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultTajwidActivity.this, TajwidActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                ResultTajwidActivity.this.finish();
            }
        });
    }
}
