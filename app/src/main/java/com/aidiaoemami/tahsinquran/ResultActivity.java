package com.aidiaoemami.tahsinquran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.min;

public class ResultActivity extends AppCompatActivity {

    TextView textView, lafadz;
    TextView tVPattern, tVkey, tvTajwid;
    Button reloadBtn;
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


        Intent i = getIntent();
        teks = i.getStringArrayListExtra("text");
//        String pattern = teks.get(0).toLowerCase().replaceAll("\\s+", "");
        String pattern = teks.get(0).toLowerCase().replaceAll("[^a-z]", "");
        String patternTajwid;
        char n = 'n';
        char m;
        int indexN=0;
        int length = pattern.length();
        for (int x = 0; x<length;x++){
            m = pattern.charAt(x);
            if (m == n && x>0){
                indexN = x-1;
                break;
            }
        }
        if (length-indexN > 4)
            patternTajwid = pattern.substring(indexN, length-3);
        else
            patternTajwid = pattern.substring(indexN, length);

        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        int idtahsin = sharedPreferences.getInt("id_tahsin",0);

        db = new DataHelper(this);
        helper = new DataHelper(getApplicationContext());
        dataModel = new DataModel(helper.getReadableDatabase());

        tahsin = dataModel.selectTahsinByID(idtahsin);
        lafadz.setText(tahsin.getLafadz());
        tVPattern.setText(pattern);
        tVkey.setText(tahsin.getTranskripsi());

        Cursor cursor = db.allDataTajwid();

        if (cursor.getCount()==0){
            textView.setText("null");
        }

//        while (cursor.moveToNext()){
//            key.add(cursor.getString(2));
//            valuedistance.add(distance(tahsin.getTransliterasi(),key.get(cursor.getPosition())));
////            stringBuilder.append("jarak : "+valuedistance.get(cursor.getPosition())+" Key : "
////                    +key.get(cursor.getPosition())+"\n");
////                stringBuilder.append(cursor.getCount());
//        }
//
//        int min = valuedistance.get(0);
//        int max = valuedistance.get(0);
//        int minIndex = 1, maxIndex =0;
//        for (int x = 1 ; x<valuedistance.size();x++){
//            if (valuedistance.get(x)<min){
//                min = valuedistance.get(x);
//                minIndex = x+1;
//            }
//        }
//        tajwid = dataModel.selectAllTajwidByID(minIndex);
//        hukum = dataModel.selectHukumByID(tajwid.getHukum());

        int distance = distance(pattern, tahsin.getTranskripsi());
        String result;
        if (distance==0)
            result= "Bacaan Sudah Benar";
        else
            result = "Terdapat Kesalahan pada Bacaan";

        textView.setText(result);
        tvTajwid.setText(patternTajwid);





        reloadBtn.setText("Ulangi Tahsin");


        reloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, TahsinActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                ResultActivity.this.finish();
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





//

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

    public static int BoyerMoore(String T, String P)
    {
        int i = P.length() -1;
        int j = P.length() -1;
        do
        {
            if (P.charAt(j) == T.charAt(i))
            {
                if (j == 0)
                {
                    return i; // a match!
                }
                else
                {
                    i--;
                    j--;
                }
            }
            else
            {
                i = i + P.length() - min(j, 1+last(T.charAt(i), P));
                j = P.length()-1;
            }
        } while(i <= T.length()-1);

        return -1;
    }

    //----------------------------------------------------------------
    // Returns index of last occurrence of character in pattern.
    //----------------------------------------------------------------
    public static int last(char c, String P)
    {
        for (int i=P.length()-1; i>=0; i--)
        {
            if (P.charAt(i) == c)
            {
                return i;
            }
        }
        return -1;
    }


    //----------------------------------------------------------------
    // Returns the minimum of two integers.
    //----------------------------------------------------------------
    public static int min(int a, int b)
    {
        if (a < b)
            return a;
        else if (b < a)
            return b;
        else
            return a;
    }

}
