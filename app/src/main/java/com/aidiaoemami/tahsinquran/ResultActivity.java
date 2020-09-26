package com.aidiaoemami.tahsinquran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public class ResultActivity extends AppCompatActivity {

    TextView textView, lafadz;
    TextView tVPattern, tVkey, tvTajwid;
    Button reloadBtn;
    ImageView btnClose, playAudio;
    MediaPlayer mySong;
    SQLiteOpenHelper helper;
//    SQLiteDatabase db;
    DataHelper db;
    private ArrayList<String> teks;
    ArrayList<String> key;
    private ArrayList<Integer> valuedistance;
    private ArrayList<Integer> cost;
    List<Tahsin> key_lafadz;
    Tahsin tahsin;
    Hukum hukum;
    Tajwid tajwid;
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
        btnClose = findViewById(R.id.btnClose);
        playAudio = findViewById(R.id.playAudio);
//        mySong = MediaPlayer.create(ResultActivity.this, R.raw.a);

        playAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySong.start();
            }
        });

        Intent i = getIntent();
        teks = i.getStringArrayListExtra("text");
        String pattern = teks.get(0).toLowerCase().replaceAll("[^a-z]", "");
        db = new DataHelper(this);
        helper = new DataHelper(getApplicationContext());
        dataModel = new DataModel(helper.getReadableDatabase());

        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        int idtahsin = sharedPreferences.getInt("id_tahsin",0);
        String a = "a";
        tahsin = dataModel.selectTahsinByID(idtahsin);
        int resId = getResources().getIdentifier(a, "raw", getPackageName());
//        int resId = getResources().getIdentifier(Integer.toString(tahsin.getID()), "raw", getPackageName());
        mySong = MediaPlayer.create(ResultActivity.this, resId);
        lafadz.setText(tahsin.getLafadz());
        tVPattern.setText(pattern);
        tVkey.setText(tahsin.getTranskripsi());


        String source = tahsin.getTransliterasi();
        String newPattern = stemming(source);



        tajwid = dataModel.selectAllTajwidByID(searchTajwid(newPattern));
        hukum = dataModel.selectHukumByID(tajwid.getHukum());

        int distance = distance(pattern, tahsin.getTranskripsi());
        String result;
        if (distance==0)
            result= "Bacaan Sudah Benar";
        else
            result = "Terdapat Kesalahan pada Bacaan";

        textView.setText(result);
        tvTajwid.setText(hukum.getHukum());


        reloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, TahsinActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                ResultActivity.this.finish();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


//        helper = new DataHelper(getApplicationContext());
//        db = helper.getReadableDatabase();



//        Cursor cursor = db.allDataTahsin();
//        StringBuilder stringBuilder = new StringBuilder();



//        if (cursor.getCount()==0){
//            textView.setText("null");
//        }
//        else{
//            while (cursor.moveToNext()){
//                key.add(cursor.getString(3));
//                valuedistance.add(distance(pattern,key.get(cursor.getPosition())));
//                stringBuilder.append("jarak : "+valuedistance.get(cursor.getPosition())+" Key : "
//                +key.get(cursor.getPosition())+"\n");
//                stringBuilder.append(cursor.getCount());
//            }
//        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mySong.release();
    }

    public static String stemming(String pattern){
        String patternTajwid;
        char n = 'n';
        char m;
        int indexN=0, noWord=0;
        int length = pattern.length();
        for (int x = 0; x<length;x++){
            m = pattern.charAt(x);
            if (m == n && x>0 && x!=length-1){
                indexN = x-1;
                noWord = 1;
                break;
            }
        }
        if (noWord==0 && !pattern.contains("irro") && !pattern.contains("iwwa") && !pattern.contains("ll") )
            patternTajwid = "x";

        else if (noWord>0 && pattern.charAt(indexN+2)== 'a' || pattern.charAt(indexN+2)== 'i'|| pattern.charAt(indexN+2)== 'u' )
            patternTajwid = pattern.substring(indexN, indexN+3);

        else if (length-indexN > 5 && indexN>=0 && noWord>0)
            patternTajwid = pattern.substring(indexN, indexN+5);

        else
            patternTajwid = pattern.substring(indexN, length);

        return patternTajwid;
    }

    public static int distance(String pattern, String key){
        int [][] dist = new int[pattern.length()+1][key.length()+1];
        for (int i=0;i<=pattern.length();i++)
            dist[i][0]=i;
        for(int j=0;j<=key.length();j++)
            dist[0][j]=j;
        for (int j=1;j<=key.length();j++){
            for (int i=1;i<=pattern.length();i++){
                if (pattern.charAt(i-1)== key.charAt(j-1)){
                    dist[i][j] = Math.min(Math.min(dist[i-1][j]+1,dist[i][j-1]+1), dist[i-1][j-1]);
                }
                else{
                    dist[i][j] = Math.min(Math.min(dist[i-1][j]+1, dist[i][j-1]+1), dist[i-1][j-1]+1);
                }
                //damerau - transposition
                if(i>1 && j>1 && pattern.charAt(i-1)== key.charAt(j-2) && pattern.charAt(i-2)== key.charAt(j-1))
                    dist[i][j] = Math.min(dist[i][j], dist[i-2][j-2]+1);

//                //minus same words for avoid value distance are same
//                if(i>1 && j>1 && pattern.charAt(i-1) == key.charAt(j-1) && pattern.charAt(i-2)==key.charAt(j-2)  )
//                    match = match+1;
            }
        }
        return dist[pattern.length()][key.length()];
    }

    public int searchTajwid(String patternTajwid){
        ArrayList<String> key = new ArrayList<>();
        ArrayList<Integer> valuedistance = new ArrayList<>();
        Cursor cursor = db.allDataTajwid();

        while (cursor.moveToNext()){
            key.add(cursor.getString(2));
            valuedistance.add(distance(patternTajwid,key.get(cursor.getPosition())));
//            stringBuilder.append("jarak : "+valuedistance.get(cursor.getPosition())+" Key : "
//                    +key.get(cursor.getPosition())+"\n");
//                stringBuilder.append(cursor.getCount());
        }

        int min = valuedistance.get(0);
        int minIndex = 1;
        for (int x = 1 ; x<valuedistance.size();x++){
            if (valuedistance.get(x)<min){
                min = valuedistance.get(x);
                minIndex = x+1;
            }
        }
        return minIndex;
    }
}
