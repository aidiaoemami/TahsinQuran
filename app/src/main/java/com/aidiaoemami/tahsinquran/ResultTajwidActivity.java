package com.aidiaoemami.tahsinquran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.text.Normalizer;
import java.util.ArrayList;


import static com.aidiaoemami.tahsinquran.ResultActivity.distance;
public class ResultTajwidActivity extends AppCompatActivity {

    Button btnReload;
    TextView tvTajwid, tvPattern, tvKey, tvHuruf;
    ImageView btnClose;
    ArrayList<String> teks, key, key2;
    ArrayList<Integer> valuedistance = null, valuedistance2;
    SQLiteOpenHelper helper;
    DataHelper db;
    DataModel dataModel;
    Tajwid tajwid;
    Hukum hukum;
    Result result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_tajwid);
        db = new DataHelper(this);
        helper = new DataHelper(getApplicationContext());
        dataModel = new DataModel(helper.getReadableDatabase());

        btnReload = findViewById(R.id.reloadBtnTajwid);
        tvTajwid = findViewById(R.id.tvTajwid);
        tvPattern = findViewById(R.id.tvPattern);
        tvKey = findViewById(R.id.tvKey);
        tvHuruf = findViewById(R.id.tvSebab);
        btnClose = findViewById(R.id.btnClose);

        Intent i = getIntent();
        teks = i.getStringArrayListExtra("text");

        String pattern = teks.get(0);

        long startTime = System.nanoTime();
        String newPattern;
        newPattern = preprocessing(pattern);



        valuedistance = new ArrayList<>();
        key = new ArrayList<>();
        Cursor cursor = db.allDataTajwid();

        while (cursor.moveToNext()){
            key.add(cursor.getString(2));
            valuedistance.add(distance(newPattern,key.get(cursor.getPosition())));
        }
        int min = valuedistance.get(0);
        int minIndex = 1;
        for (int x = 1 ; x<valuedistance.size();x++){
            if (valuedistance.get(x)<min){
                min = valuedistance.get(x);
                minIndex = x+1;
            }
        }


        if (min>1){
            tvTajwid.setText("Tidak dapat mengenali hukum bacaan");
            tvPattern.setText(pattern);
        }

        else{
            tajwid = dataModel.selectAllTajwidByID(minIndex);
            hukum = dataModel.selectHukumByID(tajwid.getHukum());
            tvKey.setText(hukum.getCara_baca().split("\\.")[0]);
            tvHuruf.setText("Nun mati bertemu dengan huruf "+tajwid.getHuruf());
            String text = newPattern;
            String changeColor = pattern.replace(text, "<font color = '#ff0000'>"+text +"</font>");
            tvPattern.setText(Html.fromHtml(changeColor));
            tvTajwid.setText("Hukum Bacaannya: "+hukum.getHukum());
            Log.d("Waktu pencarian tajwid",String.valueOf(System.nanoTime() -
                    startTime)+" ns dan distance " +valuedistance.get(minIndex-1) +" ,target "+key.get(minIndex-1));

        }



//        for (int x=0;x<pattern.length();x++){
//            if (pattern.charAt(x)=='ن' && pattern.charAt(x+1)=='ا' || pattern.charAt(x) == 'ا' && pattern.charAt(x+1) == 'ل' ||
//                    pattern.charAt(x)=='ن' && pattern.charAt(x+1)==' ' && pattern.charAt(x) == 'ا' && pattern.charAt(x+1) == 'ل')
//                found = 1;
//
//            if (pattern.charAt(x)=='ن' && pattern.charAt(x+1)!='ا' && x>0 && x<pattern.length()-1 ){
//                if(pattern.charAt(x+1)==' ')
//                    newPattern = pattern.substring(x, x+3);
//                else
//                    newPattern = pattern.substring(x, x+2);
//                found = 2;
//            }
//
//
//            if (found>0)
//                break;
//
//        }
//        result = Result.prepocessingArabic(pattern);
//        String newPattern = result.getPattern();
//        if(found>0){
//
//        }
//
//        else {
//            StringBuilder stringBuilder = new StringBuilder();
//            key = new ArrayList<>();
//            key2 = new ArrayList<>();
//            valuedistance = new ArrayList<>();
//            valuedistance2 = new ArrayList<>();


//            if (newPattern.length()<=3 && min > 0)
//                minIndex = 1;

//            int min2 = valuedistance2.get(0);
//            int minIndex2 = 1;
//            for (int x = 1 ; x<valuedistance2.size();x++){
//                if (valuedistance2.get(x)<min){
//                    min2 = valuedistance2.get(x);
//                    minIndex2 = x+1;
//                }
//            }
//
//            if (min2<min){
//                min = min2;
//                minIndex = minIndex2;
//            }





//            String arab = pattern;
//            String huruf = tajwid.getHuruf();

//            Spannable WordtoSpan = new SpannableString(arab);
//            WordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), arab.indexOf(result.getStar()), arab.indexOf(result.getStar())+3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);






//            if (pattern.contains("ن")){
//                int start = arab.indexOf('ن');
//                int end = arab.indexOf(huruf);
//                Spannable WordtoSpan = new SpannableString(arab);
//                WordtoSpan.setSpan(new ForegroundColorSpan(Color.RED), arab.indexOf(result.getStar()), end+1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                tvHuruf.setText("Nun mati bertemu dengan huruf "+tajwid.getHuruf());
//                tvPattern.setText(WordtoSpan);
//            }
//
//            else{
//                int start = arab.indexOf(' ');
//                int end = arab.indexOf(huruf);
//                Spannable WordtoSpan = new SpannableString(arab);
//                WordtoSpan.setSpan(new ForegroundColorSpan(Color.RED),  start, end+1,   Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                tvHuruf.setText("Tanwin bertemu dengan huruf "+tajwid.getHuruf());
//                tvPattern.setText(WordtoSpan);
//            }

//            tvKey.setText(tajwid.getTranskripsi());
//
//        }


        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultTajwidActivity.this, TajwidActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                ResultTajwidActivity.this.finish();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public static String preprocessing(String pattern){
        String newPattern=pattern;
//        ArrayList<String> newpattern = new ArrayList();
        int found = 0;
        for(int x=0;x<pattern.length();x++){
            if (pattern.charAt(x)=='ن' && x!=0 && x!=pattern.length()-1){
                if (pattern.charAt(x)=='ن' && pattern.charAt(x+1) == ' ' ){
                    if (pattern.charAt(x+3) == 'ل' && pattern.charAt(x+2) == 'ا')
                        newPattern = pattern;
                    else{
                        newPattern = pattern.substring(x, x+3);
                        Log.d("masuk if", "masuk if");
                        found =1;
                    }
                }

                else if (pattern.charAt(x)=='ن' && pattern.charAt(x+1) != ' '){
                    if (x>=2 && pattern.charAt(x-1) == 'ل' && pattern.charAt(x-2) == 'ا') {
                        newPattern = pattern;
                    }

                    else{
                        newPattern = pattern.substring(x, x+2);
                        found=1;
                    }

                    Log.d("masuk else if", "masuk else if");
                }

                else
                    newPattern = pattern;
            }
            if (found>0)
                break;
//            newpattern.add(newPattern);
        }

        return newPattern;

    }


}
