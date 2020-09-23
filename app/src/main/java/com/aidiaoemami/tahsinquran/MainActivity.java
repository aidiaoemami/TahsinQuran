package com.aidiaoemami.tahsinquran;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LinearLayout tahsin, tajwid, kuis, tentang;

//    ListView list_surat;
////    ArrayList<String> dataSurat;
//    SQLiteOpenHelper helper;
//    SQLiteDatabase db;
//    ModelQuran modelQuran;
//    ArrayAdapter adapter;
//    Quran lafadzh;
//    TextView textView;
//    ImageButton voiceBtn;

    private static final int REQUEST_CODE_SPEECH_INPUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tahsin = findViewById(R.id.tahsin);
        tajwid = findViewById(R.id.tajwid);
        kuis = findViewById(R.id.materi);
        tentang = findViewById(R.id.tentang);

        tahsin.setOnClickListener(new View.OnClickListener(){
                                      @Override
                                      public void onClick(View v) {
                                          Intent i = new Intent(MainActivity.this, TahsinActivity.class);
                                          startActivity(i);
                                      }
                                  });

        tajwid.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TajwidActivity.class);
                startActivity(i);
            }
        });

        kuis.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MateriActivity.class);
                startActivity(i);
            }
        });

        tentang.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TentangActivity.class);
                startActivity(i);
            }
        });
//        list_surat = findViewById(R.id.list_surat);
//        helper = new DataHelper(getApplicationContext());
//        db = helper.getReadableDatabase();
//        modelQuran = new ModelQuran(db);

//        Random r = new Random();
//        Cursor max = db.query("quran", new String[]{"MAX(id) AS id"}, null, null, null, null, null);
//        max.moveToFirst(); // to move the cursor to first record
//        int index = max.getColumnIndex("id");
//        String data = max.getString(index);// use the data type of the column or use String itself you can parse i
//        int maxValue = Integer.parseInt(data);
//
//        Cursor min = db.query("quran", new String[]{"min(id) AS id"}, null, null, null, null, null);
//        min.moveToFirst(); // to move the cursor to first record
//        int indexMin = min.getColumnIndex("id");
//        String dataMin = min.getString(indexMin);// use the data type of the column or use String itself you can parse i
//        int minValue = Integer.parseInt(dataMin);
//
//        final int id = r.nextInt(maxValue - minValue) + minValue;
//        lafadzh = modelQuran.selectByID(id);
//        final String textLafadzh = lafadzh.getLafadz();
//        textView = findViewById(R.id.textTv);
//        textView.setText(textLafadzh);
//        voiceBtn = findViewById(R.id.voiceBtn);
//
//        voiceBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                speak(id);
//            }
//        });
//
//        modelQuran.insert("al-fatihah", 3, "الرَّحْمٰنِ الرَّحِيْمِ", "arrahmanirrahim");
//        modelQuran.insert("al-fatihah", 4, "مٰلِكِ يَوْمِ الدِّيْنِِ", "maalikiyaumiddin");
//        modelQuran.insert("al-fatihah", 5, "اِيَّاكَ نَعْبُدُ وَاِيَّاكَ نَسْتَعِيْنُِِ", "iyyakanabuduwaiyyakanastaiin");
//        modelQuran.insert("al-fatihah", 2, "اَلْحَمْدُ لِلّٰهِ رَبِّ الْعٰلَمِيْنَ", "alhamdulillahirabbilaalamin");
//        modelQuran.update(1, "al-fatihah", 1,"بِسْمِ اللّٰهِ الرَّحْمٰنِ الرَّحِيْمِ","bismillahirrahmanirrahim");

//        dataSurat = modelQuran.select();
//        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, dataSurat);
//        list_surat.setAdapter(adapter);

    }

    private void speak(int id) {
        int value = id;
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra("id", value);
//        i.putExtra(RecognizerIntent.EXTRA_PROMPT, lafadzh);

        try {
            startActivityForResult(i, REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_SPEECH_INPUT:{
                if (resultCode == RESULT_OK && null!=data){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    int id = data.getIntExtra("id", 1);
                    Intent i = new Intent(MainActivity.this, ResultActivity.class);
                    i.putExtra("text", result);
                    i.putExtra("id", id);
                    startActivity(i);
                    //textView.setText(result.get(0));
                }
                break;
            }
        }
    }
}
