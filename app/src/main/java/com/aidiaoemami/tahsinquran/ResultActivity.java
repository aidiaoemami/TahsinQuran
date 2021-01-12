package com.aidiaoemami.tahsinquran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public class ResultActivity extends AppCompatActivity {

    Button btnSearch, btnReload;
    EditText et_pattern;
    TextView tv_lafadz, tv_tajwid, tv_sebab, tvKey;
    ImageView btnClose;
    String newPattern = null;
    SQLiteOpenHelper helper;
    DataHelper db;
    DataModel dataModel;
    Hukum hukum;
    Tajwid tajwid;
    ArrayList<String> key;
    ArrayList<Integer> valuedistance;

//    TextView textView, lafadz, sebab;
//    TextView tVPattern, tVkey, tvTajwid;
//    Button reloadBtn;
//    ImageView btnClose, playAudio;
//    MediaPlayer mySong;
//    SQLiteOpenHelper helper;
////    SQLiteDatabase db;
//    DataHelper db;
//    ArrayList<String> teks;
//    ArrayList<String> key;
//    ArrayList<Integer> valuedistance;
//    ArrayList<Integer> cost;
//    List<Tahsin> key_lafadz;
//    Tahsin tahsin;
//    Hukum hukum;
//    Tajwid tajwid;
//    DataModel dataModel;
//    Result result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tv_lafadz = findViewById(R.id.lafadz);
        tvKey = findViewById(R.id.tvKey);
        btnReload = findViewById(R.id.reloadBtn);
        btnClose = findViewById(R.id.btnClose);
        tv_tajwid = findViewById(R.id.tvTajwid);
        tv_sebab = findViewById(R.id.tvSebab);


        Intent i = getIntent();
        String lafadz = i.getStringExtra("pattern");
        tv_lafadz.setText(lafadz);

        String pattern = lafadz;
        String sebab=null;



        int found = 0;
        long startTime = System.nanoTime();
        for (int x = 0; x < pattern.length(); x++) {
            if (pattern.charAt(x) == 'ن' && pattern.charAt(x + 1) == 'ْ' && x > 0 && x < pattern.length() - 2) {
                if (pattern.charAt(x + 2) == ' ')
                    newPattern = pattern.substring(x, x + 4);
                else
                    newPattern = pattern.substring(x, x + 3);
                sebab = "Nun mati";
                found = 1;
                Log.d("masuk if", "masuk if");
            } else if (pattern.charAt(x) == 'ٌ' || pattern.charAt(x) == 'ٍ') {
                newPattern = pattern.substring(x, x + 3);
                found = 1;
                sebab = "Tanwin";
                Log.d("masuk else if", "masuk else if");
            } else if (pattern.charAt(x) == 'ً') {
                newPattern = pattern.substring(x, x + 4);
                found = 1;
                sebab = "Tanwin";
                Log.d("masuk if", "masuk else if 2");
            } else
                newPattern = pattern;

            if (found > 0)
                break;
        }



        db = new DataHelper(this);
        helper = new DataHelper(getApplicationContext());
        dataModel = new DataModel(helper.getReadableDatabase());

        ArrayList<String> key = new ArrayList<>();
        ArrayList<Integer> valuedistance = new ArrayList<>();
        ArrayList<Integer> valuedistance2 = new ArrayList<>();
        Cursor cursor = db.allDataTajwid();

        while (cursor.moveToNext()) {
            key.add(cursor.getString(2));
            valuedistance.add(distance(newPattern, key.get(cursor.getPosition())));
        }

        int min = valuedistance.get(0);
        int minIndex = 1;
        for (int x = 1; x < valuedistance.size(); x++) {
            if (valuedistance.get(x) < min) {
                min = valuedistance.get(x);
                minIndex = x + 1;
            }
        }
        if (min>1){
            tv_tajwid.setText("Tidak dapat mengenali hukum bacaan");
            tv_lafadz.setText(pattern);
        }

        else{
                tajwid = dataModel.selectAllTajwidByID(minIndex);
            Log.d("Waktu pencarian tajwid+stemming : ",String.valueOf(System.nanoTime() -
                    startTime)+" ns");

            hukum = dataModel.selectHukumByID(tajwid.getHukum());
            tv_tajwid.setText("Hukum Bacaannya : "+hukum.getHukum());
            tv_sebab.setText(sebab +" bertemu dengan huruf "+tajwid.getHuruf());
            tvKey.setText(hukum.getCara_baca().split("\\.")[0]);
            String text = newPattern;
            String changeColor = pattern.replace(text, "<font color = '#ff0000'>"+text +"</font>");
            tv_lafadz.setText(Html.fromHtml(changeColor));

        }



        btnReload.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                ResultActivity.this.finish();
            }
        });

