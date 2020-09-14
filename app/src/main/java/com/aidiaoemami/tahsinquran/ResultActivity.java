package com.aidiaoemami.tahsinquran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public class ResultActivity extends AppCompatActivity {

    TextView textView, lafadz;
    TextView tVPattern, tVkey, tvTajwid;
    ImageButton reloadBtn;
    SQLiteOpenHelper helper;
//    SQLiteDatabase db;
    DataHelper db;
    private ArrayList<String> teks, key;
    private ArrayList<Integer> valuedistance;
    private ArrayList<Integer> cost;
    List<Tahsin> key_lafadz;
    Tahsin tahsin;
    Hukum hukum;
    DataModel dataModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textView = findViewById(R.id.textTv);
        reloadBtn = findViewById(R.id.reloadBtn);
        lafadz = findViewById(R.id.lafadz);
        tVPattern = findViewById(R.id.pattern);
        tVkey = findViewById(R.id.key);
        tvTajwid = findViewById(R.id.tvTajwid);


        Intent i = getIntent();
        teks = i.getStringArrayListExtra("text");
        String id_tahsin = i.getStringExtra("idtahsin");
        String pattern = teks.get(0).toLowerCase().replaceAll("\\s+", "");

//        helper = new DataHelper(getApplicationContext());
//        db = helper.getReadableDatabase();

        key = new ArrayList<>();
        valuedistance = new ArrayList<>();
        db = new DataHelper(this);
        helper = new DataHelper(getApplicationContext());
        dataModel = new DataModel(helper.getReadableDatabase());
        Cursor cursor = db.allDataTahsin();
        StringBuilder stringBuilder = new StringBuilder();
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        int idtahsin = sharedPreferences.getInt("id_tahsin",0);
        tahsin = dataModel.selectTahsinByID(idtahsin);
        hukum = dataModel.selectHukumByID(tahsin.getHukum());
        lafadz.setText(tahsin.getLafadz());
        tVPattern.setText(pattern);
        tVkey.setText(tahsin.getTranskripsi());
        tvTajwid.setText(hukum.getHukum());

        if (cursor.getCount()==0){
            textView.setText("null");
        }
        else{
            while (cursor.moveToNext()){
                key.add(cursor.getString(3));
//                valuedistance.add(distance(pattern,key.get(cursor.getPosition())));
//                stringBuilder.append("jarak : "+valuedistance.get(cursor.getPosition())+" Key : "
//                +key.get(cursor.getPosition())+"\n");
//                stringBuilder.append(cursor.getCount());
            }
        }

//        int min = valuedistance.get(0);
//        int max = valuedistance.get(0);
//        int minIndex = 0, maxIndex =0;
//        for (int x = 1 ; x<valuedistance.size();x++){
//            if (valuedistance.get(x)<min){
//                min = valuedistance.get(x);
//                minIndex = x;
//            }
//        }
        int min = distance(pattern, tahsin.getTranskripsi());
        String result;
        if (min==0)
            result= "Bacaan Sudah Benar";
        else
            result = "Terdapat Kesalahan pada Bacaan";



        textView.setText(result);


        String jumlah = Integer.toString(cursor.getCount());

        String coloumindex = Integer.toString(cursor.getColumnIndex("hukum"));



        reloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ResultActivity.this,TahsinActivity.class);
                startActivity(i);
            }
        });
    }

    public static int distance(String pattern, String key){
        int [][] dist = new int[pattern.length()+1][key.length()+1];
        for (int i=0;i<=pattern.length();i++)
            dist[i][0]=i;
        for(int j=0;j<=key.length();j++)
            dist[0][j]=j;
        for (int j=1;j<=key.length();j++){
            for (int i=1;i<=pattern.length();i++){
                if (pattern.charAt(i-1)== key.charAt(j-1))
                    dist[i][j] = Math.min(Math.min(dist[i-1][j]+1,dist[i][j-1]+1), dist[i-1][j-1]);
                else
                    dist[i][j] = Math.min(Math.min(dist[i-1][j]+1, dist[i][j-1]+1), dist[i-1][j-1]+1);

            }
        }
        return dist[pattern.length()][key.length()];
    }

}