//        textView = findViewById(R.id.textTv);
//        reloadBtn = findViewById(R.id.reloadBtn);
//        lafadz = findViewById(R.id.lafadz);
//        sebab = findViewById(R.id.tvSebab);
//        tVPattern = findViewById(R.id.pattern);
////        tVkey = findViewById(R.id.key);
//        tvTajwid = findViewById(R.id.tvTajwid);
//        btnClose = findViewById(R.id.btnClose);
//        playAudio = findViewById(R.id.playAudio);
////        mySong = MediaPlayer.create(ResultActivity.this, R.raw.a);
//
//        playAudio.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mySong.start();
//            }
//        });
//
////        Intent i = getIntent();
////        teks = i.getStringArrayListExtra("text");
////        String pattern = teks.get(0).toLowerCase().replaceAll("[^a-z]", "");
//        String pattern = getIntent().getStringExtra("pattern");
//        Log.d("pattern", pattern);
//        db = new DataHelper(this);
//        helper = new DataHelper(getApplicationContext());
//        dataModel = new DataModel(helper.getReadableDatabase());
//
//        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
//        int idtahsin = sharedPreferences.getInt("id_tahsin",0);
//        tahsin = dataModel.selectTahsinByID(idtahsin);
//        String a = tahsin.getTranskripsi();
//        int resId = getResources().getIdentifier(a, "raw", getPackageName());
////        int resId = getResources().getIdentifier(Integer.toString(tahsin.getID()), "raw", getPackageName());
//        mySong = MediaPlayer.create(ResultActivity.this, resId);
//        lafadz.setText(pattern);
////        tVPattern.setText(pattern);
////        tVkey.setText(tahsin.getTranskripsi());
//
//
//        String source = tahsin.getTransliterasi();
//        long startTime = System.nanoTime();
//        String lafadz = tahsin.getLafadz();
//        String newPatern = null;
//
//        for (int x =0; x<lafadz.length();x++){
//            if (lafadz.charAt(x) == 'ن' && lafadz.charAt(x+1) == 'ْ'){
////                lafadz = Normalizer.normalize(pattern, Normalizer.Form.NFKD).replaceAll("\\p{M}", "");
////                newPatern = ResultTajwidActivity.preprocessing()
//            }
//        }
//
//        result = Result.prepocessingArabic(tahsin.getLafadz());
//        String newPattern = result.getPattern();
//
//        tajwid = dataModel.selectAllTajwidByID(searchTajwid(newPattern));
//        Log.d("Waktu pencarian tajwid+stemming : ",String.valueOf(System.nanoTime() -
//                startTime)+" ns");
//
//        hukum = dataModel.selectHukumByID(tajwid.getHukum());
//
//        String arab = tahsin.getLafadz();
////        Spannable WordtoSpan = new SpannableString(arab);
////        WordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), arab.indexOf(result.getStar()), arab.indexOf(result.getEnd())+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////        lafadz.setText(WordtoSpan);
//
////        int distance = distance(pattern, tahsin.getTranskripsi());
////        String conclu;
////        if (distance==0)
////            conclu = String.valueOf(result.getStar());
////        else
////            conclu = String.valueOf(result.getEnd());
//
////        textView.setText(conclu);
//        tvTajwid.setText(hukum.getHukum());
////        String arab = tahsin.getLafadz();
////        Spannable WordtoSpan = new SpannableString(tahsin.getLafadz());
////        if (tahsin.getLafadz().contains("ٌ") || tahsin.getLafadz().contains("ً") || tahsin.getLafadz().contains("ٍ")){
////            sebab.setText("Tanwin bertemu dengan "+tajwid.getHuruf());
////            int position = tahsin.getLafadz().indexOf(tajwid.getHuruf());
////            if(tahsin.getLafadz().charAt(position+1) == ' ')
////                WordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), position-1, position, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////            else
////                WordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), position-3, position+2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////        }
////        else {
////            sebab.setText("Nun mati bertemu dengan "+tajwid.getHuruf());
////            int end = arab.indexOf("َن");
////            if(arab.charAt(end-2) == ' ')
////                WordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), end+1, end+4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////            else
////                WordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), end+1, end+3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
////        }
//
//        sebab.setText(result.getSebab()+" "+tajwid.getHuruf());
//

//
    }

    //
//    @Override
//    protected void onPause() {
//        super.onPause();
//        mySong.release();
//    }
//
//    public static String stemming(String pattern){
//        String patternTajwid;
//        char n = 'n';
//        char m;
//        int indexN=0, noWord=0;
//        int length = pattern.length();
//        for (int x = 0; x<length;x++){
//            m = pattern.charAt(x);
//            if (m == n && x>0 && x!=length-1){
//                if (pattern.charAt(x-1)=='a' || pattern.charAt(x-1)=='i' || pattern.charAt(x-1)=='u'){
//                    indexN = x-1;
//                    noWord = 1;
//                    break;
//                }
//            }
//        }
//        if (noWord==0 && !pattern.contains("rr") && !pattern.contains("ww") && !pattern.contains("ll")
//                && !pattern.contains("mb") && !pattern.contains("yy"))
//            patternTajwid = "x";
//
//        else if (noWord>0 && pattern.charAt(indexN+2)== 'a' || pattern.charAt(indexN+2)== 'i'|| pattern.charAt(indexN+2)== 'u' )
//            patternTajwid = pattern.substring(indexN, indexN+3);
//
//        else if (length-indexN > 3 && indexN>=0 && noWord>0)
//            patternTajwid = pattern.substring(indexN, indexN+4);
//
//        else
//            patternTajwid = pattern.substring(indexN, length);
//
//        return patternTajwid;
//    }
//
    public static int distance(String pattern, String key) {
        long startTime = System.nanoTime();
        int cost;
        int[][] dist = new int[pattern.length()+1][key.length()+1];
        for (int i = 0; i <pattern.length()+1; i++)
            dist[i][0] = i;
        for (int j = 0; j <key.length()+1; j++)
            dist[0][j] = j;
        for (int i = 1; i <pattern.length()+1; i++) {
            for (int j = 1; j <key.length()+1; j++) {
//                if (pattern.charAt(i - 1) == key.charAt(j - 1)) {
//                    //dist[i - 1][j] + 1 = deletion
//                    //dist[i][j - 1] + 1 = insertion
//                    //dist[i - 1][j - 1] + 1 = substitution
//                    //dist[i - 2][j - 2] + 1 = transposition
//                    dist[i][j] = Math.min(Math.min(dist[i - 1][j] + 1, dist[i][j - 1] + 1), dist[i - 1][j - 1]);
//                } else {
//                    dist[i][j] = Math.min(Math.min(dist[i - 1][j] + 1, dist[i][j - 1] + 1), dist[i - 1][j - 1] + 1);
//                }
//                //damerau - transposition
//                if (i > 1 && j > 1 && pattern.charAt(i - 1) == key.charAt(j - 2) && pattern.charAt(i - 2) == key.charAt(j - 1))
//                    dist[i][j] = Math.min(dist[i][j], dist[i - 2][j - 2] + 1);
                if (pattern.charAt(i-1) == key.charAt(j-1)){
                    cost = 0;
                    Log.d("PATTERN", pattern.charAt(i-1)  +" DAN KEY " +key.charAt(j-1));
                }
                else
                    cost = 1;
                dist[i][j] = Math.min(Math.min(dist[i-1][j]+1, dist[i][j-1]+1), dist[i-1][j-1]+cost);
                if (i>1 && j>1 && pattern.charAt(i-1) == key.charAt(j-2) && pattern.charAt(i-2) == key.charAt(j-1)){
                    dist[i][j] = Math.min(dist[i][j], dist[i - 2][j - 2]+cost);
                }
//                dist[pattern.length()][key.length()] = dist[i][j];
            }
        }
//        long endTime = System.nanoTime();
//        Log.d("Waktu", pattern + " dan " + key + String.valueOf(System.nanoTime() -
//                startTime) + " ns, distance :" + dist[pattern.length()-1][key.length()-1] +" ");
        return dist[pattern.length()][key.length()];
    }

    //
    public int searchTajwid(String patternTajwid) {
        long startTime = System.nanoTime();
        ArrayList<String> key = new ArrayList<>();
        ArrayList<String> key2 = new ArrayList<>();
        ArrayList<Integer> valuedistance = new ArrayList<>();
        ArrayList<Integer> valuedistance2 = new ArrayList<>();
        Cursor cursor = db.allDataTajwid();

        while (cursor.moveToNext()) {
            key.add(cursor.getString(2));
//            key2.add(cursor.getString(3));
            valuedistance.add(distance(patternTajwid, key.get(cursor.getPosition())));
//            valuedistance2.add(distance(patternTajwid, key2.get(cursor.getPosition())));
//            valuedistance.add(damerauLevenshteinAlgorithm.execute(patternTajwid,key.get(cursor.getPosition())));
//            valuedistance2.add(damerauLevenshteinAlgorithm.execute(patternTajwid, key2.get(cursor.getPosition())));
//            stringBuilder.append("jarak : "+valuedistance.get(cursor.getPosition())+" Key : "
//                    +key.get(cursor.getPosition())+"\n");
//                stringBuilder.append(cursor.getCount());
        }

        int min = valuedistance.get(0);
        int minIndex = 1;
        for (int x = 1; x < valuedistance.size(); x++) {
            if (valuedistance.get(x) < min) {
                min = valuedistance.get(x);
                minIndex = x + 1;
            }
        }
//        int min2 = valuedistance2.get(0);
//        int minIndex2 = 1;
//        for (int x = 1 ; x<valuedistance2.size();x++){
//            if (valuedistance2.get(x)<min){
//                min2 = valuedistance2.get(x);
//                minIndex2 = x+1;
//            }
//        }

//        if (min2<min){
//            min = min2;
//            minIndex = minIndex2;
//        }
        Log.d("Waktu pencarian tajwid", String.valueOf(System.nanoTime() -
                startTime) + " ns dan distance " + valuedistance.get(minIndex - 1) + " ,target " + key.get(minIndex - 1));
        return minIndex;
    }
}
//}
